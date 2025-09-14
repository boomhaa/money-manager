package com.example.money_manager.presentation.ui.screens.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.money_manager.presentation.viewmodel.homeviewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.money_manager.presentation.components.BalanceSummary
import com.example.money_manager.presentation.components.BurgerMenu
import com.example.money_manager.presentation.components.TransactionItem
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.utils.ScreenMenuList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BurgerMenu(
        navController = navController,
        drawerState = drawerState,
        menuItems = ScreenMenuList.screenMenuList
    )
     {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Финансовый учет") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("addTransaction")
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить транзакцию")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                uiState.transactions.takeIf { it.isNotEmpty() }?.let {
                    BalanceSummary(
                        balance = uiState.balance,
                        totalIncome = uiState.totalIncome,
                        totalExpense = uiState.totalExpense,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        uiState.isLoading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        uiState.error != null -> {
                            Text(
                                text = "Ошибка: ${uiState.error}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        uiState.transactions.isEmpty() -> {
                            Text(
                                text = "Нет транзакций",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(uiState.transactions) { item ->
                                    TransactionItem(
                                        item = item,
                                        onEdit = { id ->
                                            navController.navigate(
                                                Screens.EditTransaction.createRoute(
                                                    id
                                                )
                                            )
                                        },
                                        onDelete = { id ->
                                            viewModel.deleteTransaction(transaction = item.transaction)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}