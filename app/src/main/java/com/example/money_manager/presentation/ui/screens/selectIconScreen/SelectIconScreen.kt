package com.example.money_manager.presentation.ui.screens.selectIconScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.money_manager.presentation.components.IconCategorySection
import com.example.money_manager.utils.getIconsByCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectIconScreen(
    navController: NavController
) {
    val iconsByCategories = getIconsByCategory()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Выберите иконку",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(iconsByCategories) { category ->
                IconCategorySection(
                    categoryName = category.name,
                    icons = category.icons,
                    onIconSelected = { icon ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedIcon", icon)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
