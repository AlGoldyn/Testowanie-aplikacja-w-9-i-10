package com.example.mylab5.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Add : Screen("add")
    object List : Screen("list")
    object Delete : Screen("delete")
    object Privacy : Screen("privacy")
    object Licenses : Screen("licenses")
    object Language : Screen("language")
}
