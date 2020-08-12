package com.damiankwasniak.interview.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun View.visible() {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
    }
}

fun View.setViewVisibility(isVisible: Boolean?) {
    if (isVisible != null && isVisible) {
        this.visible()
    } else {
        this.gone()
    }
}

fun ViewGroup.layoutInflater(): LayoutInflater {
    return LayoutInflater.from(this.context)
}