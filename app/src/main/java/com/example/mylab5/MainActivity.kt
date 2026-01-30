package com.example.mylab5

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.ui.navigation.Screen
import com.example.mylab5.ui.screens.add.AddPersonScreen
import com.example.mylab5.ui.screens.delete.DeletePersonScreen
import com.example.mylab5.ui.screens.home.HomeScreen
import com.example.mylab5.ui.screens.language.LanguageScreen
import com.example.mylab5.ui.screens.list.ListPersonScreen
import com.example.mylab5.ui.screens.auth.login.LoginScreen
import com.example.mylab5.ui.screens.auth.register.RegisterScreen
import com.example.mylab5.ui.theme.MyLab5Theme
import com.example.mylab5.util.AuthPreferences
import com.example.mylab5.util.LocalHelper

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val lang = LocalHelper.getSavedLanguage(newBase)
        val context = LocalHelper.setLocale(newBase, lang)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = PersonDatabase.getDatabase(this)

        setContent {
            MyLab5Theme {

                val navController = rememberNavController()
                val refreshTrigger = remember { mutableStateOf(false) }
                val startDestination =
                    if (AuthPreferences.isLoggedIn(this))
                        Screen.Home.route
                    else
                        Screen.Login.route

                val backStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry.value?.destination?.route

                val showBottomBar = currentRoute !in listOf(
                    Screen.Login.route,
                    Screen.Register.route
                )

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {

                                NavigationBarItem(
                                    selected = false,
                                    onClick = { navController.navigate(Screen.Add.route) },
                                    label = {
                                        Text(stringResource(R.string.home_add_person))
                                    },
                                    icon = {}
                                )

                                NavigationBarItem(
                                    selected = false,
                                    onClick = { navController.navigate(Screen.List.route) },
                                    label = {
                                        Text(stringResource(R.string.home_list))
                                    },
                                    icon = {}
                                )

                                NavigationBarItem(
                                    selected = false,
                                    onClick = { navController.navigate(Screen.Delete.route) },
                                    label = {
                                        Text(stringResource(R.string.home_delete))
                                    },
                                    icon = {}
                                )
                            }
                        }
                    }
                ) { paddingValues ->

                    val context = this
                    val startRoute =
                        if (AuthPreferences.isLoggedIn(context))
                            Screen.Home.route
                        else
                            Screen.Login.route

                    NavHost(
                        navController = navController,
                        startDestination = startRoute,
                        modifier = Modifier.padding(paddingValues)
                    ) {

                        composable(Screen.Login.route) {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                },
                                onGoToRegister = {
                                    navController.navigate(Screen.Register.route)
                                }
                            )
                        }

                        composable(Screen.Register.route) {
                            RegisterScreen(
                                onBackToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }


                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigate = { route ->
                                    navController.navigate(route)
                                },
                                onLogoutNavigate = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }


                        composable(Screen.Add.route) {
                            AddPersonScreen(db) {
                                navController.navigate(Screen.Home.route)
                                refreshTrigger.value = !refreshTrigger.value
                            }
                        }

                        composable(Screen.List.route) {
                            ListPersonScreen(db, refreshTrigger) {
                                navController.navigate(Screen.Home.route)
                            }
                        }

                        composable(Screen.Delete.route) {
                            DeletePersonScreen(db) {
                                navController.navigate(Screen.Home.route)
                            }
                        }

                        composable(Screen.Language.route) {
                            LanguageScreen {
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}
