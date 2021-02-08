package com.sisada.neversituptodo.etc

import android.app.Activity
import android.app.AlertDialog
import com.sisada.neversituptodo.R

class WaitDialog(private val activity: Activity) {

    lateinit var dialog: AlertDialog

    fun show(){
        var builder =  AlertDialog.Builder(activity)
        builder.setView(activity.layoutInflater.inflate(R.layout.wait_dialog,null))
        //builder.setCancelable(true)

        dialog = builder.create()
        dialog.show()
    }

    fun dismiss(){
        dialog.dismiss()
    }
}