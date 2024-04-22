package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import com.kh.ite.rupp.edu.trendy.MainActivity
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.databinding.FragmentProfileBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>() {
    override fun getViewBinding(): FragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater)
    private lateinit var mySharePreferences: MySharePreferences
    override fun onViewCreated() {
        mySharePreferences = MySharePreferences(requireContext())
        val dialog = DialogX(requireContext())
        binding.logout.setOnClickListener {
            DialogX(requireContext()).showQuestion(
                "Are you sure?",
               "Do you want to log out?"
            ){
                logOut()
                MainActivity.lunch(requireContext())
                requireActivity().finish()
            }
        }
    }


    private fun logOut(){
        mySharePreferences.clearToken()
    }

}