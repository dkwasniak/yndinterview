package com.damiankwasniak.domain.repository

import com.damiankwasniak.domain.utils.AsyncResult
import java.io.File

interface PhotosRepository {

    fun savePhoto(file: File): AsyncResult<Boolean>
}