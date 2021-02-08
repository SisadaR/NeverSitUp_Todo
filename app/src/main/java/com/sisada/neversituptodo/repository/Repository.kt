package com.sisada.neversituptodo.repository

import androidx.lifecycle.liveData
import com.sisada.neversituptodo.api.Resource
import com.sisada.neversituptodo.api.TodoApi
import com.sisada.neversituptodo.model.Task
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



    fun logout(token:String) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = todoApi.logout(token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }


    fun getAllTask(
        token: String,
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = todoApi.getAllTasks(token)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }

    fun addTask(
        token: String,
        task: Task
    ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val data = hashMapOf(
                "description" to task.description
            )
            emit(Resource.success(data = todoApi.addTask(token,data)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }

    fun updateTask(
        token: String,
        task: Task,

        ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            val data = hashMapOf<String,Any>(
                "description" to task.description,
                "completed" to task.completed
            )
            emit(Resource.success(data = todoApi.updateTask(token,data,task._id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }

    fun deleteTask(
        token: String,
        task:Task,

        ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {

            emit(Resource.success(data = todoApi.deleteTask(token,task._id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }

}