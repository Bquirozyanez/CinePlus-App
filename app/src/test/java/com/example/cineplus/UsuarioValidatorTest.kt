package com.example.cineplus

import org.junit.Assert.*
import org.junit.Test

class UsuarioValidatorTest {

    @Test
    fun nombreVacio_esInvalido() {
        val resultado = UsuarioValidator.esNombreValido("")
        assertFalse(resultado)
    }

    @Test
    fun nombreConTexto_esValido() {
        val resultado = UsuarioValidator.esNombreValido("Benja")
        assertTrue(resultado)
    }

    @Test
    fun correoSinArroba_esInvalido() {
        val resultado = UsuarioValidator.esCorreoValido("benjagmail.com")
        assertFalse(resultado)
    }

    @Test
    fun correoConArrobaYpunto_esValido() {
        val resultado = UsuarioValidator.esCorreoValido("benja@gmail.com")
        assertTrue(resultado)
    }

    @Test
    fun claveCorta_esInvalida() {
        val resultado = UsuarioValidator.esClaveValida("abc123")
        assertFalse(resultado)
    }

    @Test
    fun claveLarga_esValida() {
        val resultado = UsuarioValidator.esClaveValida("castor1234")
        assertTrue(resultado)
    }

    @Test
    fun clavesDiferentes_noCoinciden() {
        val resultado = UsuarioValidator.coincidenClaves("clave123", "otraClave")
        assertFalse(resultado)
    }
}
