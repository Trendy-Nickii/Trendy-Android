package com.kh.ite.rupp.edu.trendy.Ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.AddressListModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.AddressRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.ListAddressManagerAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnDeleteClick
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.Util.toastHelper
import com.kh.ite.rupp.edu.trendy.ViewModel.AddressViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.AddressViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.ActivityManageAddressBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class ManageAddressActivity : BaseActivityBinding<ActivityManageAddressBinding>(),
    OnBackResponse<AddToCartResponseModel> {
    override fun getLayoutViewBinding(): ActivityManageAddressBinding = ActivityManageAddressBinding.inflate(layoutInflater)

    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: AddressViewModelFactory
    private lateinit var addressRepository: AddressRepository
    private var viewModel: AddressViewModel? = null
    private var firstRun = true
    private var locationBack: ActivityResultLauncher<Intent>? = null
    private var dialogX: DialogX? = null
    private var adapterX : ListAddressManagerAdapter? = null

    private var positionItem = 0

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
            adapterX = ListAddressManagerAdapter(context, item, object :OnItemClick<AddressListModel.Addres>{
                override fun onItemClickListener(model: AddressListModel.Addres, position: Int) {
                    AddressDetailActivity.lunchUpdate(context, model.id.toString(), true)
                    ADDRESS_UPDATE = false
                }

            }, object : OnDeleteClick<AddressListModel.Addres>{
                override fun onDeleteListenerWithPos(
                    model: AddressListModel.Addres,
                    position: Int
                ) {
                    DialogX(context).showQuestion(
                        "Remaining",
                        "Are you sure to delete this address?"
                    ){
                        positionItem = position
                        viewModel?.deleteAddress(model.id.toString())
                    }

                }

            })
            adapter =adapterX
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("UPDATE_ADDRESS", "onResume: =====================onResume===================")
//        if (firstRun){
//            firstRun = false
//        }else{
//            initListAddress(viewModel?.addressList!!.value!!.address!!)
//        }
        if (ADDRESS_UPDATE){
            dialogX?.showProgress()
            viewModel?.getAddressList()
            viewModel?.addressList?.observe(this, Observer {
                initListAddress(it.address!!)
                dialogX?.dismissX()
            })
        }
    }

    companion object{
        fun lunch(context: Context){
            val intent = Intent(context, ManageAddressActivity::class.java)
            context.startActivity(intent)
        }
        var ADDRESS_UPDATE = false
    }

    override fun success(message: AddToCartResponseModel) {
        adapterX?.removeItem(positionItem)
        toastHelper(message.message!!)
    }

    override fun fail(message: String) {
        toastHelper(message)
    }
}