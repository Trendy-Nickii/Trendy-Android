package com.kh.ite.rupp.edu.trendy.Ui.fragment

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.databinding.FragmentHomeBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
    override fun onViewCreated() {
        initBanner()
    }



    private fun initBanner() {
        val slider : ArrayList<SlideModel> = arrayListOf()
        slider.add(SlideModel(R.drawable.img_test, ScaleTypes.FIT))
        binding.imageSlider.setImageList(slider, ScaleTypes.FIT)
    }
}