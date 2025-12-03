package com.example.cineplus.data.remote.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?
)

data class LoginData(
    val access_token: String,
    val user: UserData
)

data class UserData(
    val _id: String,
    val email: String,
    val role: String
)
