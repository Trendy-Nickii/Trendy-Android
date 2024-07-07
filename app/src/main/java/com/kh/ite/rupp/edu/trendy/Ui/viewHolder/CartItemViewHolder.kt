package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.CartItemModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnUpdateDeleteClick
import com.kh.ite.rupp.edu.trendy.Util.calculateDiscount
import com.kh.ite.rupp.edu.trendy.Util.totalPriceFormat
import com.kh.ite.rupp.edu.trendy.databinding.CartItemLayoutBinding
import com.squareup.picasso.Picasso

class CartItemViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView) {
    private val binding: CartItemLayoutBinding = CartItemLayoutBinding.bind(itemView)

    fun onBin(
        item: CartItemModel.Cart,
        itemClick: OnUpdateDeleteClick<CartItemModel.Cart>,
        position: Int
    ){
        try{

            item.productName?.let { binding.productName.text = it}
            initPriceDisplay(item)
            val color = Color.parseColor(item.colorCode)
            binding.colorDis.backgroundTintList = ColorStateList.valueOf(color)
            item.images?.let {
                Picasso.get().load(it[0]).into(binding.imageProduct)
            }
            item.size?.let { binding.size.text = context.getString(R.string.size_v, it) }
            item.colorCode?.let { binding.color.text = context.getString(R.string.color_v, it) }
            item.quantity?.let { binding.qty.text = context.getString(R.string.qty_v, it.toString()) }


            binding.edit.setOnClickListener {
                itemClick.onUpdateListener(item, position)
            }
            binding.delete.setOnClickListener {
                itemClick.onDeleteListener(item, position)
            }

        }catch (_: Exception){}
    }


    private fun initPriceDisplay(item : CartItemModel.Cart){
        if (item.productDiscount == 0.0) {
            binding.productPriceAfterDis.visibility = View.GONE
            binding.disPercentage.visibility = View.GONE
            binding.productBeforeDis.text = binding.root.context.getString(
                R.string.price,
                totalPriceFormat(item.productPrice!!)
            )
            binding.productBeforeDis.setTextColor(context.getColor(R.color.primary))
        } else {
            binding.productPriceAfterDis.visibility = View.VISIBLE
            binding.productPriceAfterDis.text = binding.root.context.getString(
                R.string.price2, totalPriceFormat(
                    calculateDiscount(
                        item.productDiscount!!,
                        item.productPrice!!
                    )
                )
            )
            binding.productPriceAfterDis.setTextColor(context.getColor(R.color.primary))
            binding.productBeforeDis.text =
                binding.root.context.getString(
                    R.string.price, totalPriceFormat(
                        item.productPrice!!
                    )
                )
            binding.productBeforeDis.setTextColor(context.getColor(R.color.txt_black))

            binding.disPercentage.visibility = View.VISIBLE
            binding.disPercentage.text = "-${item.productDiscount}%"
            binding.productBeforeDis.paintFlags =
                binding.productBeforeDis.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        }
    }
}