package com.example.money_manager.presentation.ui.screens.statisticScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.money_manager.presentation.viewmodel.statisticviewmodel.StatisticViewModel
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.PieChartData.Slice
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.money_manager.presentation.components.BeautifulCard
import com.example.money_manager.presentation.components.BurgerMenu
import com.example.money_manager.presentation.components.DatePickerField
import com.example.money_manager.presentation.viewmodel.authviewmodel.AuthUiState
import com.example.money_manager.presentation.viewmodel.authviewmodel.AuthViewModel
import com.example.money_manager.utils.ScreenMenuList
import com.example.money_manager.utils.generateColor
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticViewModel = hiltViewModel(),
    authUiState: AuthUiState,
    signOut: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    BurgerMenu(
        navController = navController,
        drawerState = drawerState,
        menuItems = ScreenMenuList.screenMenuList,
        uiState = authUiState,
        signOut = signOut
    )
    {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            "Статистика и Аналитика",
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DatePickerField(
                        selectedDate = uiState.value.startDate.atStartOfDay(),
                        onDateSelected = { viewModel.onStartDateChange(it.toLocalDate()) },
                        modifier = Modifier.weight(1f)
                    )
                    DatePickerField(
                        selectedDate = uiState.value.endDate.atStartOfDay(),
                        onDateSelected = { viewModel.onEndDateChange(it.toLocalDate()) },
                        modifier = Modifier.weight(1f)
                    )
                }

                BeautifulCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 6,
                    cornerRadius = 16
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Финансовая сводка",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                Icons.Default.Analytics,
                                contentDescription = "Аналитика",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    "Доходы",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    "${uiState.value.totalIncome} ₽",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                            Column {
                                Text(
                                    "Расходы",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    "${uiState.value.totalExpense} ₽",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF44336)
                                )
                            }
                        }
                        
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Баланс",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "${uiState.value.balance} ₽",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (uiState.value.balance >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                            )
                        }
                    }
                }

                if (uiState.value.expenseByCategory.isNotEmpty()) {
                    BeautifulCard(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4,
                        cornerRadius = 16
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                "Расходы по категориям", 
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            PieChart(
                                pieChartData = PieChartData(
                                    slices = uiState.value.expenseByCategory.values.mapIndexed { index, entry ->
                                        Slice(
                                            value = entry.toFloat(),
                                            color = generateColor(index)
                                        )
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                uiState.value.expenseByCategory.keys.forEachIndexed { index, category ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .background(generateColor(index))
                                        )
                                        Text(
                                            category,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (uiState.value.transactionsOverTime.isNotEmpty()) {
                    BeautifulCard(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4,
                        cornerRadius = 16
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                "Динамика транзакций", 
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            BarChart(
                                barChartData = BarChartData(
                                    bars = uiState.value.transactionsOverTime.map { (label, value) ->
                                        BarChartData.Bar(
                                            label = "${label.format(formatter)}",
                                            value = value.toFloat(),
                                            color = Color(0xFF2196F3)
                                        )
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

