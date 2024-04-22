package com.kh.ite.rupp.edu.trendy.Ui.custom

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductColorListAdapter
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductSizeAdapter
import com.kh.ite.rupp.edu.trendy.databinding.BottomSheetAddToCartLayoutBinding
import com.squareup.picasso.Picasso

class AddToCartBottomSheet (
    private val context: Context,
    private val sizeSelect: String? = "",
    private val data : SingleProductModel
): BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetAddToCartLayoutBinding
    private var sizePro : String? = ""
    private var colorPro: String? = ""
    private var itemId :String? = ""
    private var itemForColor: List<SingleProductModel.Item> = listOf()
    private var itemList : SingleProductModel? = SingleProductModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddToCartLayoutBinding.inflate(inflater, container, false)

        Picasso.get().load(data.image!![0]!!.imageUrl).into(binding.imageProduct)
        data.productName?.let { binding.productName.text = it }
        data.amount?.let { binding.stock.text = context.getString(R.string.stock, it.toString()) }


        val itemSize = ArrayList<SingleProductModel.Item>()
        for (i in data.items!!) {
            val newSize = i.size
            // Check if the size already exists in itemSize
            if (!itemSize.any { it.size == newSize }) {
                itemSize.add(i)
            }
        }
        sizePro = sizeSelect
        sizeList(itemSize)
        itemList = data
        itemForColor = filterItemsBySize(itemList!!.items!!, sizePro!!)
        initColorList(itemForColor as ArrayList)


        return binding.root
    }


    private fun sizeList(item: List<SingleProductModel.Item>){
        val data : ArrayList<SingleProductModel.Item> = item as ArrayList
        binding.sizeRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductSizeAdapter(context,data , object : OnItemClick<SingleProductModel.Item>{
                override fun onItemClickListener(
                    model: SingleProductModel.Item,
                    position: Int
                ) {
                    // Set isActive flag for the clicked item to true
                    item[position].isActive = true
                    sizePro = model.size
                    itemId = ""

                    itemForColor = filterItemsBySize(itemList!!.items!!, sizePro!!)
                    Log.d("PRODUCT_DETAIL_SIZE", "Filtered items for size = $sizePro")
                    itemForColor.forEach {
                        Log.d("PRODUCT_DETAIL_SIZE", "item:  ${it.itemId}, ${it.color}, ${it.colorCode}, ${it.size}, ${it.amount}")

                    }
                    initColorList(itemForColor as ArrayList)
                    // Set isActive flag to false for all other items except the clicked one
                    item.forEachIndexed { index, item ->
                        item.isActive = (index == position)
                    }

                    // Notify adapter of the data change

                    adapter?.notifyDataSetChanged()
                }
            })

        }
    }

    private fun initColorList(item: ArrayList<SingleProductModel.Item>){
        binding.colorRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductColorListAdapter(context, item, object : OnItemClick<SingleProductModel.Item>{
                override fun onItemClickListener(model: SingleProductModel.Item, position: Int) {

                }

            })
        }
    }

    fun filterItemsBySize(items: List<SingleProductModel.Item>, selectedSize: String): List<SingleProductModel.Item> {
        return items.filter { it.size == selectedSize }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener{ dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            }
        }

        return dialog
    }
}