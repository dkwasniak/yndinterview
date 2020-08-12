package com.damiankwasniak.interview.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
 * Created by avonil on 9/14/17.
 */
class GlideImageLoader constructor(var context: Context) : YndImageLoader {


    override fun loadIntoWithCornersRounded(byteArray: ByteArray, @DrawableRes placeholder: Int, radius: Int, imageView: ImageView) {
        Glide.with(context).load(byteArray).asBitmap()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .transform(
                RoundedCornersTransformation(
                    imageView.context,
                    imageView.resources.getDimensionPixelSize(radius),
                    0,
                    RoundedCornersTransformation.CornerType.ALL
                )
            )
            .placeholder(placeholder)
            .into(imageView)
    }
}
