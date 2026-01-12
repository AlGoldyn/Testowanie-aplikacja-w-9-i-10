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
import androidx.navigation.compose.rememberNavController
import com.example.mylab5.ui.theme.*
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
            val navController = rememberNavController()
            val refreshTrigger = remember { mutableStateOf(false) }

            Scaffold(
                bottomBar = {
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
            ) { paddingValues ->

                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(paddingValues)
                ) {

                    composable(Screen.Home.route) {
                        HomeScreen { route ->
                            navController.navigate(route)
                        }
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

                    composable(Screen.Privacy.route) {
                        PrivacyPolicyScreen {
                            navController.navigateUp()
                        }
                    }

                    composable(Screen.Licenses.route) {
                        LicensesScreen {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}
