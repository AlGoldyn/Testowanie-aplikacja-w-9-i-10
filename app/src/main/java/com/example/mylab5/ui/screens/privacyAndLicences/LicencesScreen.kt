package com.example.mylab5.ui.screens.privacyAndLicences

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylab5.R

@Composable
fun LicensesScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())) {
        Text(stringResource(R.string.licenses_title), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.licenses_content))
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBack) { Text(stringResource(R.string.back)) }
    }
}
