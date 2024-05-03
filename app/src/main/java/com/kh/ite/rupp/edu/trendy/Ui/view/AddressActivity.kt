package com.kh.ite.rupp.edu.trendy.Ui.view

import android.content.Context
import android.content.Intent
import com.kh.ite.rupp.edu.trendy.databinding.ActivityAddressBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class AddressActivity : BaseActivityBinding<ActivityAddressBinding>() {
    override fun getLayoutViewBinding(): ActivityAddressBinding = ActivityAddressBinding.inflate(layoutInflater)

    override fun initView() {
        binding.btnAdd.setOnClickListener {
            ManageAddressActivity.lunch(this)
        }
        binding.backBtnLogin.setOnClickListener { finish() }
    }

    companion object{
        fun lunch(context: Context){
            val intent = Intent(context, AddressActivity::class.java)
            context.startActivity(intent)
        }
    }

}