package com.example.mylab5.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylab5.R
import com.example.mylab5.ui.navigation.Screen
import com.example.mylab5.util.AuthPreferences

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onLogoutNavigate: () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = { onNavigate(Screen.Add.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.home_add_person))
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { onNavigate(Screen.List.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.home_list))
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { onNavigate(Screen.Delete.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.home_delete))
            }

            Spacer(Modifier.height(16.dp))

Button (
    onClick = {onNavigate(Screen.Language.route)},
    modifier = Modifier.fillMaxWidth()
){
    Text(stringResource(R.string.language_title))
}

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    AuthPreferences.setLoggedIn(context, false)
                    onLogoutNavigate()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.logout))
            }
        }
    }
}
