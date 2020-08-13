package com.damiankwasniak.interview.ui.camera


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.databinding.FragmentCameraBinding
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.extensions.gone
import com.damiankwasniak.interview.extensions.startAppSettingsActivity
import com.damiankwasniak.interview.extensions.visible
import com.damiankwasniak.interview.manager.CameraManager
import com.damiankwasniak.interview.permission.PermissionResult
import com.damiankwasniak.interview.permission.PermissionState
import com.damiankwasniak.interview.permission.PermissionsManager
import com.damiankwasniak.interview.ui.base.BaseFragment
import com.damiankwasniak.interview.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_camera.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CameraFragment :
    BaseFragment<CameraFragmentViewModel, CameraFragmentViewModel.Command, FragmentCameraBinding>() {

    private val viewModel: CameraFragmentViewModel by viewModel()

    private val cameraManager: CameraManager by inject()

    private val permissionsManager: PermissionsManager by inject()

    private lateinit var lastCameraPermissionState: PermissionState

    override fun provideLayoutRes(): Int = R.layout.fragment_camera

    override fun getViewModel(): BaseViewModel<CameraFragmentViewModel.Command> = viewModel

    override fun bindData(binding: FragmentCameraBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lastCameraPermissionState = permissionsManager.checkPermission(requireContext(), PermissionsManager.CAMERA_PERMISSION)
        if (lastCameraPermissionState !is PermissionState.Granted) {
            permissionsManager.askForPermission(this, PermissionsManager.CAMERA_PERMISSION)
        } else {
            onPermissionGranted()
        }
    }

    override fun onViewStateChanged(command: CameraFragmentViewModel.Command) {
        when (command) {
            CameraFragmentViewModel.Command.GrandPermissionButtonClicked -> requireContext().startAppSettingsActivity()
            CameraFragmentViewModel.Command.TakePicture -> takePicture()
            CameraFragmentViewModel.Command.OpenGallery -> goToGallery()
        }.exhaustive
    }

    private fun goToGallery() {
        val direction = CameraFragmentDirections.actionCameraFragmentToGalleryFragment()
        findNavController().navigate(direction)
    }

    private fun startCamera() {
        cameraView?.let {
            cameraManager.startCamera(it, this)
        }
    }

    override fun onPause() {
        super.onPause()
        cameraManager.unbindAll()
    }

    private fun takePicture() {
        cameraManager.takePhoto(
            onPhotoCaptured = {
                viewModel.onPictureCaptured(it)
            },
            onError = {
                Log.d("CAMERA", "photo captured error")
            })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onPermissionResult(permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults))
    }

    private fun onPermissionResult(result: PermissionResult) = when (result) {
        is PermissionResult.Granted -> onPermissionGranted()
        is PermissionResult.Denied -> grantPermissionView.visible()
        PermissionResult.Unknown -> {
        }
    }

    private fun onPermissionGranted() {
        grantPermissionView.gone()
        startCamera()
        initListeners()
    }

    private fun initListeners() {

    }

}
