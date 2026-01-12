package com.example.mylab5.ui.theme

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylab5.R
import com.example.mylab5.util.LocalHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(
    onBack: () -> Unit
) {
    val activity = LocalContext.current as Activity

    Column {

        TopAppBar(
            title = {
                Text(stringResource(R.string.language_title))
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Button(
                onClick = {
                    LocalHelper.applyLanguageAndRestart(activity, "pl")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.language_polish))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    LocalHelper.applyLanguageAndRestart(activity, "en")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.language_english))
            }
        }
    }
}
