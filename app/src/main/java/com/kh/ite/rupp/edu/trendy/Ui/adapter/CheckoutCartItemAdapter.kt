package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.CartCheckoutSummaryModel
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.CheckoutCartItemViewHolder
import com.kh.ite.rupp.edu.trendy.Util.LastItemMarginDecorator

class CheckoutCartItemAdapter (
    private val context: Context,
    private val mItem: ArrayList<CartCheckoutSummaryModel.OrderDetails.Item>,
):Adapter<CheckoutCartItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutCartItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_checkout_item_layout, parent, false)
        return CheckoutCartItemViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    override fun onBindViewHolder(holder: CheckoutCartItemViewHolder, position: Int) {
        holder.onBin(mItem[position], position)
    }

    fun addLastItemMargin(recyclerView: RecyclerView, marginInPx: Int) {
        recyclerView.addItemDecoration(LastItemMarginDecorator(context, marginInPx))
    }

}