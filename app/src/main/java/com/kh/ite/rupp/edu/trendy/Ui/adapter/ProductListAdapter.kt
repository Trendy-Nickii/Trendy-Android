package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ProductViewHolder

class ProductListAdapter(
    private val context: Context,
    private val mItem: ProductListModel,
    private val mListener: OnItemClick<ProductListModel.ProductListModelItem>
): Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(mItem[position], position, mListener)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

}