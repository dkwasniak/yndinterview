package com.damiankwasniak.interview.manager

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.damiankwasniak.interview.manager.file.FileManager
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.lang.Exception

class CameraManager(
    private val context: Context,
    private val fileManager: FileManager
) {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val executor = ContextCompat.getMainExecutor(context)

    private var cameraProvider: ProcessCameraProvider? = null

    private var imageCapture: ImageCapture? = null

    fun startCamera(view: PreviewView, fragment: Fragment) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            bindPreview(view, fragment)
        }, executor)
    }

    private fun bindPreview(cameraView: PreviewView, fragment: Fragment) {
        cameraProvider?.let { provider ->
            val preview: Preview = Preview.Builder().build()

            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            preview.setSurfaceProvider(cameraView.createSurfaceProvider())
            imageCapture = ImageCapture.Builder().setTargetRotation(cameraView.display.rotation).build()
            var camera = provider.bindToLifecycle(fragment as LifecycleOwner, cameraSelector, preview, imageCapture)
        }
    }

    fun unbindAll() {
        cameraProvider?.unbindAll()
    }

    fun takePhoto(onPhotoCaptured: (file: File) -> Unit, onError: (error: ImageCaptureException) -> Unit) {
        val file = fileManager.createTempImageFile()
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        if (imageCapture != null) {
            imageCapture!!.takePicture(outputFileOptions, executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException) {
                        onError(error)
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        onPhotoCaptured(file)
                    }
                })
        } else {
            onError(ImageCaptureException(ImageCapture.ERROR_UNKNOWN, "Camera unavailable", Exception("Camera unavailable")))
        }

    }

}