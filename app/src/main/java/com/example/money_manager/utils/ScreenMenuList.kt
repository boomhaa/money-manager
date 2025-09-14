package com.example.money_manager.utils

import com.example.money_manager.domain.model.MenuItem
import com.example.money_manager.presentation.navigation.Screens

object ScreenMenuList {
    val screenMenuList: List<MenuItem> = listOf(
        MenuItem("Главная", Screens.Home.route),
        MenuItem("Статистика", Screens.Statistics.route),
        MenuItem("Категории", Screens.Categories.route)
    )
}