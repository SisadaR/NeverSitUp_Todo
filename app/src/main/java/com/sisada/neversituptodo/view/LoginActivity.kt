package com.sisada.neversituptodo.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.sisada.neversituptodo.api.Status
import com.sisada.neversituptodo.databinding.ActivityLoginBinding
import com.sisada.neversituptodo.etc.PASSWORD_MIN_LENGTH
import com.sisada.neversituptodo.etc.SharedInfo
import com.sisada.neversituptodo.etc.Validator
import com.sisada.neversituptodo.etc.WaitDialog
import com.sisada.neversituptodo.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private var viewModel: AuthViewModel = AuthViewModel()
    lateinit var binding: ActivityLoginBinding
    private var validator = Validator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        this.setUpValidation()
        this.setupOnLoginButtonClicked()
        this.setupOnRegisterButtonClicked()
    }

    private fun setupOnRegisterButtonClicked() {
       binding.btnRegister.setOnClickListener {
           val intent = Intent(this, RegisterActivity::class.java)
           startActivity(intent)
           finish()
       }
    }

    private fun setUpValidation(){
        validator.addCheckEmail(binding.tinEmail)

        validator.addCheckEmpty(binding.tinEmail)
        validator.addCheckEmpty(binding.tinPassword)

        validator.addCheckTooShort(binding.tinPassword,PASSWORD_MIN_LENGTH)
    }

    private fun setupOnLoginButtonClicked() {


        binding.btnLogin.setOnClickListener {

            if (!validator.result())
                return@setOnClickListener

            var waitDialog = WaitDialog(this)
            waitDialog.show()

            val password = binding.tinPasswordValue.text.toString()
            val email = binding.tinEmailValue.text.toString()
            viewModel.login(email, password).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            waitDialog.dismiss()
                            resource.data?.let { response ->
                                SharedInfo.newToken(this, response.token)
                                gotoTaskActivity()
                            }
                        }
                        Status.ERROR -> {
                            waitDialog.dismiss()
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

