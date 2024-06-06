package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.UserOrderingModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Util.dateFormat
import com.kh.ite.rupp.edu.trendy.databinding.OrderingItemViewholderBinding
import com.squareup.picasso.Picasso

class UserOrderingItemViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    private val binding: OrderingItemViewholderBinding = OrderingItemViewholderBinding.bind(itemView)

    fun onBind(
        item: UserOrderingModel.UserOrderingItem,
        position:Int,
        context: Context
    ){
        try {
            binding.productName.text = item.items!![0]!!.productName
            Picasso.get().load(item.items!![0]!!.images!![0]!!.imageUrl).into(binding.imageProduct)
            binding.date.text = dateFormat(item.onDate!!)
            if (item.items!!.size > 1){
                binding.otherProduct.text = "+${item.items!!.size-1} other products"
            }else{
                binding.otherProduct.visibility = View.GONE
            }
            binding.qty.text = "$${item.totalAmountAfterDiscount}"
            if (item.status == "pending"){
                binding.status.text = item.status
                binding.status.setTextColor(context.getColor(R.color.pending))
                binding.isSelect.visibility = View.GONE
            }else if (item.status == "confirmed"){
                binding.status.text = "Item preparation......"
                binding.status.setTextColor(context.getColor(R.color.confirm))
                binding.isSelect.visibility = View.GONE
                binding.delete.visibility = View.GONE
            }else if (item.status == "shipped"){
                binding.status.text = "Delivery..........."
                binding.status.setTextColor(context.getColor(R.color.shipped))
                binding.isSelect.visibility = View.GONE
            }else if (item.status == "completed"){
                binding.status.text = item.status
                binding.status.setTextColor(context.getColor(R.color.primary))
                binding.isSelect.visibility = View.VISIBLE
                binding.delete.visibility =  View.GONE
            }


        }catch (_:Exception){

        }
    }
}