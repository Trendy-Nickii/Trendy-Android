package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.ShopViewModelFactory
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryHandleData
import com.kh.ite.rupp.edu.trendy.Model.SubCategoryModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.ShopRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ListSubCategoryAdapter
import com.kh.ite.rupp.edu.trendy.Ui.view.AllProductActivity
import com.kh.ite.rupp.edu.trendy.ViewModel.ShopViewModel
import com.kh.ite.rupp.edu.trendy.databinding.FragmentSubCategoryListBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding


class SubCategoryListFragment(private var idCategory:Int, private var categoryName: String) : BaseFragmentBinding<FragmentSubCategoryListBinding>() {

    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var shopRepository: ShopRepository
    private lateinit var factory : ShopViewModelFactory
    private var viewModel : ShopViewModel? = null
    override fun getViewBinding(): FragmentSubCategoryListBinding = FragmentSubCategoryListBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        shopRepository = ShopRepository(api)
        factory = ShopViewModelFactory(shopRepository)
        viewModel = ViewModelProvider(this, factory).get(ShopViewModel::class.java)
        viewModel?.getSubCategoryData(idCategory.toString())

    }
    override fun onViewCreated() {

        val animationLayout = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_item_scale)
        viewModel?.subCategoryData?.observe(viewLifecycleOwner, Observer {
            binding.recSubCategory.apply {
                layoutAnimation = animationLayout
                layoutManager = LinearLayoutManager(context)
                adapter = ListSubCategoryAdapter(context, it, object :ListSubCategoryAdapter.OnClickListenerItem{
                    override fun onItemClick(data: SubCategoryModel.SubCategoryModelItem) {
                        AllProductActivity.lunch(context, SubCategoryHandleData(
                            categoryId = idCategory.toString(),
                            categoryNameHandle = categoryName,
                            subCategoryId = data.id.toString(),
                            isViewByCategory = true,
                            isViewAllHandle = false,
                            subCategoryName = data.name
                        ))

                    }

                })
            }
        })

    }

}