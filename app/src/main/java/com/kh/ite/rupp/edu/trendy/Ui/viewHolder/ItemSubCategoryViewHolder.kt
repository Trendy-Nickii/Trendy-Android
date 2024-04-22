package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ListSubCategoryAdapter
import com.kh.ite.rupp.edu.trendy.databinding.ItemSubcategoryViewHolderBinding
import com.squareup.picasso.Picasso

class ItemSubCategoryViewHolder(itemView: View, private val mListener: ListSubCategoryAdapter.OnClickListenerItem):RecyclerView.ViewHolder(itemView) {
    private val binding: ItemSubcategoryViewHolderBinding = ItemSubcategoryViewHolderBinding.bind(itemView)
    fun onBind(item: SubCategoryModel.SubCategoryModelItem, position:Int){
        binding.categoryName.text = item.name
        if (!item.imageUrl.isNullOrEmpty()){
            Log.d("IMAGE_URL", "item = ${item.imageUrl}")
            Picasso.get().load(item.imageUrl).into(binding.imageSubCategory)
        }

        binding.root.setOnClickListener {
            mListener.onItemClick(item)
        }
    }
}