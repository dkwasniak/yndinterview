package com.damiankwasniak.data.repository

import androidx.lifecycle.LiveData
import com.damiankwasniak.domain.repository.PhotosRepository
import com.damiankwasniak.domain.utils.AsyncResult
import com.damiankwasniak.extensions.photoDao
import io.realm.Realm
import java.io.File

class PhotosRepositoryImpl(
    private val realm: Realm
) : PhotosRepository {

    override fun savePhoto(file: File): AsyncResult<Boolean> {
        return realm.photoDao().savePhoto(file.readBytes())
    }
}