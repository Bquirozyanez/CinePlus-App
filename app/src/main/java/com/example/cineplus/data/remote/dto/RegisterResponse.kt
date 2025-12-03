package com.example.cineplus.data.remote.dto


data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val data: RegisterData?
)

data class RegisterData(
    val access_token: String,
    val user: UserData
)
