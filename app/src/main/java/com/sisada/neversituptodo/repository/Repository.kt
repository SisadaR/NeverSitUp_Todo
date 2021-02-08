package com.sisada.neversituptodo.repository

import androidx.lifecycle.liveData
import com.sisada.neversituptodo.api.Resource
import com.sisada.neversituptodo.api.TodoApi
import kotlinx.coroutines.Dispatchers

object Repository {
    private val todoApi by lazy { TodoApi.create()}

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {

            val data = hashMapOf(
                "email" to email,
                "password" to password
            )
            emit(Resource.success(data = todoApi.login(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }

    fun register(
        name: String,
        email: String,
        password: String,
        age: Int
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {

            val data = hashMapOf<String,Any>(
                "name" to name,
                "email" to email,
                "password" to password,
                "age" to age
            )
            emit(Resource.success(data = todoApi.register(data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }



}