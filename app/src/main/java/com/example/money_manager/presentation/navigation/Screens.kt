package com.example.money_manager.presentation.navigation

sealed class Screens(val route: String) {
    object Home: Screens("home")
    object AddTransaction: Screens("addTransaction")
}