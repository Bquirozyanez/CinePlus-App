package com.example.cineplus.data.remote.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val nombre: String,
    val role: String = "USUARIO",
    val telefono: String? = null,
    val preferencias: List<String>? = null,
    val historialVisto: String? = null
)
