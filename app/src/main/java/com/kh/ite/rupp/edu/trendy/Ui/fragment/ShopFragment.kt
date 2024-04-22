package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.kh.ite.rupp.edu.trendy.Factory.ShopViewModelFactory
import com.kh.ite.rupp.edu.trendy.Model.TopCategoryListModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.ShopRepository
import com.kh.ite.rupp.edu.trendy.Util.BaseTabBase
import com.kh.ite.rupp.edu.trendy.ViewModel.ShopViewModel
import com.kh.ite.rupp.edu.trendy.databinding.FragmentShopBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class ShopFragment : BaseFragmentBinding<FragmentShopBinding>() {
    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var shopRepository: ShopRepository
    private lateinit var factory : ShopViewModelFactory
    private var viewModel : ShopViewModel? = null
    override fun getViewBinding(): FragmentShopBinding = FragmentShopBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        shopRepository = ShopRepository(api)
        factory = ShopViewModelFactory(shopRepository)
        viewModel = ViewModelProvider(this, factory).get(ShopViewModel::class.java)
        viewModel?.getTopCategoryData()
    }
    override fun onViewCreated() {
        viewModel?.topCategoryData?.observe(viewLifecycleOwner, Observer {
            Log.d("CATEGORY", "category = $it")
            setUpTab(it, BaseTabBase(requireActivity(),it))

        })
    }

    private fun setUpTab(data: TopCategoryListModel, adapter: BaseTabBase){

        binding.vwSr.adapter = adapter
        val tabLayout = TabLayoutMediator(
            binding.tabSr,
            binding.vwSr
        ){tab, pos ->

            tab.text = data[pos].name

        }

        tabLayout.attach()

    }


}