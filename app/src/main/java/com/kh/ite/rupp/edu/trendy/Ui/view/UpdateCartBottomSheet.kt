package com.kh.ite.rupp.edu.trendy.Ui.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.CartUpdateModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductColorListAdapter
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductSizeAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Util.calculateDiscount
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.Util.totalPriceFormat
import com.kh.ite.rupp.edu.trendy.ViewModel.CartViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.CartViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.BottomSheetAddToCartLayoutBinding
import com.squareup.picasso.Picasso
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class UpdateCartBottomSheet(
    private val context: Context,
    private val sizeSelect: String? = "",
    private val color: String?,
    private val qty: Int?,
    private val productId: Int,
    private val cartId: Int,
    private val mListener: OnBackResponse<AddToCartResponseModel>

) : BottomSheetDialogFragment(), OnBackResponse<AddToCartResponseModel> {
    private lateinit var binding: BottomSheetAddToCartLayoutBinding
    private var sizePro: String? = ""
    private var colorPro: String? = ""
    private var itemId: Int? = 0
    private var itemForColor: List<SingleProductModel.Item> = listOf()
    private var itemList: SingleProductModel? = SingleProductModel()
    private var amount = 1
    private lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
    private lateinit var api: MyApi
    private lateinit var factory: CartViewModelFactory
    private lateinit var cartRepository: CartRepository
    private lateinit var viewModel: CartViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(context, networkConnectionInterceptor)
        cartRepository = CartRepository(api)
        factory = CartViewModelFactory(cartRepository)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        viewModel.listener = this
        viewModel.getOneProduct(productId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddToCartLayoutBinding.inflate(inflater, container, false)
        binding.buyNowBtn.setOnClickListener { dismiss() }

        viewModel.productOne.observe(viewLifecycleOwner, Observer {
            initView(it)
        })

        return binding.root
    }

    private fun initView(data: SingleProductModel) {
        Picasso.get().load(data.image!![0]!!.imageUrl).into(binding.imageProduct)
        data.productName?.let { binding.productName.text = it }

        binding.buyNowBtn.text = "Cancel"
        binding.addToCartBtn.text = "Update"


        val itemSize = ArrayList<SingleProductModel.Item>()
        for (i in data.items!!) {
            val newSize = i.size
            // Check if the size already exists in itemSize
            if (!itemSize.any { it.size == newSize }) {
                itemSize.add(i)
            }
        }
        sizePro = sizeSelect
       for (i in data.items!!){
           if (i.size == sizePro){
               i.isActive = true
           }
       }
        sizeList(itemSize)
        itemList = data
        itemForColor = filterItemsBySize(itemList!!.items!!, sizePro!!)


//        itemForColor[0].isActiveColor = true
//        itemId = itemForColor[0].itemId
//        itemForColor[0].amount?.let { binding.stock.text = context.getString(R.string.stock, it.toString()) }

        for (i in itemForColor) {
            if (i.color == color) {
                i.isActiveColor = true
                itemId = i.itemId
                i.amount?.let {
                    binding.stock.text = context.getString(R.string.stock, it.toString())
                    amountBtn(it)
                }
            }
        }

        amount = qty!!
        initColorList(itemForColor as ArrayList)
        binding.txtAmount.text = amount.toString()
        initPriceDisplay(data)

        binding.addToCartBtn.setOnClickListener {
            val body = CartUpdateModel(itemId.toString(), amount)
            viewModel.updateCart(cartId.toString(), body)
            Log.d("UPDATE_CART", "data = $body ")
        }

    }


    private fun initPriceDisplay(item: SingleProductModel) {
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

    private fun amountBtn(stock: Int) {

        binding.addBtn.setOnClickListener {
            if (amount < stock){
                Log.d("ADDTOCART", "amount in sub = $amount stock = $stock ")
                amount += 1
                binding.txtAmount.text = amount.toString()
            }
            if (stock <= amount){
                Log.d("ADDTOCART", "amount in sub = $amount stock = $stock ")
            }

        }

        binding.subBtn.setOnClickListener {
            if (amount > 1) {
                amount -= 1
                binding.txtAmount.text = amount.toString()
            }
            Log.d("ADDTOCART", "amount in sub = $amount ")

        }
    }

    private fun sizeList(item: List<SingleProductModel.Item>) {
        val data: ArrayList<SingleProductModel.Item> = item as ArrayList
        binding.sizeRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductSizeAdapter(context, data, object :
                OnItemClick<SingleProductModel.Item> {
                override fun onItemClickListener(
                    model: SingleProductModel.Item,
                    position: Int
                ) {
                    // Set isActive flag for the clicked item to true
                    item[position].isActive = true
                    sizePro = model.size

                    itemForColor = filterItemsBySize(itemList!!.items!!, sizePro!!)
                    Log.d("PRODUCT_DETAIL_SIZE", "Filtered items for size = $sizePro")
                    itemForColor.forEach {
                        Log.d(
                            "PRODUCT_DETAIL_SIZE",
                            "item:  ${it.itemId}, ${it.color}, ${it.colorCode}, ${it.size}, ${it.amount}"
                        )

                    }


                    // Set isActive flag to false for all other items except the clicked one
                    item.forEachIndexed { index, item ->
                        item.isActive = (index == position)
                    }

                    for (i in itemForColor) {
                        i.isActiveColor = false
                    }

                    itemForColor[0].isActiveColor = true
                    itemId = itemForColor[0].itemId
                    itemForColor[0].amount?.let {
                        binding.stock.text = context.getString(R.string.stock, it.toString())
                    }

                    initColorList(itemForColor as ArrayList)
                    // Notify adapter of the data change

                    adapter?.notifyDataSetChanged()
                }
            })

        }
    }

    private fun initColorList(item: ArrayList<SingleProductModel.Item>) {
        binding.colorRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductColorListAdapter(context, item, object :
                OnItemClick<SingleProductModel.Item> {
                override fun onItemClickListener(model: SingleProductModel.Item, position: Int) {
                    item[position].isActiveColor = true

                    item[position].amount?.let {
                        binding.stock.text = context.getString(R.string.stock, it.toString())
                        amountBtn(it)
                    }


                    // Set isActive flag to false for all other items except the clicked one
                    item.forEachIndexed { index, item ->
                        item.isActiveColor = (index == position)
                    }
                    // Notify adapter of the data change
                    adapter?.notifyDataSetChanged()
                    itemId = model.itemId
                }

            })
        }
    }

    fun filterItemsBySize(
        items: List<SingleProductModel.Item>,
        selectedSize: String
    ): List<SingleProductModel.Item> {
        return items.filter { it.size == selectedSize }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            }
        }

        return dialog
    }

    override fun success(message: AddToCartResponseModel) {

        val position = MotionToast.GRAVITY_BOTTOM

        MotionToast.createColorToast(
            requireActivity(), "Update to Cart 🛒",
            message = message.message!!,
            MotionToastStyle.SUCCESS,
            position,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, R.font.roboto_regular)
        )

        mListener.success(message)

        dismiss()

    }

    override fun fail(message: String) {
        context.toastHelper(message)
        mListener.fail(message)
    }
}