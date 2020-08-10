package com.damiankwasniak.interview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damiankwasniak.domain.interactor.PhotosInteractor
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.interview.base.BaseViewModel
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.file.FileManager
import com.damiankwasniak.interview.provider.ResourcesProvider
import kotlinx.coroutines.launch
import java.io.File


class CameraFragmentViewModel(
    private val photosInteractor: PhotosInteractor,
    private val fileManager: FileManager,
    resourcesProvider: ResourcesProvider
) : BaseViewModel<CameraFragmentViewModel.Command>(resourcesProvider) {

    private val _isTorchEnabled = MutableLiveData(false)

    val isTorchEnabled: LiveData<Boolean> = _isTorchEnabled

    fun onGrantPermissionButtonClicked() {
        Command.GrandPermissionButtonClicked.apply()
    }

    fun onPictureTaken(file: File) {
        viewModelScope.launch {
            val result = photosInteractor.savePhoto(file)
            when (result) {
                is AsyncResult.Success -> Log.d("PHOTOSAVE", "photo save success")
                is AsyncResult.Error -> Log.d("PHOTOSAVE", "photo save fail")
            }.exhaustive
        }
    }

    fun onTorchButtonClicked() {

    }

    fun onTakePhotoButtonClicked() {
        val file = fileManager.createTempImageFile()
        Command.TakePicture(file).apply()
    }

    sealed class Command {
        object GrandPermissionButtonClicked : Command()

        class TakePicture(val file: File) : Command()
    }

}
