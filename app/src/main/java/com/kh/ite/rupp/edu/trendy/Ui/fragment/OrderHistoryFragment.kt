package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.AddToCartResponseModel
import com.kh.ite.rupp.edu.trendy.Model.UserOrderingModel
import com.kh.ite.rupp.edu.trendy.Service.MyApi
import com.kh.ite.rupp.edu.trendy.Service.intercepter.NetworkConnectionInterceptor
import com.kh.ite.rupp.edu.trendy.Service.repository.CartRepository
import com.kh.ite.rupp.edu.trendy.Ui.adapter.CartItemAdapter
import com.kh.ite.rupp.edu.trendy.Ui.adapter.UserOrderingAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.DialogX
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnBackResponse
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.ViewModel.CartViewModel
import com.kh.ite.rupp.edu.trendy.ViewModel.Factory.CartViewModelFactory
import com.kh.ite.rupp.edu.trendy.databinding.FragmentOrderHistoryBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class OrderHistoryFragment : BaseFragmentBinding<FragmentOrderHistoryBinding>(),
    OnBackResponse<AddToCartResponseModel> {
    override fun getViewBinding(): FragmentOrderHistoryBinding = FragmentOrderHistoryBinding.inflate(layoutInflater)

    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: CartViewModelFactory
    private lateinit var cartRepository: CartRepository
    private var viewModel: CartViewModel? = null
    private var cartAdapter : CartItemAdapter? = null
    private var dialogX : DialogX? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        networkConnectionInterceptor = NetworkConnectionInterceptor()
        api = MyApi(requireContext(), networkConnectionInterceptor)
        cartRepository = CartRepository(api)
        factory = CartViewModelFactory(cartRepository)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        viewModel?.listener = this
        dialogX = DialogX(context)
        viewModel?.getUserOrderingHistory()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated() {
        viewModel?.orderingHistory?.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()){
                binding.orderingRec.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
                binding.noData.text = "Your cart is Empty"
            }else{
                initRListOrdering(it)
            }
        })
    }

    private fun initRListOrdering(data: UserOrderingModel){
        binding.orderingRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = UserOrderingAdapter(context, data, object : OnItemClick<UserOrderingModel.UserOrderingItem> {
                override fun onItemClickListener(
                    model: UserOrderingModel.UserOrderingItem,
                    position: Int
                ) {

                }

            })
        }
    }
    override fun success(message: AddToCartResponseModel) {

    }

    override fun fail(message: String) {

    }

}