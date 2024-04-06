package com.kh.ite.rupp.edu.trendy.Util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.DecimalFormat
import java.text.NumberFormat

fun hideKeyboard(view: View, activity: Activity) {
    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
}

fun totalPriceFormat(price: Double): String {
    val numberFormat: NumberFormat = DecimalFormat("#,##0.00")
    numberFormat.maximumFractionDigits = 2
    return numberFormat.format(price)
}
fun calculateDiscount(dis: Double, unitPrice: Double): Double {
    return unitPrice * (100 - dis) / 100
}
fun Context.toastHelper(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}