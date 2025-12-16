package com.example.cineplus.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.ui.screens.*
import com.example.cineplus.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // ViewModel compartido
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "profile"
    ) {

        composable("profile") {
            ProfileScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("registro") {
            RegisterScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel
            )
        }

        composable("resumen") {
            ResumenScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("modificar") {
            ModificarScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // ✅ NUEVA RUTA
        composable("recuperar") {
            RecuperarPasswordScreen(navController = navController)
        }

        // DETALLE PELÍCULAS
        composable("creed") { CreedScreen(navController = navController) }
        composable("fantastic4") { Fantastic4Screen(navController = navController) }
        composable("kimetsu") { KimetsuScreen(navController = navController) }
        composable("shrek") { ShrekScreen(navController = navController) }

        // Conócenos (si ya lo tienes)
        composable("conocenos") { ConocenosScreen(navController = navController) }
    }
}
