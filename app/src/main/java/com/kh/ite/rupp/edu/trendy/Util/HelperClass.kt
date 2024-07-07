package com.kh.ite.rupp.edu.trendy.Util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kh.ite.rupp.edu.trendy.Application.MySharePreferences
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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

fun isLoggedIn(context: Context): Boolean {
    // Add your logic to check if the user is logged in
    if (MySharePreferences(context).getToken().isNullOrEmpty()){
        return false
    }
    else{
        return true
    }
    // Placeholder, replace with actual logic
}


fun dateFormat(date: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH)
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
    val fullFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy 'at' hh:mm a", Locale.ENGLISH)

    // Parse the input date string
    val dateTime = ZonedDateTime.parse(date, inputFormatter).withZoneSameInstant(ZoneId.systemDefault())

    // Get the current date and time
    val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())

    // Determine if the date is today, yesterday, or earlier
    val isToday = dateTime.toLocalDate().isEqual(currentDateTime.toLocalDate())
    val isYesterday = dateTime.toLocalDate().isEqual(currentDateTime.minusDays(1).toLocalDate())

    // Format the date accordingly
    val finalFormattedDate = when {
        isToday -> "Today at ${dateTime.format(timeFormatter)}"
        isYesterday -> "Yesterday at ${dateTime.format(timeFormatter)}"
        else -> dateTime.format(fullFormatter)
    }

    return finalFormattedDate
}