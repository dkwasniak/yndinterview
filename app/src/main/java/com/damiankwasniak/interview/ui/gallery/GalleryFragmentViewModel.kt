package com.damiankwasniak.interview.ui.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damiankwasniak.domain.interactor.PhotosInteractor
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.interview.ui.base.BaseViewModel
import com.damiankwasniak.interview.provider.ResourcesProvider
import kotlinx.coroutines.launch


class GalleryFragmentViewModel(
    private val photosInteractor: PhotosInteractor,
    resourcesProvider: ResourcesProvider
) : BaseViewModel<GalleryFragmentViewModel.Command>(resourcesProvider) {

    private val _photos: MutableLiveData<List<ByteArray>> = MutableLiveData(emptyList())

    val photos: LiveData<List<ByteArray>> = _photos

    override fun onResume() {
        super.onResume()
        viewModelScope.launch {
            val result = photosInteractor.getPhotos()
            when (result) {
                is AsyncResult.Success -> {
                    Log.d("GaleryFragment", "has photos")
                    _photos.value = result.data.map { it.byteArray }
                }
                is AsyncResult.Error -> Log.d("GaleryFragment", Log.getStackTraceString(result.exception))
            }
        }
    }


    sealed class Command {

    }

}
