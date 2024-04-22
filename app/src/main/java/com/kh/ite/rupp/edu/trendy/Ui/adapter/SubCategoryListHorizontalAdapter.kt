package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ItemSubLayoutCategoryViewHolder


class SubCategoryListHorizontalAdapter(
    private val context: Context,
    private val mItem: SubCategoryModel,
    private val mListener: OnSubCategoryClick
):RecyclerView.Adapter<ItemSubLayoutCategoryViewHolder>() {
    interface OnSubCategoryClick{
        fun onItemClick(data: SubCategoryModel.SubCategoryModelItem)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemSubLayoutCategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_list_horizotal_sub_layout, parent,false)
        return ItemSubLayoutCategoryViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    override fun onBindViewHolder(holder: ItemSubLayoutCategoryViewHolder, position: Int) {
        holder.onBind(mItem[position], position)
    }
}