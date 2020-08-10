package com.damiankwasniak.interview.file

import android.content.Context
import android.os.Environment
import com.damiankwasniak.EMPTY_STRING
import java.io.File
import java.io.IOException

class DefaultFileManager(private val ctx: Context) : FileManager {

    private val picturesDir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    private var currentPhotoPath: String = EMPTY_STRING


    @Throws(IOException::class)
    override fun createTempImageFile(): File {
        val timeStamp: String = System.currentTimeMillis().toString()
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", picturesDir)
            .apply {
                currentPhotoPath = absolutePath
            }
    }
}