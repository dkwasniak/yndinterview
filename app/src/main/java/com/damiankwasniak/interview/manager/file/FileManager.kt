package com.damiankwasniak.interview.manager.file

import java.io.File

interface FileManager {

    fun createTempImageFile(): File
}