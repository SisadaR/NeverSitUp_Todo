package com.sisada.neversituptodo.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.api.Status
import com.sisada.neversituptodo.databinding.ActivityLoginBinding
import com.sisada.neversituptodo.databinding.ActivityRegisterBinding
import com.sisada.neversituptodo.etc.*
import com.sisada.neversituptodo.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private var viewModel: AuthViewModel = AuthViewModel()
    lateinit var binding: ActivityRegisterBinding
    private var validator = Validator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setUpValidation()
        this.setupOnLoginButtonClicked()
        this.setupOnRegisterButtonClicked()
    }

    private fun setupOnLoginButtonClicked() {
        binding.btnGotoLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupOnRegisterButtonClicked() {

        binding.btnRegister.setOnClickListener {

            if (!validator.result())
                return@setOnClickListener

            var waitDialog = WaitDialog(this)
            waitDialog.show()

            val username = binding.tinNameValue.text.toString()
            val password = binding.tinPasswordValue.text.toString()
            val email = binding.tinEmailValue.text.toString()
            val age = binding.tinAgeValue.text.toString().toInt()

            viewModel.register(username,email, password,age).observe(this, Observer {
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

    private fun setUpValidation(){
        validator.addCheckEmail(binding.tinEmail)

        validator.addCheckEmpty(binding.tinEmail)
        validator.addCheckEmpty(binding.tinPassword)
        validator.addCheckEmpty(binding.tinName)
        validator.addCheckEmpty(binding.tinAge)

        validator.addCheckTooShort(binding.tinPassword, PASSWORD_MIN_LENGTH)

        validator.addCheckNumberRange(binding.tinAge, IntRange(AGE_MIN, AGE_MAX))
    }

    private fun gotoTaskActivity() {
        val intent = Intent(this, TaskActivity::class.java)
        startActivity(intent)
        finish()
    }
}