package com.example.money_manager.presentation.ui.screens.secureSettingsScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.money_manager.presentation.components.PinSetupSheet
import com.example.money_manager.presentation.components.SettingSectionTitle
import com.example.money_manager.presentation.components.SettingSwitchRow
import com.example.money_manager.presentation.components.TimeoutRow
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.presentation.viewmodel.securitysettingsviewmodel.SecuritySettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecureSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SecuritySettingsViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Безопасность") },
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
    }) { inner ->
        LazyColumn(
            modifier = modifier
                .padding(inner)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            item {
                SettingSectionTitle("Блокировка приложения")
                SettingSwitchRow(
                    title = "Включить блокировку",
                    subtitle = if (uiState.lockEnabled) "PIN активен" else "PIN отключен",
                    checked = uiState.lockEnabled,
                    onCheckedChange = viewModel::onToggleLock
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
            item {
                ListItem(
                    headlineContent = { Text("Сменить PIN", fontWeight = FontWeight.Medium) },
                    supportingContent = { Text("Текущий -> новый -> подтвержение") },
                    trailingContent = {
                        TextButton(
                            onClick = viewModel::onChangePin,
                            enabled = uiState.lockEnabled
                        ) {
                            Text("Изменить")
                        }
                    })
            }
            item {
                HorizontalDivider(
                    Modifier.padding(vertical = 8.dp),
                    DividerDefaults.Thickness,
                    DividerDefaults.color
                )
            }
            item {
                SettingSectionTitle("Биометрия")
                val bioSubtitle = when {
                    !uiState.deviceBiometricCapable -> "На устройстве нет биометрии/не настроена"
                    !uiState.lockEnabled -> "Требуется активная блокировка PIN"
                    uiState.biometricEnabled -> "Разрешен вход по биометрии"
                    else -> "Выключено"
                }
                SettingSwitchRow(
                    title = "Вход по биометрии",
                    subtitle = bioSubtitle,
                    checked = uiState.biometricEnabled && uiState.deviceBiometricCapable && uiState.lockEnabled,
                    onCheckedChange = viewModel::onBiometricToggle
                )
            }
            item {
                HorizontalDivider(
                    Modifier.padding(vertical = 8.dp),
                    DividerDefaults.Thickness,
                    DividerDefaults.color
                )
            }
            item {
                SettingSectionTitle("Таймаут блокировки")
                TimeoutRow(
                    selected = uiState.timeoutSec,
                    onSelect = viewModel::onTimeOutSelected
                )
            }
        }
    }
    if (uiState.showPinSheet){
        PinSetupSheet(
            stage = uiState.pinStage,
            pinLength = uiState.pinLength,
            error = uiState.pinError,
            busy = uiState.busy,
            onDigit = viewModel::onDigit,
            onBackspace = viewModel::onBackspace,
            onClearAll = viewModel::onClearAll,
            onDismiss = viewModel::closePinSheet
        )
    }
}