package com.damiankwasniak.interview.ui.gallery.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damiankwasniak.interview.R
import com.damiankwasniak.interview.extensions.layoutInflater
import com.damiankwasniak.interview.utils.YndImageLoader
import kotlinx.android.synthetic.main.photo_list_item.view.*

class PhotosAdapter(private val imageLoader: YndImageLoader) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    var items: List<ByteArray> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.layoutInflater().inflate(R.layout.photo_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photo: ByteArray) {
            imageLoader.loadIntoWithCornersRounded(photo, R.drawable.photo_placeholder, R.dimen.material_baseline_grid_1x, view.photoImageView)
        }
    }
}