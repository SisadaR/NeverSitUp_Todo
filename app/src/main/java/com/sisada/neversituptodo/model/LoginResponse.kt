package com.sisada.neversituptodo.model

data class LoginResponse (
    val token: String,
    val user: User
)