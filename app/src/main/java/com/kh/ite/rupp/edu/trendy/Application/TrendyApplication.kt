package com.kh.ite.rupp.edu.trendy.Application

import android.app.Application

class TrendyApplication :Application(){

    override fun onCreate() {
        super.onCreate()

    }



    companion object{
        var isInternetConnected = true

    }
}