package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.databinding.AdressItemViewholderBinding

class ListAddressManagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding : AdressItemViewholderBinding = AdressItemViewholderBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun onBind(
        item: AddressListModel.Addres,
        itemClick: OnItemClick<AddressListModel.Addres>,
        position: Int
    ){
        try {

            item.addressName?.let { binding.addressName.text = it }
            item.recipientName?.let { binding.recipientName.text = it }
            item.recipientPhone?.let { binding.recipientPhone.text = "( $it )" }
            item.addressLine?.let { binding.addressLine.text = it }
            item.khan?.let { binding.point.text = it }


        }catch (_:Exception){}
    }
}