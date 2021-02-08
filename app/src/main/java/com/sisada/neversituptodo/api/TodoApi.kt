package com.sisada.neversituptodo.api

import com.sisada.neversituptodo.etc.BASE_URL
import com.sisada.neversituptodo.model.LoginResponse
import com.sisada.neversituptodo.model.RegisterResponse
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