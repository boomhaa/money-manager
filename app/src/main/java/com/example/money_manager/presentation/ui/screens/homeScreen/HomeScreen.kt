package com.example.money_manager.presentation.ui.screens.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.money_manager.presentation.viewmodel.homeviewmodel.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.money_manager.presentation.components.BalanceSummary
import com.example.money_manager.presentation.components.BeautifulFAB
import com.example.money_manager.presentation.components.BurgerMenu
import com.example.money_manager.presentation.components.ColorfulBackground
import com.example.money_manager.presentation.components.ShimmerCard
import com.example.money_manager.presentation.components.TransactionItem
import com.example.money_manager.presentation.components.WelcomeCard
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
                TopAppBar(
                    title = {
                        Text(
                            "Финансовый учет",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Меню",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            floatingActionButton = {
                BeautifulFAB(
                    onClick = {
                        navController.navigate(Screens.AddTransaction.route)
                    },
                    contentDescription = "Добавить транзакцию"
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            ColorfulBackground {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item { WelcomeCard() }

                    uiState.transactions.takeIf { it.isNotEmpty() }?.let {
                        item {
                            BalanceSummary(
                                balance = uiState.balance,
                                totalIncome = uiState.totalIncome,
                                totalExpense = uiState.totalExpense
                            )
                        }
                    }


                    when {
                        uiState.isLoading -> {
                            items(5) {
                                ShimmerCard()
                            }
                        }

                        uiState.error != null -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        Icons.Default.Warning,
                                        contentDescription = "Ошибка",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Text(
                                        text = "Ошибка: ${uiState.error}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        uiState.transactions.isEmpty() -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.List,
                                        contentDescription = "Нет транзакций",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Text(
                                        text = "Нет транзакций",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "Добавьте первую транзакцию,\nнажав на кнопку +",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        else -> {
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