package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.databinding.FragmentOrderBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class OrderFragment : BaseFragmentBinding<FragmentOrderBinding>() {
    override fun getViewBinding(): FragmentOrderBinding = FragmentOrderBinding.inflate(layoutInflater)
    override fun onViewCreated() {

    }

}