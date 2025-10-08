package com.example.money_manager.presentation.ui.screens.dataSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.presentation.viewmodel.datasettingsviewmodel.DataSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataSettingsScreen(
    viewModel: DataSettingsViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var showCurrencyDropdown by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Настройки данных") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(Screens.Settings.route) {
                                popUpTo(Screens.DataSettings.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Назад")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ListItem(
                    headlineContent = {
                        Column {
                            Text("Основная валюта")
                            uiState.value.selectedCurrency?.let { currency ->
                                Text(
                                    text = "${currency.name} (${currency.symbol})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    leadingContent = {
                        Icon(Icons.Default.CurrencyExchange, contentDescription = null)
                    },
                    trailingContent = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Выбрать валюту"
                        )
                    },
                    modifier = Modifier.clickable {
                        showCurrencyDropdown = true
                    }
                )
                HorizontalDivider()

                if (showCurrencyDropdown) {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { showCurrencyDropdown = false },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        uiState.value.currencies.forEach { currency ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = currency.symbol,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.width(30.dp)
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = currency.name,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = currency.code,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        if (uiState.value.selectedCurrency?.code == currency.code) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = "Выбрано",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    viewModel.onChangeCurrency(currency)
                                    showCurrencyDropdown = false
                                }
                            )
                        }
                    }
                }

                ListItem(
                    headlineContent = { Text("Удалить все данные") },
                    leadingContent = {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    },
                    modifier = Modifier.clickable { viewModel.clearData() }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                ListItem(
                    headlineContent = { Text("Синхронизировать данные") },
                    leadingContent = {
                        Icon(Icons.Default.Sync, contentDescription = null)
                    },
                    modifier = Modifier.clickable { viewModel.syncData() }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }
        }



        if (uiState.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = uiState.value.loadingText, color = Color.White)
                }
            }
        }
    }
}
