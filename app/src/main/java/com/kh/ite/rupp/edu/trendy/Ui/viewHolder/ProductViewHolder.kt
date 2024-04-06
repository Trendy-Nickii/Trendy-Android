package com.kh.ite.rupp.edu.trendy.Ui.viewHolder

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Util.calculateDiscount
import com.kh.ite.rupp.edu.trendy.Util.totalPriceFormat
import com.kh.ite.rupp.edu.trendy.databinding.ProductItemLayoutBinding
import com.squareup.picasso.Picasso

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding: ProductItemLayoutBinding = ProductItemLayoutBinding.bind(itemView)
    @SuppressLint("SetTextI18n")
    fun onBind(
        item: ProductListModel.ProductListModelItem,
        position: Int,
        itemClick: OnItemClick<ProductListModel.ProductListModelItem>
    ){
        try {
            binding.productName.text = item.productName
            Picasso.get().load(item.image!![0]!!.imageUrl).into(binding.productImg)

            if (item.productDiscount == 0.0){
                binding.productPriceAfterDis.visibility = View.GONE
                binding.disPercentage.visibility = View.GONE
                binding.productBeforeDis.text = binding.root.context.getString(R.string.price, totalPriceFormat(item.productPrice!!))
            }else{
                binding.productPriceAfterDis.visibility = View.VISIBLE
                binding.productPriceAfterDis.text = binding.root.context.getString(R.string.price2, totalPriceFormat(
                    calculateDiscount(
                        item.productDiscount!!,
                        item.productPrice!!
                    )
                ))

                binding.productBeforeDis.text =
                    binding.root.context.getString(R.string.price, totalPriceFormat(
                        item.productPrice!!
                    ))

                binding.disPercentage.visibility = View.VISIBLE
                binding.disPercentage.text = "-${item.productDiscount}%"
                binding.productBeforeDis.paintFlags = binding.productBeforeDis.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }



            binding.root.setOnClickListener {
                itemClick.onItemClickListener(item, position)
            }
        }catch (_: Exception){}
    }
}