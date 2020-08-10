package com.damiankwasniak.interview.provider

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.BoolRes
import androidx.annotation.DimenRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

class AndroidResourcesProvider(val context: Context) : ResourcesProvider {

    private val resources = context.resources

    override fun getStringFormatted(@StringRes resId: Int, vararg args: Any): String {
        return resources.getString(resId, *args)
    }

    override fun getString(@StringRes resId: Int): String {
        return resources.getString(resId)
    }

    override fun getQuantityString(@PluralsRes resId: Int, count: Int, vararg args: Any): String {
        return resources.getQuantityString(resId, count, *args)
    }

    override fun getInteger(resId: Int): Int {
        return resources.getInteger(resId)
    }

    override fun getDimension(@DimenRes resId: Int): Float {
        return resources.getDimension(resId)
    }

    override fun getBoolean(@BoolRes resId: Int): Boolean {
        return resources.getBoolean(resId)
    }

    override fun getDrawable(resId: Int): Drawable? {
        return ResourcesCompat.getDrawable(resources, resId, null)
    }
}