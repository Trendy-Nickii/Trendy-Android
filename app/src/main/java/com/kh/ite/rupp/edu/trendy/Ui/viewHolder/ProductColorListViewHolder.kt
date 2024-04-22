package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.databinding.ItemColorViewHolderBinding
import com.kh.ite.rupp.edu.trendy.databinding.ItemSizeViewHolderBinding
import java.text.FieldPosition

class ProductColorListViewHolder(itemView: View):ViewHolder(itemView) {
    private val binding: ItemColorViewHolderBinding = ItemColorViewHolderBinding.bind(itemView)
    fun onBind(
        item: SingleProductModel.Item,
        itemClick: OnItemClick<SingleProductModel.Item>,
        position: Int
    ){
        try {
            val color = Color.parseColor(item.colorCode)
            binding.sizeLayout.backgroundTintList = ColorStateList.valueOf(color)

        }catch (_:Exception){}
    }
}