package com.example.mylab5.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylab5.R

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Button(
            onClick = { onNavigate(Screen.Add.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.home_add_person))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.List.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.home_list))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.Delete.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.home_delete))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNavigate(Screen.Language.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.language_title))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNavigate(Screen.Privacy.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.home_privacy))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNavigate(Screen.Licenses.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.home_licenses))
        }
    }
}
