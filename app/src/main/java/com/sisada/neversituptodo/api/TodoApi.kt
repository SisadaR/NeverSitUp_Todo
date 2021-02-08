package com.sisada.neversituptodo.api

import com.sisada.neversituptodo.etc.BASE_URL
import com.sisada.neversituptodo.model.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TodoApi {
    @POST("/user/register")
    @Headers("Content-Type: application/json")
    suspend fun register(
        @Body dataRaw: HashMap<String, Any>
    ) : RegisterResponse

    @POST("/user/login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body dataRaw:HashMap<String, String>
    ) : LoginResponse

    @POST("/user/logout")
    @Headers("Content-Type: application/json")
    suspend fun logout(
        @Header("Authorization") token:String
    ) : LogoutResponse

    @GET("/task")
    @Headers("Content-Type: application/json")
    suspend fun getAllTasks(
        @Header("Authorization") token:String
    ) : GetAllTaskResponse

    @POST("/task")
    @Headers("Content-Type: application/json")
    suspend fun addTask(
        @Header("Authorization") token:String,
        @Body dataRaw:HashMap<String, String>,
    ) : AddTaskResponse

    @PUT("/task/{id}")
    @Headers("Content-Type: application/json")
    suspend fun updateTask(
        @Header("Authorization") token:String,
        @Body dataRaw:HashMap<String, Any>,
        @Path(value = "id", encoded = true)userId:String
    ) :UpdateTaskResponse

    @DELETE("/task/{id}")
    @Headers("Content-Type: application/json")
    suspend fun deleteTask(
        @Header("Authorization") token:String,
        @Path(value = "id", encoded = true)userId:String
    ) :DeleteTaskResponse


    companion object {
        fun create(): TodoApi {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(TodoApi::class.java)
        }
    }

}