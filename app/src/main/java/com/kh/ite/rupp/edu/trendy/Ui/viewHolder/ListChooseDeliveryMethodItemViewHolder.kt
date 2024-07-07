package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.DeliveryMethodModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.databinding.ChooseDeliveryMethodItemViewholderBinding

class ListChooseDeliveryMethodItemViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    private val binding : ChooseDeliveryMethodItemViewholderBinding = ChooseDeliveryMethodItemViewholderBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun onBind(
        item: DeliveryMethodModel,
        itemClick: OnItemClick<DeliveryMethodModel>,
        position: Int
    ){
        try {


            binding.deliveryMethod.text = item.delivery
            binding.imgDelivery.setImageResource(item.img!!)

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