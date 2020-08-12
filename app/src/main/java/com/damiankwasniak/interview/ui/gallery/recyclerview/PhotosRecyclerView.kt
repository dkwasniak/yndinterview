package com.damiankwasniak.interview.ui.gallery.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damiankwasniak.interview.utils.YndImageLoader
import org.koin.core.KoinComponent
import org.koin.core.inject

class PhotosRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), KoinComponent {

    private val imageLoader: YndImageLoader by inject()

    private val photosAdapter = PhotosAdapter(imageLoader)

    init {
        layoutManager = GridLayoutManager(context, 2)
        adapter = photosAdapter
        addItemDecoration(PhotosItemDecoration(context))
    }

    fun setItems(photos: List<ByteArray>) {
        photosAdapter.items = photos
        photosAdapter.notifyDataSetChanged()
    }
}

@BindingAdapter("bind:photos")
fun setPhotos(plansRecyclerView: PhotosRecyclerView, photos: LiveData<List<ByteArray>>) {
    photos.value?.let {
        plansRecyclerView.setItems(it)
    }
}