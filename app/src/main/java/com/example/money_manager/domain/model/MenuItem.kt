package com.example.money_manager.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItem(
    val label: String,
    val route: String,
    val icon: ImageVector? = null
)
