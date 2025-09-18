package com.example.money_manager.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.money_manager.domain.model.MenuItem
import kotlinx.coroutines.launch

@Composable
fun BurgerMenu(
    navController: NavController,
    drawerState: DrawerState,
    menuItems: List<MenuItem>,
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
                        icon = { menuItem.icon?.let { Icon(it, contentDescription = menuItem.label) } },
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
            }
        },
        content = {
            content()
        }
    )
}
