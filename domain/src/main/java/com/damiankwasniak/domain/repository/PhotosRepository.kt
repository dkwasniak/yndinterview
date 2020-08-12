package com.damiankwasniak.domain.repository

import com.damiankwasniak.domain.model.PhotoDomainModel
import com.damiankwasniak.domain.utils.AsyncResult

interface PhotosRepository {

    fun savePhoto(model: PhotoDomainModel): AsyncResult<Boolean>

    fun getPhotos(): AsyncResult<List<PhotoDomainModel>>
}