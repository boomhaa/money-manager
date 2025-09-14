package com.example.money_manager.presentation.ui.screens.statisticScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.money_manager.presentation.components.BurgerMenu
import com.example.money_manager.presentation.components.DatePickerField
import com.example.money_manager.utils.ScreenMenuList
import com.example.money_manager.utils.generateColor
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
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
                CenterAlignedTopAppBar(
                    title = { Text("Статистика и Аналитика") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    }
                )
            }
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

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Доходы: ${uiState.value.totalIncome} ₽", color = Color(0xFF4CAF50))
                        Text("Расходы: ${uiState.value.totalExpense} ₽", color = Color(0xFFF44336))
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                        Text(
                            "Баланс: ${uiState.value.balance} ₽",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                if (uiState.value.expenseByCategory.isNotEmpty()) {
                    Text("Расходы по категориям", style = MaterialTheme.typography.titleMedium)
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
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(generateColor(index))
                                )
                                Text(category)
                            }
                        }
                    }
                }

                if (uiState.value.transactionsOverTime.isNotEmpty()) {
                    Text("Динамика транзакций", style = MaterialTheme.typography.titleMedium)
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

