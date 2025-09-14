package com.example.money_manager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.money_manager.presentation.theme.Primary500
import com.example.money_manager.presentation.theme.Success500

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Primary500.copy(alpha = 0.05f),
                        Color.Transparent,
                        Success500.copy(alpha = 0.03f)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        content()
    }
}

@Composable
fun ColorfulBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Primary500.copy(alpha = 0.08f),
                        Success500.copy(alpha = 0.05f),
                        Color.Transparent
                    ),
                    radius = 800f
                )
            )
    ) {
        content()
    }
}
