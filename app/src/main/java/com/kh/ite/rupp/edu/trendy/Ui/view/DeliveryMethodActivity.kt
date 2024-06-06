package com.kh.ite.rupp.edu.trendy.Ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.kh.ite.rupp.edu.trendy.Model.DeliveryMethodModel
import com.kh.ite.rupp.edu.trendy.R
import com.kh.ite.rupp.edu.trendy.Ui.adapter.DeliveryAdapter
import com.kh.ite.rupp.edu.trendy.Ui.custom.OnItemClick
import com.kh.ite.rupp.edu.trendy.databinding.ActivityDeliveryMethodBinding
import kh.edu.rupp.ite.trendy.Base.BaseActivityBinding

class DeliveryMethodActivity : BaseActivityBinding<ActivityDeliveryMethodBinding>() {
    private var deliveryMethod : ArrayList<DeliveryMethodModel> = arrayListOf()
    private var adapterX : DeliveryAdapter? = null
    private var deliveryItem : DeliveryMethodModel = DeliveryMethodModel()
    override fun getLayoutViewBinding(): ActivityDeliveryMethodBinding = ActivityDeliveryMethodBinding.inflate(layoutInflater)

    override fun initView() {
        initDataDelivery()
        if (getDeliveryId() == ""){
            deliveryMethod[0].isSelect = true
            deliveryItem = deliveryMethod[0]
        }else{
            for (i in deliveryMethod){
                if (i.id.toString() == getDeliveryId()){
                    i.isSelect = true
                    deliveryItem = i
                }
            }
        }
        initListAddress(deliveryMethod)

        binding.okBtn.setOnClickListener {
            val intent = Intent().apply {
                putExtra("DATA_BACK", deliveryItem)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


        binding.backBtnLogin.setOnClickListener { finish() }

    }


    private fun getDeliveryId(): String{
        return intent.getStringExtra("ADD_ID") as String
    }

    private fun initListAddress(item: ArrayList<DeliveryMethodModel>){
        binding.deliveryRec.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterX = DeliveryAdapter(context, item, object :
                OnItemClick<DeliveryMethodModel> {
                override fun onItemClickListener(model: DeliveryMethodModel, position: Int) {
                    item[position].isSelect = true
                    deliveryItem = item[position]
                    item.forEachIndexed { index, item ->
                        item.isSelect = (index == position)
                    }
                    adapter?.notifyDataSetChanged()
                }

            })
            adapter =adapterX
        }
    }
    private fun initDataDelivery(){
        deliveryMethod.add(DeliveryMethodModel(1,"Food Panda", R.drawable.food_panda_log, "food_panda", false))
        deliveryMethod.add(DeliveryMethodModel(2,"J&T Express Cambodia", R.drawable.jt_logo, "j&t", false))
        deliveryMethod.add(DeliveryMethodModel(3,"Grab", R.drawable.grab_official, "grab", false))
        deliveryMethod.add(DeliveryMethodModel(4,"Wownow Cambodia", R.drawable.wownow, "wownow", false))
        deliveryMethod.add(DeliveryMethodModel(5,"Vireak Buntham Express", R.drawable.vireak, "vireak", false))

    }
    companion object{
        fun lunch(context: Context){
            val intent = Intent(context, DeliveryMethodActivity::class.java)
            context.startActivity(intent)
        }
    }

}