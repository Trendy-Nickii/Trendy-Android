package com.kh.ite.rupp.edu.trendy.Ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.AddressRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ListChooseItemAddressAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.ViewModel.AddressViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.AddressViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.ActivityAddressBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class AddressActivity : BaseActivityBinding<ActivityAddressBinding>(),
    OnBackResponse<AddToCartResponseModel> {
    override fun getLayoutViewBinding(): ActivityAddressBinding = ActivityAddressBinding.inflate(layoutInflater)
    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: AddressViewModelFactory
    private lateinit var addressRepository: AddressRepository
    private var viewModel: AddressViewModel? = null
    private var firstRun = true
    private var dialogX: DialogX? = null
    private var adapterX : ListChooseItemAddressAdapter? = null
    private var itemAddress = AddressListModel.Addres()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogX = DialogX(this)
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(this, networkConnectionInterceptor)
        addressRepository = AddressRepository(api)
        factory = AddressViewModelFactory(addressRepository)
        viewModel = ViewModelProvider(this, factory).get(AddressViewModel::class.java)
        viewModel?.listener = this
        viewModel?.getAddressList()
        viewModel?.addressList?.observe(this, Observer {
//            itemAddress = it.address!!
//            itemAddress[0].isSelect = true
            if (getAddId() == ""){
               if(it.address.isNullOrEmpty()){

               }else{
                   it.address!![0].isSelect = true
                   itemAddress = it.address!![0]
               }
            }else{
                for (i in it.address!!){
                    if (i.id.toString() == getAddId()){
                        i.isSelect = true
                        itemAddress = i
                    }
                }
            }
            initListAddress(it.address!!)
        })
    }
    override fun initView() {
        binding.btnAdd.setOnClickListener {
            ManageAddressActivity.lunch(this)
        }
        binding.backBtnLogin.setOnClickListener { finish() }

        binding.confirmOrderBtn.setOnClickListener {
            val intent = Intent().apply {
                putExtra("DATA_BACK", itemAddress)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun initListAddress(item: ArrayList<AddressListModel.Addres>){
        binding.addressRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterX = ListChooseItemAddressAdapter(context, item, object :
                OnItemClick<AddressListModel.Addres> {
                override fun onItemClickListener(model: AddressListModel.Addres, position: Int) {
                    item[position].isSelect = true
                    itemAddress = item[position]
                    item.forEachIndexed { index, item ->
                        item.isSelect = (index == position)
                    }
                    adapter?.notifyDataSetChanged()
                }

            })
            adapter =adapterX
        }
    }

    private fun getAddId(): String{
        return intent.getStringExtra("ADD_ID") as String
    }

    companion object{
        fun lunch(context: Context){
            val intent = Intent(context, AddressActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun success(message: AddToCartResponseModel) {

    }

    override fun fail(message: String) {
        toastHelper(message)
    }

}