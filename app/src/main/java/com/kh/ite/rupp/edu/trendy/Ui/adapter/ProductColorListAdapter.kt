package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ProductColorListViewHolder

class ProductColorListAdapter(
    private val context: Context,
    private val mItem: ArrayList<SingleProductModel.Item>,
    private val itemClick: OnItemClick<SingleProductModel.Item>
):Adapter<ProductColorListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductColorListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_color_view_holder, parent, false)
        return ProductColorListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductColorListViewHolder, position: Int) {
        holder.onBind(mItem[position], itemClick, position)
    }
    override fun getItemCount(): Int {
        return mItem.size
    }
}