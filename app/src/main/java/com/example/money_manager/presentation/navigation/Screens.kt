package com.example.money_manager.presentation.navigation

import com.example.money_manager.utils.TransactionType

sealed class Screens(val route: String) {
    object Home : Screens("home")
    object AddTransaction : Screens("addTransaction")
    object EditTransaction : Screens("editTransaction/{transactionId}") {
        fun createRoute(transactionId: Long): String {
            return "editTransaction/$transactionId"
        }
    }
    object SelectCategory : Screens("selectCategory/{transactionType87}"){
        fun createRoute(transactionType: TransactionType): String {
            return "selectCategory/$transactionType"
        }
    }
    object AddCategory : Screens("addCategory")
    object Statistics : Screens("statistics")
    object Categories : Screens("categories")
}