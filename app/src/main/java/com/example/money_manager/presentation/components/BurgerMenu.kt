package com.example.money_manager.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

                menuItems.forEach { menuItem ->
                    NavigationDrawerItem(
                        label = { Text(menuItem.label) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(menuItem.route)
                        }
                    )
                }
            }
        },
        content = {
            content()
        }
    )
}