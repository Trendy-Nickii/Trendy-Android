package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ProductSizeViewHolder

class ProductSizeAdapter(
    private val context: Context,
    private val mItem: ArrayList<SingleProductModel.Item>,
    private val mListener: OnItemClick<SingleProductModel.Item>
): Adapter<ProductSizeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSizeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_size_view_holder, parent, false)
        return ProductSizeViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProductSizeViewHolder, position: Int) {
        holder.onBind(mItem[position], mListener, position)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

}