package com.sisada.neversituptodo.view

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.sisada.neversituptodo.api.Status
import com.sisada.neversituptodo.databinding.ActivityLoginBinding
import com.sisada.neversituptodo.etc.SharedInfo
import com.sisada.neversituptodo.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private var viewModel: AuthViewModel = AuthViewModel()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

   
        this.onLoginButtonClicked()
    }

    private fun onLoginButtonClicked() {


        binding.btnLogin.setOnClickListener {


            val password = binding.tinPasswordValue.text.toString()
            val email = binding.tinEmailValue.text.toString()
            viewModel.login(email, password).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data?.let { response ->
                                SharedInfo.newToken(this, response.token)
                                gotoTaskActivity()
                            }

                        }
                        Status.ERROR -> {
                            Log.e("LoginError", resource.message!!)

                            AlertDialog.Builder(this)
                                .setTitle("Something Wrong")
                                .setMessage(resource.message)
                                .setPositiveButton(android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show()
                        }
                        Status.LOADING -> {
                            Log.d("Login", "Loading")
                        }
                    }
                }
            })


        }
    }

    private fun gotoTaskActivity() {
        Log.d("LOGIN_ACTICITY" , "go to task view")
    }


}

