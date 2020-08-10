package com.damiankwasniak.interview.extensions

import android.view.View

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