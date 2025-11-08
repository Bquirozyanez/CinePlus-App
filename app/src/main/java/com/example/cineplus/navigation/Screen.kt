package com.example.cineplus.navigation

// Define las rutas (pantallas) de tu app
sealed class Screen(val route: String) {

    data object Home : Screen("home_page")

    data object Profile : Screen("profile_page")

    // Si luego agregas más pantallas, las añades aquí:
    // data object Settings : Screen("settings_page")
}
