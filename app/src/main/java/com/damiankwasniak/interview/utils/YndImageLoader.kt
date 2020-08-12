package com.damiankwasniak.interview.utils

import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes

interface YndImageLoader {

    fun loadIntoWithCornersRounded(byteArray: ByteArray, @DrawableRes placeholder: Int, @DimenRes radius: Int, imageView: ImageView)

}