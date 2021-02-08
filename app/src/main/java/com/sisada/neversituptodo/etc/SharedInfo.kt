package com.sisada.neversituptodo.etc

import android.content.Context

const val PREF_FILE = "todo"
const val PREF_KEY_TOKEN = "token"
object SharedInfo{


    fun getToken(context: Context): String{
        var sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PREF_KEY_TOKEN,"")!!
    }

    fun newToken(context: Context, token:String){

        var sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PREF_KEY_TOKEN,token).apply()
    }

    fun logout(context: Context){
        newToken(context,"")
    }

}
