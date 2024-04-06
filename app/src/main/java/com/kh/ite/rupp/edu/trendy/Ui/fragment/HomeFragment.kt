package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.kh.ite.rupp.edu.trendy.Factory.ProductViewModelFactory
import com.kh.ite.rupp.edu.trendy.Model.ProductListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.ProductRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ProductListAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnRequestResponse
import com.kh.ite.rupp.edu.trendy.Ui.view.ProductDetailActivity
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.ViewModel.HomeScreenViewModel
import com.kh.ite.rupp.edu.trendy.databinding.FragmentHomeBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>(), OnRequestResponse {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    private lateinit var networkConnectionInterceptor: NetworkConnectionInterceptor
    private lateinit var api: MyApi
    private lateinit var productRepository: ProductRepository
    private lateinit var factory: ProductViewModelFactory
    private var viewModel: HomeScreenViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        productRepository = ProductRepository(api)
        factory = ProductViewModelFactory(productRepository)
        viewModel = ViewModelProvider(this, factory).get(HomeScreenViewModel::class.java)
        viewModel?.listener = this
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated() {
        initBanner()
        bannerSkincare()
        showSkeleton()

        viewModel?.productList?.observe(viewLifecycleOwner, Observer {productList ->
            hideSkeleton()
            initNewArrivalList(productList)
            initHotDealList(productList)
            initSkincareList(productList)
            initSkincareSummerList(productList)
        })




    }
    private fun showSkeleton() {
        // Show skeleton layout
        binding.loading.visibility = View.VISIBLE
        // Hide RecyclerViews
        binding.newArrivalSection.visibility = View.GONE
        binding.hotDealSection.visibility = View.GONE
        binding.skincareSection.visibility = View.GONE
        binding.skincareSummerSection.visibility = View.GONE
        binding.cardBanner2.visibility = View.GONE
    }
    private fun hideSkeleton() {
        // Hide skeleton layout
        binding.loading.visibility = View.GONE
        // Show RecyclerViews
        binding.newArrivalSection.visibility = View.VISIBLE
        binding.hotDealSection.visibility = View.VISIBLE
        binding.skincareSection.visibility = View.VISIBLE
        binding.skincareSummerSection.visibility = View.VISIBLE
        binding.cardBanner2.visibility = View.VISIBLE

    }
    private fun initNewArrivalList(dataItem: ProductListModel){
        binding.recNewArrival.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductListAdapter(
                context,
                dataItem,
                object : OnItemClick<ProductListModel.ProductListModelItem>{
                    override fun onItemClickListener(
                        model: ProductListModel.ProductListModelItem,
                        position: Int
                    ) {
                        ProductDetailActivity.lunch(context, model.id.toString())
                    }

                }
            )
        }
    }

    private fun initHotDealList(dataItem: ProductListModel){
        binding.recHotDeal.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductListAdapter(
                context,
                dataItem,
                object : OnItemClick<ProductListModel.ProductListModelItem>{
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

    private fun initSkincareList(dataItem: ProductListModel){
        binding.skincareRec.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductListAdapter(
                context,
                dataItem,
                object : OnItemClick<ProductListModel.ProductListModelItem>{
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

    private fun initSkincareSummerList(dataItem: ProductListModel){
        binding.skincareSummerRec.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = ProductListAdapter(
                context,
                dataItem,
                object : OnItemClick<ProductListModel.ProductListModelItem>{
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



    private fun initBanner() {
        val slider : ArrayList<SlideModel> = arrayListOf()
        slider.add(SlideModel(R.drawable.img_test, ScaleTypes.FIT))
        binding.imageSlider.setImageList(slider, ScaleTypes.FIT)
    }
    private fun bannerSkincare(){
        val slider : ArrayList<SlideModel> = arrayListOf()
        slider.add(SlideModel(R.drawable.sincare_banner, ScaleTypes.FIT))
        binding.imageSlider2.setImageList(slider, ScaleTypes.FIT)
    }

    override fun onFailed(message: String) {
        context?.toastHelper(message)
    }
}