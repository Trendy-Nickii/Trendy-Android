package com.kh.ite.rupp.edu.trendy.Ui.view

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.Model.SingleProductModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductColorListAdapter
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductListAdapter
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductSizeAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnRequestResponse
import com.kh.ite.rupp.edu.trendy.Util.calculateDiscount
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.Util.totalPriceFormat
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.ProductDetailViewModelFactory
import com.kh.ite.rupp.edu.trendy.ViewModel.ProductDetailViewModel
import com.kh.ite.rupp.edu.trendy.databinding.ActivityProductDetailBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class ProductDetailActivity : BaseActivityBinding<ActivityProductDetailBinding>(),
    OnRequestResponse {
    override fun getLayoutViewBinding(): ActivityProductDetailBinding =
        ActivityProductDetailBinding.inflate(layoutInflater)

    private lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
    private lateinit var api: MyApi
    private lateinit var productRepository: ProductRepository
    private lateinit var factory: ProductDetailViewModelFactory
    private var viewModel: ProductDetailViewModel? = null
    private var sizePro : String? = ""
    private var colorPro: String? = ""
    private var itemId :String? = ""
    private var itemForColor: List<SingleProductModel.Item> = listOf()
    private var itemList : SingleProductModel? = SingleProductModel()
    private var productData: SingleProductModel? = SingleProductModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Change the status bar color
        val window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        binding.backBtnLogin.setOnClickListener { finish() }
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        productRepository = ProductRepository(api)
        factory = ProductDetailViewModelFactory(productRepository, productId())
        viewModel = ViewModelProvider(this, factory).get(ProductDetailViewModel::class.java)
        viewModel?.listener = this
        showSkeleton()
        val itemSize = ArrayList<SingleProductModel.Item>()
        // Observer for productOne LiveData
        viewModel?.productOne?.observe(this, Observer { singleProduct ->
            singleProduct?.let {
                // Update UI with the received data
                productData = it
                initView(it)
                viewModel?.getProductById(it.categoryId.toString())
                for (i in singleProduct.items!!) {
                    val newSize = i.size
                    // Check if the size already exists in itemSize
                    if (!itemSize.any { it.size == newSize }) {
                        itemSize.add(i)
                    }
                }
                itemSize[0].isActive = true
                sizePro = itemSize[0].size
                sizeList(itemSize)
                itemList = singleProduct
                itemForColor = filterItemsBySize(itemList!!.items!!, sizePro!!)
                Log.d("PRODUCT_DETAIL_SIZE", "Filtered items for size = $sizePro")
                itemForColor.forEach {
                    Log.d("PRODUCT_DETAIL_SIZE", "item:  ${it.itemId}, ${it.color}, ${it.colorCode}, ${it.size}, ${it.amount}")

                }
                initColorList(itemForColor as ArrayList)
                hideSkeleton()
            }
        })

        binding.addToCartBtn.setOnClickListener {
            val bottomSheet = AddToCartBottomSheet(this, sizePro, productData!!, this)
            bottomSheet.show(supportFragmentManager, "Add to cart bottom sheet")
        }




        // Debugging: Log initial state of productOne LiveData
        Log.d("PRODUCT_DETAIL", "Initial productOne value: ${viewModel?.productOne?.value}")
        // Debugging: Trigger the API call manually for testing
        // getOneProduct(id)

        viewModel?.productList?.observe(this, Observer { productList ->
            productList?.let {
                initSimilarProduct(productList)
            }
        })
        // Debugging: Log initial state of productOne LiveData
        Log.d("PRODUCT_DETAIL", "Initial product list value: ${viewModel?.productList?.value}")

    }


    override fun initView() {




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

    private fun colorList(item: ArrayList<SingleProductModel.AvailableSize.ColorOnSize>){

    }
    private fun showSkeleton() {
        // Show skeleton layout
        binding.loading.visibility = View.VISIBLE
        // Hide RecyclerViews
        binding.contentLayout.visibility = View.GONE
        binding.groupBtn.visibility = View.GONE
    }

    private fun hideSkeleton() {
        // Hide skeleton layout
        binding.loading.visibility = View.GONE
        // Show RecyclerViews
        binding.contentLayout.visibility = View.VISIBLE
        binding.groupBtn.visibility = View.VISIBLE

    }

    private fun initView(item: SingleProductModel) {
        binding.productName.text = item.productName
        binding.productDesc.text = item.productDescription
        if (item.productDiscount == 0.0) {
            binding.productPriceAfterDis.visibility = View.GONE
            binding.disPercentage.visibility = View.GONE
            binding.productBeforeDis.text = binding.root.context.getString(
                R.string.price,
                totalPriceFormat(item.productPrice!!)
            )
            binding.productBeforeDis.setTextColor(this.getColor(R.color.primary))
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
            binding.productPriceAfterDis.setTextColor(this.getColor(R.color.primary))
            binding.productBeforeDis.text =
                binding.root.context.getString(
                    R.string.price, totalPriceFormat(
                        item.productPrice!!
                    )
                )
            binding.productBeforeDis.setTextColor(this.getColor(R.color.txt_black))

            binding.disPercentage.visibility = View.VISIBLE
            binding.disPercentage.text = "-${item.productDiscount}%"
            binding.productBeforeDis.paintFlags =
                binding.productBeforeDis.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        }

        val slider : ArrayList<SlideModel> = arrayListOf()
        if (!item.image.isNullOrEmpty()){
            for(i in item.image!!){
                slider.add(SlideModel(i!!.imageUrl, ScaleTypes.FIT))
            }
        }

        binding.productImage.setImageList(slider, ScaleTypes.FIT)

    }

    private fun initSimilarProduct(dataItem: ProductListModel) {
        binding.similarRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductListAdapter(
                context,
                dataItem,
                object : OnItemClick<ProductListModel.ProductListModelItem> {
                    override fun onItemClickListener(
                        model: ProductListModel.ProductListModelItem,
                        position: Int
                    ) {
                        context.toastHelper("click on ${model.productName}")
                    }

                }
            )
        }
    }

    fun filterItemsBySize(items: List<SingleProductModel.Item>, selectedSize: String): List<SingleProductModel.Item> {
        return items.filter { it.size == selectedSize }
    }

    override fun onFailed(message: String) {
        toastHelper(message)
    }

    private fun productId(): String {
        return intent.getStringExtra("PRO_ID") as String
    }

    companion object {
        fun lunch(context: Context, productId: String) {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("PRO_ID", productId)
            context.startActivity(intent)
        }
    }

}