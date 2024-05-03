package com.kh.ite.rupp.edu.trendy.Util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastItemMarginDecorator(context: Context, private val marginInPx: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        
        // Check if it's the last item
        if (position == itemCount - 1) {
            outRect.bottom = marginInPx // Set a bigger bottom margin
        } else {
            outRect.bottom = 0 // No bottom margin for other items
        }
    }
}
