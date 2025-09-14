package com.example.money_manager.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import com.example.money_manager.domain.model.MenuItem
import com.example.money_manager.presentation.navigation.Screens

object ScreenMenuList {
    val screenMenuList: List<MenuItem> = listOf(
        MenuItem("Главная", Screens.Home.route, Icons.Filled.Home),
        MenuItem("Статистика", Screens.Statistics.route, Icons.Filled.BarChart),
        MenuItem("Категории", Screens.Categories.route, Icons.AutoMirrored.Filled.List)
    )
}