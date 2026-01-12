package com.example.mylab5.ui.theme

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object List : Screen("list")
    object Delete : Screen("delete")
    object Privacy : Screen("privacy")
    object Licenses : Screen("licenses")
    object Language : Screen("language")
}
