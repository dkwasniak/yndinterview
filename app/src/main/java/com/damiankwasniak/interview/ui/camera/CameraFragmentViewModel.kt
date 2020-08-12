package com.damiankwasniak.interview.ui.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damiankwasniak.domain.interactor.PhotosInteractor
import com.damiankwasniak.domain.model.PhotoDomainModel
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.interview.ui.base.BaseViewModel
import com.damiankwasniak.interview.extensions.exhaustive
import com.damiankwasniak.interview.provider.ResourcesProvider
import kotlinx.coroutines.launch
import java.io.File


class CameraFragmentViewModel(
    private val photosInteractor: PhotosInteractor,
    resourcesProvider: ResourcesProvider
) : BaseViewModel<CameraFragmentViewModel.Command>(resourcesProvider) {

    private val _isTorchEnabled = MutableLiveData(false)

    val isTorchEnabled: LiveData<Boolean> = _isTorchEnabled

    fun onGrantPermissionButtonClicked() {
        Command.GrandPermissionButtonClicked.apply()
    }

    fun onPictureCaptured(file: File) {
        viewModelScope.launch {
            val result = photosInteractor.savePhoto(PhotoDomainModel(file.readBytes()))
            when (result) {
                is AsyncResult.Success -> Log.d("PHOTOSAVE", "photo save success")
                is AsyncResult.Error -> Log.d("PHOTOSAVE", "photo save fail")
            }.exhaustive
        }
    }

    fun onGalleryButtonClicked() {
        Command.OpenGallery.apply()
    }

    fun onTakePhotoButtonClicked() {
        Command.TakePicture.apply()
    }

    sealed class Command {
        object GrandPermissionButtonClicked : Command()

        object TakePicture : Command()

        object OpenGallery : Command()
    }

}
