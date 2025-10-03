package com.example.money_manager.presentation.ui.screens.dataSettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
