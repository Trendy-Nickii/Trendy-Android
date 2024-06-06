package com.kh.ite.rupp.edu.trendy.Model

import java.io.Serializable

data class DeliveryMethodModel(
    var id: Int?=0,
    var delivery: String?="",
    var img: Int?=0,
    var jsonDelivery:String?="",
    var isSelect: Boolean? = false
):Serializable
