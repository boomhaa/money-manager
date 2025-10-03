package com.example.money_manager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.money_manager.presentation.ui.screens.authScreen.AuthScreen
import com.example.money_manager.presentation.ui.screens.addCategoryScreen.AddCategoryScreen
import com.example.money_manager.presentation.ui.screens.addTransactionScreen.AddTransactionScreen
import com.example.money_manager.presentation.ui.screens.categoriesScreen.CategoriesScreen
import com.example.money_manager.presentation.ui.screens.dataSettingsScreen.DataSettingsScreen
import com.example.money_manager.presentation.ui.screens.editCategoryScreen.EditCategoryScreen
import com.example.money_manager.presentation.ui.screens.editTransactionScreen.EditTransactionScreen
import com.example.money_manager.presentation.ui.screens.homeScreen.HomeScreen
import com.example.money_manager.presentation.ui.screens.selectCategoryScreen.SelectCategoryScreen
import com.example.money_manager.presentation.ui.screens.selectIconScreen.SelectIconScreen
import com.example.money_manager.presentation.ui.screens.settingsScreen.SettingsScreen
import com.example.money_manager.presentation.ui.screens.statisticScreen.StatisticsScreen
import com.example.money_manager.presentation.viewmodel.authviewmodel.AuthViewModel
import com.example.money_manager.utils.TransactionType
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun AppNavigation(
    googleSignInClient: GoogleSignInClient,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val uiState = authViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = if (uiState.value.isGuest || uiState.value.user != null) {
            Screens.Home.route
        } else {
            Screens.Auth.route
        }
    ) {
        composable(Screens.Home.route) {
            HomeScreen(
                navController = navController,
                authUiState = uiState.value,
                signOut = authViewModel::signOut
            )
        }

        composable(Screens.AddTransaction.route) {
            AddTransactionScreen(navController)
        }

        composable(
            route = Screens.EditTransaction.route,
            arguments = listOf(navArgument("transactionId") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getLong("transactionId") ?: 0L
            EditTransactionScreen(
                navController = navController,
                transactionId = transactionId
            )
        }

        composable(
            route = Screens.SelectCategory.route,
            arguments = listOf(navArgument("transactionType") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val transactionTypeName = backStackEntry.arguments?.getString("transactionType")
            val transactionType = transactionTypeName?.let { TransactionType.valueOf(it) }
            SelectCategoryScreen(
                navController = navController,
                transactionType = transactionType
            )
        }

        composable(Screens.AddCategory.route) {
            AddCategoryScreen(navController = navController)
        }

        composable(Screens.Statistics.route) {
            StatisticsScreen(
                navController = navController,
                authUiState = uiState.value,
                signOut = authViewModel::signOut
            )
        }

        composable(Screens.Categories.route) {
            CategoriesScreen(
                navController = navController,
                authUiState = uiState.value,
                signOut = authViewModel::signOut
            )
        }

        composable(Screens.SelectIcon.route) {
            SelectIconScreen(navController = navController)
        }

        composable(
            route = Screens.EditCategory.route,
            arguments = listOf(navArgument("categoryId") {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getLong("categoryId") ?: 0L
            EditCategoryScreen(
                navController = navController,
                categoryId = categoryId
            )
        }

        composable(Screens.Auth.route) {
            AuthScreen(
                googleSignInClient = googleSignInClient,
                navController = navController,
                viewModel = authViewModel
            )
        }

        composable(Screens.Settings.route) {
            SettingsScreen(
                navController = navController,
                authUiState = uiState.value,
                signOut = authViewModel::signOut
            )
        }

        composable(Screens.DataSettings.route) {
            DataSettingsScreen(navController = navController)
        }
    }
}