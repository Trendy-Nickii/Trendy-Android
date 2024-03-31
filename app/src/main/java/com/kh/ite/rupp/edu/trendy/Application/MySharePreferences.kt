package com.kh.ite.rupp.edu.trendy.Application

import android.content.Context
import android.content.SharedPreferences

class MySharePreferences(context: Context) {
    private val PREFERENCES_NAME = "MyAppPreferences"
    private val TOKEN_KEY = "token"
    private val USER_ID = "userid"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token:String){
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun saveUserId(userId: Int){
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }
    fun getUserId():Int{
        return sharedPreferences.getInt(USER_ID, 0)
    }

    fun getToken():String?{
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken(){
        val editor = sharedPreferences.edit()
        editor.remove(TOKEN_KEY)
        editor.remove(USER_ID)
        editor.apply()
    }
}