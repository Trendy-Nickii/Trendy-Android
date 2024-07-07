package com.kh.ite.rupp.edu.trendy.Ui.fragment

import android.content.BroadcastReceiver
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
import com.kh.ite.rupp.edu.trendy.databinding.FragmentOrderingBinding
import kh.edu.rupp.ite.trendy.Base.BaseFragmentBinding

class OrderingFragment : BaseFragmentBinding<FragmentOrderingBinding>(), OnBackResponse<AddToCartResponseModel> {
    override fun getViewBinding(): FragmentOrderingBinding = FragmentOrderingBinding.inflate(layoutInflater)
    private lateinit var networkConnectionInterceptor : NetworkConnectionInterceptor
    private lateinit var api : MyApi
    private lateinit var factory: CartViewModelFactory
    private lateinit var cartRepository: CartRepository
    private var viewModel: CartViewModel? = null
    private var cartAdapter : CartItemAdapter? = null
    private var dialogX : DialogX? = null
    private lateinit var orderStatusReceiver: BroadcastReceiver
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
        viewModel?.getUserOrdering()


        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated() {
        viewModel?.ordering?.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()){
                binding.orderingRec.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
                binding.noData.text = "No order in progress"
            }else{
                initRListOrdering(it)
            }
        })
    }


    private fun initRListOrdering(data: UserOrderingModel){
        binding.orderingRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = UserOrderingAdapter(context, data, object : OnItemClick<UserOrderingModel.UserOrderingItem>{
                override fun onItemClickListener(
                    model: UserOrderingModel.UserOrderingItem,
                    position: Int
                ) {

                }

            })
        }
    }

//    private fun initSocket(){
//        // Initialize Socket.IO connection
//        val serverUrl = "http://10.0.2.2:5001"
//        SocketManager.initialize(requireContext(), serverUrl)
//// Register the BroadcastReceiver
//        orderStatusReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                val status = intent.getStringExtra("status")
//                val orderId = intent.getStringExtra("orderId")
//
//                // Update your UI here
//                handleOrderUpdate(status, orderId)
//            }
//        }
//        val intentFilter = IntentFilter("orderStatusUpdate")
//        requireContext().registerReceiver(orderStatusReceiver, intentFilter)
//    }
//    private fun handleOrderUpdate(status: String?, orderId: String?) {
//        // Handle the order update and refresh the UI as needed
//        // Example:
//        dialogX?.showSuccess("Test","Order $orderId status updated to $status")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        // Unregister the BroadcastReceiver
//        requireContext().unregisterReceiver(orderStatusReceiver)
//    }

    override fun success(message: AddToCartResponseModel) {

    }

    override fun fail(message: String) {

    }


}