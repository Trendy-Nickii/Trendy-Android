package com.kh.ite.rupp.edu.trendy.Model

data class CreateAddressBody(
    private var recipient_phone: String,
    private var address_name: String,
    private var recipient_name: String,
    private var address_line: String,
    private var latitude: String,
    private var longitude: String,
    private var khan:String

)
