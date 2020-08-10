package com.damiankwasniak.interview.fragment


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.base.BaseFragment
import com.damiankwasniak.interview.base.BaseViewModel
import com.damiankwasniak.interview.databinding.FragmentCameraBinding
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.extensions.gone
import com.damiankwasniak.interview.extensions.startAppSettingsActivity
import com.damiankwasniak.interview.extensions.visible
import com.damiankwasniak.interview.permission.PermissionResult
import com.damiankwasniak.interview.permission.PermissionState
import com.damiankwasniak.interview.permission.PermissionsManager
import com.damiankwasniak.interview.viewmodel.CameraFragmentViewModel
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Audio
import com.otaliastudios.cameraview.controls.Flash
import kotlinx.android.synthetic.main.fragment_camera.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CameraFragment :
    BaseFragment<CameraFragmentViewModel, CameraFragmentViewModel.Command, FragmentCameraBinding>() {

    private val viewModel: CameraFragmentViewModel by viewModel()

    private val permissionsManager: PermissionsManager by inject()

    private var imageFile: File? = null

    private lateinit var lastCameraPermissionState: PermissionState

    override fun provideLayoutRes(): Int = R.layout.fragment_camera

    override fun getViewModel(): BaseViewModel<CameraFragmentViewModel.Command> = viewModel

    override fun bindData(binding: FragmentCameraBinding) {
        binding.viewModel = viewModel
    }

    override fun onViewStateChanged(command: CameraFragmentViewModel.Command) {
        when (command) {
            CameraFragmentViewModel.Command.GrandPermissionButtonClicked -> requireContext().startAppSettingsActivity()
            is CameraFragmentViewModel.Command.TakePicture -> takePicture(command.file)
        }.exhaustive
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        lastCameraPermissionState = permissionsManager.checkPermission(requireContext(), PermissionsManager.CAMERA_PERMISSION)
        if (lastCameraPermissionState !is PermissionState.Granted) {
            permissionsManager.askForPermission(this, PermissionsManager.CAMERA_PERMISSION)
        } else {
            onPermissionGranted()
        }
    }

    private fun initObservers() {
        viewModel.isTorchEnabled.observe(viewLifecycleOwner, Observer { enabled ->
            cameraView.flash = if (enabled) Flash.TORCH else Flash.OFF
        })
    }

    private fun takePicture(file: File) {
        this.imageFile = file
        cameraView.takePicture()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
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
        cameraView.audio = Audio.OFF
        cameraView.close()
        cameraView.open()
        initListeners()
        cameraView.setLifecycleOwner(this)
    }

    private fun initListeners() {
        cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                this@CameraFragment.onPictureTaken(result)
            }
        })
    }

    private fun onPictureTaken(result: PictureResult) {
        with(result) {
            toFile(imageFile ?: return) { file ->
                file?.let {
                    viewModel.onPictureTaken(file)
                }
            }
        }

    }

}
