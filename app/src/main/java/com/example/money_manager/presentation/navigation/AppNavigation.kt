package com.example.money_manager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.money_manager.presentation.ui.screens.homescreen.HomeScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screens.Home.route){
        composable(Screens.Home.route) {
            HomeScreen(navController)
        }

    }
}