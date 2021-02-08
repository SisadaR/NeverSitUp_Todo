package com.sisada.neversituptodo.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer

import com.sisada.neversituptodo.etc.*
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.api.Status
import com.sisada.neversituptodo.databinding.ActivityTaskBinding
import com.sisada.neversituptodo.etc.SharedInfo
import com.sisada.neversituptodo.etc.WaitDialog
import com.sisada.neversituptodo.viewmodel.AuthViewModel

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private lateinit var logoutViewModel:AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavDrawer()
    }

    private fun setupNavDrawer() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        binding.navViewTaskActivity.setNavigationItemSelectedListener {
            it.isChecked = true;
            when(it.itemId){
                R.id.nav_menu_logout -> {
                   logout()
                }
            }

            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }

    }

    private fun logout(){
        var waitDialog = WaitDialog(this)
        waitDialog.show()

        logoutViewModel = AuthViewModel()

        logoutViewModel.logout(SharedInfo.getToken(this)).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        waitDialog.dismiss()
                        resource.data?.let { response ->

                            if(resource.data.success){
                                SharedInfo.logout(this)
                                gotoTaskActivity()
                            }
                            else{
                                AlertDialog.Builder(this)
                                    .setTitle("Something Wrong")
                                    .setMessage("Can't logout")
                                    .setPositiveButton(android.R.string.yes, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show()
                            }

                        }
                    }
                    Status.ERROR -> {
                        waitDialog.dismiss()
                        Log.e("LogoutError", resource.message!!)

                        AlertDialog.Builder(this)
                            .setTitle("Something Wrong")
                            .setMessage(resource.message)
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                    Status.LOADING -> {
                        Log.d("Logout", "Loading")
                    }
                }
            }
        })
    }


    private fun gotoTaskActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}