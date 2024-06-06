package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.DeliveryMethodModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ListChooseDeliveryMethodItemViewHolder

class DeliveryAdapter(
    private val context: Context,
    private val mItem: ArrayList<DeliveryMethodModel>,
    private val mListener: OnItemClick<DeliveryMethodModel>
):Adapter<ListChooseDeliveryMethodItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListChooseDeliveryMethodItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.choose_delivery_method_item_viewholder, parent, false)
        return ListChooseDeliveryMethodItemViewHolder(view, context)
    }


    override fun getItemCount(): Int {
        return mItem.size
    }

    override fun onBindViewHolder(holder: ListChooseDeliveryMethodItemViewHolder, position: Int) {
       holder.onBind(mItem[position], mListener, position)
    }
}