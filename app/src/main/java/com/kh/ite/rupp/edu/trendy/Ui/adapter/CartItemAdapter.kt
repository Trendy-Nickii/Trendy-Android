package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnUpdateDeleteClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.CartItemViewHolder
import com.kh.ite.rupp.edu.trendy.Util.LastItemMarginDecorator

class CartItemAdapter (
    private val context: Context,
    private val mItem: ArrayList<CartItemModel.Cart>,
    private val itemClick: OnUpdateDeleteClick<CartItemModel.Cart>
):Adapter<CartItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false)
        return CartItemViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.onBin(mItem[position], itemClick, position)
    }

    fun removeItem(position: Int){
        mItem.removeAt(position)
        notifyDataSetChanged()
    }

    fun addLastItemMargin(recyclerView: RecyclerView, marginInPx: Int) {
        recyclerView.addItemDecoration(LastItemMarginDecorator(context, marginInPx))
    }

}