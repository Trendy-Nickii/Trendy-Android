package com.kh.ite.rupp.edu.trendy.Ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Factory.ShopViewModelFactory
import com.kh.ite.rupp.edu.trendy.Model.ListProductWithDetailByCategory
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryHandleData
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.ShopRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductListAdapterForGridLayout
import com.kh.ite.rupp.edu.trendy.Ui.adapter.SubCategoryListHorizontalAdapter
import com.kh.ite.rupp.edu.trendy.ViewModel.ShopViewModel
import com.kh.ite.rupp.edu.trendy.databinding.ActivityAllProductBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class AllProductActivity : BaseActivityBinding<ActivityAllProductBinding>() {

    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var shopRepository: ShopRepository
    private lateinit var factory : ShopViewModelFactory
    private var viewModel : ShopViewModel? = null

    private var grid = true
    private var gridLayoutManager: GridLayoutManager? = null
    private var adapterPro: ProductListAdapterForGridLayout? = null
    override fun getLayoutViewBinding(): ActivityAllProductBinding = ActivityAllProductBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        shopRepository = ShopRepository(api)
        factory = ShopViewModelFactory(shopRepository)
        viewModel = ViewModelProvider(this, factory).get(ShopViewModel::class.java)
        binding.backBtn.setOnClickListener { finish() }
        gridLayoutManager = GridLayoutManager(this, SPAN_COUNT_TWO)
        if (handleDataModel().isViewByCategory == true) {
            viewModel?.getSubCategoryData(handleDataModel().categoryId!!)
            viewModel?.getListProductByCategory(handleDataModel().subCategoryId!!)
            binding.titleLead.text = "${handleDataModel().categoryNameHandle}'s ${handleDataModel().subCategoryName}"
        }
        binding.listChange.setOnClickListener {
            grid = !grid
            switchLayout()

            if (grid) {
                binding.listChange.setImageResource(R.drawable.grid_svgrepo_com)

            } else {
                binding.listChange.setImageResource(R.drawable.list_ul)
            }
        }

        if (handleDataModel().isViewByCategory == true) {
            viewModel?.subCategoryData?.observe(this, Observer {
                initSubCategoryRec(it)
            })
        }



        viewModel?.listProductByCategory?.observe(this, Observer {
            val dataX = ListProductWithDetailByCategory()
            var y = 1
            while (y != 10) {
                dataX.addAll(it)
                y++
            }
            adapterPro = ProductListAdapterForGridLayout(this, dataX, gridLayoutManager!!, object :
                ProductListAdapterForGridLayout.OnProductClick {
                override fun onClickProduct(product: ListProductWithDetailByCategory.ListProductWithDetailByCategoryItem) {
                    ProductDetailActivity.lunch(this@AllProductActivity, product.id.toString())
                }

            })
            initProductRecGrid()
        })



    }

    override fun initView() {

    }

    private fun initProductRecGrid() {
        binding.productList.apply {
            layoutManager = gridLayoutManager
            adapter = adapterPro
        }
    }
    private fun initSubCategoryRec(data: SubCategoryModel) {
        binding.subCategoryRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = SubCategoryListHorizontalAdapter(
                context,
                data,
                object : SubCategoryListHorizontalAdapter.OnSubCategoryClick {
                    @SuppressLint("SetTextI18n")
                    override fun onItemClick(data: SubCategoryModel.SubCategoryModelItem) {
                        viewModel?.getListProductByCategory(data.id.toString())
                        binding.titleLead.text = "${handleDataModel().categoryNameHandle}'s ${data.name}"
                    }

                })
        }

    }

    private fun switchLayout() {
        if (gridLayoutManager?.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager?.spanCount = SPAN_COUNT_TWO
        } else {
            gridLayoutManager?.spanCount = SPAN_COUNT_ONE
        }

        adapterPro?.notifyItemRangeChanged(0, adapterPro!!.itemCount)

    }

    private fun handleDataModel(): SubCategoryHandleData{
        return intent.getSerializableExtra("HANDLE") as SubCategoryHandleData
    }

    companion object {
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_TWO = 2


        fun lunch(context: Context, handleDataModel: SubCategoryHandleData){
            val intent = Intent(context, AllProductActivity::class.java)
            intent.putExtra("HANDLE", handleDataModel)
            context.startActivity(intent)
        }

    }
}