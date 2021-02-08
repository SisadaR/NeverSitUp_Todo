package com.sisada.neversituptodo.viewmodel

import androidx.lifecycle.ViewModel
import com.sisada.neversituptodo.repository.Repository

class AuthViewModel : ViewModel() {

    fun login(email:String,password:String) = Repository.login(email,password)
    fun register(name:String,email:String,password:String,age:Int) = Repository.register(name,email,password,age)
    fun logout(token:String) = Repository.logout(token)
}