package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ListChooseAddressItemViewHolder

class ListChooseItemAddressAdapter(
    private val context: Context,
    private val mItem: ArrayList<AddressListModel.Addres>,
    private val mListener: OnItemClick<AddressListModel.Addres>
):Adapter<ListChooseAddressItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListChooseAddressItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.choose_adress_item_viewholder, parent, false)
        return ListChooseAddressItemViewHolder(view, context)
    }


    override fun getItemCount(): Int {
        return mItem.size
    }


    override fun onBindViewHolder(holder: ListChooseAddressItemViewHolder, position: Int) {
        holder.onBind(mItem[position], mListener, position)
    }
}