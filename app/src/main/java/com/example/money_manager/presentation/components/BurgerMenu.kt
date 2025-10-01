package com.example.money_manager.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.money_manager.domain.model.MenuItem
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.presentation.viewmodel.authviewmodel.AuthUiState
import kotlinx.coroutines.launch

@Composable
fun BurgerMenu(
    navController: NavController,
    drawerState: DrawerState,
    menuItems: List<MenuItem>,
    uiState: AuthUiState,
    signOut: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(240.dp)) {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Меню",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )

                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                menuItems.forEachIndexed { index, menuItem ->
                    NavigationDrawerItem(
                        label = { Text(menuItem.label) },
                        icon = {
                            menuItem.icon?.let {
                                Icon(
                                    it,
                                    contentDescription = menuItem.label
                                )
                            }
                        },
                        selected = currentRoute == menuItem.route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                if (currentRoute != menuItem.route) {
                                    navController.navigate(menuItem.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(vertical = 4.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color(0xFFDDE2F9),
                            selectedTextColor = Color(0xFF000000),
                            selectedIconColor = Color(0xFF000000)
                        )
                    )
                    if (index < menuItems.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = DividerDefaults.Thickness,
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                when {
                    uiState.user != null -> {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (uiState.user.photoUrl != null) {
                                    AsyncImage(
                                        model = uiState.user.photoUrl,
                                        contentDescription = "Profile picture",
                                        modifier = Modifier.size(40.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Profile picture",
                                        modifier = Modifier.size(40.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Column {
                                    Text(
                                        text = uiState.user.displayName ?: "Пользователь",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = uiState.user.email ?: "",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    signOut()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Выйти")
                            }
                        }
                    }
                    uiState.isGuest -> {
                        Column(Modifier.padding(16.dp)) {
                            Text("Вы используете гостевой режим")
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(Screens.Auth.route)
                                }
                            ) {
                                Text("Войти через Google")
                            }
                        }
                    }
                    else ->{
                        Column(Modifier.padding(16.dp)) {
                            Button(
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    navController.navigate(Screens.Auth.route)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Войти через Google")
                            }
                        }
                    }
                }
            }
        },
        content = {
            content()
        }
    )
}
