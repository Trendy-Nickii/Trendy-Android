package com.kh.ite.rupp.edu.trendy.Util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kh.ite.rupp.edu.trendy.Model.TopCategoryListModel
import com.kh.ite.rupp.edu.trendy.Ui.fragment.OrderHistoryFragment
import com.kh.ite.rupp.edu.trendy.Ui.fragment.OrderingFragment


class BaseTabOrder(
    fragmentActivity: FragmentActivity,
    private val data: TopCategoryListModel
):FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
         if (data[position].id == 1){
             return OrderingFragment()
        }else{
            return OrderHistoryFragment()
         }
    }

}