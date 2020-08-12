package com.damiankwasniak.interview.ui.gallery.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.damiankwasniak.interview.R

class PhotosItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var needLeftSpacing = false

    private val sizeGridSpacingPx = context.resources.getDimension(R.dimen.material_baseline_grid_1x).toInt()

    private val gridSize = 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {


        val frameWidth = ((parent.width - sizeGridSpacingPx * (gridSize - 1)) / gridSize)
        val padding = parent.width / gridSize - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < gridSize) {
            outRect.top = padding
        } else {
            outRect.top = sizeGridSpacingPx
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = padding * 2
            outRect.right = padding
            needLeftSpacing = true
        } else if ((itemPosition + 1) % gridSize == 0) {
            needLeftSpacing = false
            outRect.right = padding * 2
            outRect.left = padding
        } else if (needLeftSpacing) {
            needLeftSpacing = false
            outRect.left = sizeGridSpacingPx - padding
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = sizeGridSpacingPx - padding
            } else {
                outRect.right = sizeGridSpacingPx / 2
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            needLeftSpacing = false
            outRect.left = sizeGridSpacingPx / 2
            outRect.right = sizeGridSpacingPx - padding
        } else {
            needLeftSpacing = false
            outRect.left = sizeGridSpacingPx / 2
            outRect.right = sizeGridSpacingPx / 2
        }
        outRect.bottom = padding
    }
}