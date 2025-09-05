package com.example.money_manager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.money_manager.presentation.ui.screens.addtransactionscreen.AddTransactionScreen
import com.example.money_manager.presentation.ui.screens.edittransactionscreen.EditTransactionScreen
import com.example.money_manager.presentation.ui.screens.homescreen.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(navController)
        }

        composable(Screens.AddTransaction.route) {
            AddTransactionScreen(navController)
        }

        composable(route = Screens.EditTransaction.route,
            arguments = listOf(navArgument("transactionId") {
                type = NavType.LongType
            })) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getLong("transactionId") ?: 0L
            EditTransactionScreen(
                navController = navController,
                transactionId = transactionId
            )
        }
    }
}