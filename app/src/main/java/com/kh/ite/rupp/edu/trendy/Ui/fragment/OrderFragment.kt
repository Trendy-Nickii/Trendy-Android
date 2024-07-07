package com.kh.ite.rupp.edu.trendy.Ui.fragment

import com.google.android.material.tabs.TabLayoutMediator
import com.kh.ite.rupp.edu.trendy.Model.TopCategoryListModel
import com.kh.ite.rupp.edu.trendy.Util.BaseTabOrder
import com.kh.ite.rupp.edu.trendy.databinding.FragmentOrderBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class OrderFragment : BaseFragmentBinding<FragmentOrderBinding>() {
    override fun getViewBinding(): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater)
    override fun onViewCreated() {
        val dataTap : TopCategoryListModel = TopCategoryListModel()
        dataTap.add(TopCategoryListModel.TopCategoryListModelItem(id = 1, name = "Ordering"))
        dataTap.add(TopCategoryListModel.TopCategoryListModelItem(id = 2, name = "History"))

        setUpTab(dataTap, BaseTabOrder(requireActivity(), dataTap))

    }

    private fun setUpTab(data: TopCategoryListModel, adapter: BaseTabOrder){

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