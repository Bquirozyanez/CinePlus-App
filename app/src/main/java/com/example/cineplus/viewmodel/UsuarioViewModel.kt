package com.example.cineplus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineplus.data.UserData
import com.example.cineplus.data.UserDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = UserDataStore(application)

    private val _user = MutableStateFlow(UserData("", "", ""))
    val user: StateFlow<UserData> = _user

   
    fun guardarUsuario(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            dataStore.saveUser(nombre, email, password)
            _user.value = UserData(nombre, email, password)
        }
    }

    
    fun guardarLogin(email: String, password: String) {
        viewModelScope.launch {
            dataStore.saveLogin(email, password)

            // mantener el nombre actual en memoria
            val nombreActual = _user.value.name
            _user.value = UserData(nombreActual, email, password)
        }
    }

 
    fun cargarUsuario() {
        viewModelScope.launch {
            _user.value = dataStore.userFlow.first()
        }
    }

   
    fun cerrarSesion() {
        viewModelScope.launch {
            dataStore.saveUser("", "", "")
            _user.value = UserData("", "", "")
        }
    }
}
