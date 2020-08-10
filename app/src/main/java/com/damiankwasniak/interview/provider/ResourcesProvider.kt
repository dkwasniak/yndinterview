package com.damiankwasniak.interview.provider

import android.graphics.drawable.Drawable
import androidx.annotation.*

interface ResourcesProvider {

    fun getString(@StringRes resId: Int): String

    fun getStringFormatted(@StringRes resId: Int, vararg args: Any): String

    fun getQuantityString(@PluralsRes resId: Int, count: Int, vararg args: Any): String

    fun getInteger(@IntegerRes resId: Int): Int

    fun getDimension(@DimenRes resId: Int): Float

    fun getBoolean(@BoolRes resId: Int): Boolean

    fun getDrawable(@DrawableRes resId: Int): Drawable?
}