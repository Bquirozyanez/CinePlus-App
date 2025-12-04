package com.example.cineplus

object UsuarioValidator {

    fun esNombreValido(nombre: String): Boolean {
        return nombre.isNotBlank()
    }

    fun esCorreoValido(correo: String): Boolean {
        // Regla simple: tiene "@" y "."
        return correo.contains("@") && correo.contains(".")
    }

    fun esClaveValida(clave: String): Boolean {
        // Por ejemplo: mínimo 8 caracteres
        return clave.length >= 8
    }

    fun coincidenClaves(clave1: String, clave2: String): Boolean {
        return clave1 == clave2
    }
}
