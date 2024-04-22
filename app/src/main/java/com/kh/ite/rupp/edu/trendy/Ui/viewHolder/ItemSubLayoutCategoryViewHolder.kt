package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.Ui.adapter.SubCategoryListHorizontalAdapter
import com.kh.ite.rupp.edu.trendy.databinding.ItemCategoryListHorizotalSubLayoutBinding

class ItemSubLayoutCategoryViewHolder(itemView:View, private val mListener: SubCategoryListHorizontalAdapter.OnSubCategoryClick):RecyclerView.ViewHolder(itemView) {
    private val binding : ItemCategoryListHorizotalSubLayoutBinding = ItemCategoryListHorizotalSubLayoutBinding.bind(itemView)
    fun onBind(item: SubCategoryModel.SubCategoryModelItem, position:Int){
        binding.title.text = item.name
        binding.root.setOnClickListener {
            mListener.onItemClick(item)
        }
    }
}