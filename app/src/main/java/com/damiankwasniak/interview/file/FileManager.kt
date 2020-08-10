package com.damiankwasniak.interview.file

import java.io.File

interface FileManager {

    fun createTempImageFile(): File
}