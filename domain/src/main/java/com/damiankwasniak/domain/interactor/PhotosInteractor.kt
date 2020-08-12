package com.damiankwasniak.domain.interactor

import com.damiankwasniak.domain.model.PhotoDomainModel
import com.damiankwasniak.domain.repository.PhotosRepository
import com.damiankwasniak.domain.utils.AsyncResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotosInteractor(
    private val photosRepository: PhotosRepository
) {

    suspend fun savePhoto(model: PhotoDomainModel): AsyncResult<Boolean> {
        return withContext(Dispatchers.IO) {
            photosRepository.savePhoto(model)
        }
    }

    suspend fun getPhotos(): AsyncResult<List<PhotoDomainModel>> {
        return withContext(Dispatchers.IO) {
            photosRepository.getPhotos()
        }
    }
}