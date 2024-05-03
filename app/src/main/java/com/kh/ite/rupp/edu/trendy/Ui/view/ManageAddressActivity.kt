package com.kh.ite.rupp.edu.trendy.Ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.AddressRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ListAddressManagerAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.ViewModel.AddressViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.AddressViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.ActivityManageAddressBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class ManageAddressActivity : BaseActivityBinding<ActivityManageAddressBinding>() {
    override fun getLayoutViewBinding(): ActivityManageAddressBinding = ActivityManageAddressBinding.inflate(layoutInflater)

    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: AddressViewModelFactory
    private lateinit var addressRepository: AddressRepository
    private var viewModel: AddressViewModel? = null
    private var firstRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        addressRepository = AddressRepository(api)
        factory = AddressViewModelFactory(addressRepository)
        viewModel = ViewModelProvider(this, factory).get(AddressViewModel::class.java)
        viewModel?.getAddressList()
        viewModel?.addressList?.observe(this, Observer {
            initListAddress(it.address!!)
        })
    }
    override fun initView() {
        binding.btnAdd.setOnClickListener {
            MapAddressActivity.lunch(this)
        }
        binding.backBtnLogin.setOnClickListener { finish() }


    }


    private fun initListAddress(item: ArrayList<AddressListModel.Addres>){
        binding.addressRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListAddressManagerAdapter(context, item, object :OnItemClick<AddressListModel.Addres>{
                override fun onItemClickListener(model: AddressListModel.Addres, position: Int) {

                }

            })
        }
    }

//    override fun onResume() {
//        super.onResume()
//        if (firstRun){
//            firstRun = false
//        }else{
//            initListAddress(viewModel?.addressList!!.value!!.address!!)
//        }
//    }

    companion object{
        fun lunch(context: Context){
            val intent = Intent(context, ManageAddressActivity::class.java)
            context.startActivity(intent)
        }
    }
}