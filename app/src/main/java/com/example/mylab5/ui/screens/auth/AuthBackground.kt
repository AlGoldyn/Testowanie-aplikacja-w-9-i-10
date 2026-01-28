package com.example.mylab5.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AuthBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // 🔵 CIEMNE – MAŁE
        Box(
            modifier = Modifier
                .size(128.dp)
                .offset(x = 310.dp, y = (-88).dp)
                .background(Color(0xFF471AA0), CircleShape)
        )

        // 🟣 JASNE – DUŻE
        Box(
            modifier = Modifier
                .size(142.dp)
                .offset(x = 381.dp, y = (-47).dp)
                .background(Color(0xFFBB84E8), CircleShape)
        )

        // 🔽 CONTENT NA WIERZCHU
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            content()
        }
    }
}
