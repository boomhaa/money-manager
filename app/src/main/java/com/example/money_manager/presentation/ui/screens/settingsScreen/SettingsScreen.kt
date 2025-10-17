package com.example.money_manager.presentation.ui.screens.settingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.money_manager.presentation.components.BurgerMenu
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.presentation.viewmodel.authviewmodel.AuthUiState
import com.example.money_manager.utils.ScreenMenuList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    authUiState: AuthUiState,
    signOut: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BurgerMenu(
        navController = navController,
        drawerState = drawerState,
        menuItems = ScreenMenuList.screenMenuList,
        uiState = authUiState,
        signOut = signOut
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Настройки") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Меню",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
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
                    headlineContent = { Text("Данные") },
                    modifier = Modifier.clickable { navController.navigate(Screens.DataSettings.route) }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                ListItem(
                    headlineContent = { Text("Безопасность") },
                    modifier = Modifier.clickable { navController.navigate(Screens.SecureSettings.route) }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }
        }
    }
}
