package com.damiankwasniak.domain.interactor

import com.damiankwasniak.domain.repository.PhotosRepository
import com.damiankwasniak.domain.utils.AsyncResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class PhotosInteractor(
    private val photosRepository: PhotosRepository
) {

    suspend fun savePhoto(file: File): AsyncResult<Boolean> {
        return withContext(Dispatchers.IO) {
            photosRepository.savePhoto(file)
        }
    }
}