package com.example.mylab5.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Add : Screen("add")
    object List : Screen("list")
    object Delete : Screen("delete")
    object Language : Screen("language")
}
