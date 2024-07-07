package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.databinding.ChooseAdressItemViewholderBinding

class ListChooseAddressItemViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    private val binding : ChooseAdressItemViewholderBinding = ChooseAdressItemViewholderBinding.bind(itemView)

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

            if (item.isSelect == true){
                val drawable = ContextCompat.getDrawable(context, R.drawable.address_item_bg_round)
                binding.cardItem.background = drawable
                binding.isSelect.visibility = View.VISIBLE
            }else{
                val drawable = ContextCompat.getDrawable(context, R.drawable.address_item_bg_round_white_noborder)

                binding.cardItem.background = drawable
                binding.isSelect.visibility = View.GONE
            }


            binding.root.setOnClickListener {
                itemClick.onItemClickListener(item, position)
            }



        }catch (_:Exception){}
    }
}