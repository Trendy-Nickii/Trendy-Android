package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.databinding.ItemSizeViewHolderBinding

class ProductSizeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding: ItemSizeViewHolderBinding = ItemSizeViewHolderBinding.bind(itemView)
    fun onBind(item: SingleProductModel.Item, mListener: OnItemClick<SingleProductModel.Item>, position: Int){
        try {
            binding.size.text = item.size

            if (item.isActive == true){
                // Assuming you have a reference to your binding object
                binding.sizeLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.primary))
                binding.size.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))

            }
            else{
                // Assuming you have a reference to your binding object
                binding.sizeLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.white))
                binding.size.setTextColor(ContextCompat.getColor(binding.root.context, R.color.txt_black))
            }

            binding.root.setOnClickListener {
                mListener.onItemClickListener(item, position)
            }

        }catch (_: Exception){}
    }
}