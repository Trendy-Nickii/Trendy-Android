package com.kh.ite.rupp.edu.trendy.Ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnDeleteClick
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.viewHolder.ListAddressManagerViewHolder

class ListAddressManagerAdapter(
    private val context: Context,
    private val mItem: ArrayList<AddressListModel.Addres>,
    private val mListener: OnItemClick<AddressListModel.Addres>,
    private val deleteListener: OnDeleteClick<AddressListModel.Addres>
):Adapter<ListAddressManagerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAddressManagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adress_item_viewholder, parent, false)
        return ListAddressManagerViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mItem.size
    }

    fun removeItem(position: Int){
        mItem.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListAddressManagerViewHolder, position: Int) {
        holder.onBind(mItem[position], mListener, deleteListener, position)
    }
}