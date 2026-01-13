package com.example.money_manager.presentation.ui.screens.editCategoryScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.money_manager.presentation.components.BeautifulButton
import com.example.money_manager.presentation.components.BeautifulCard
import com.example.money_manager.presentation.components.BeautifulTextField
import com.example.money_manager.presentation.components.IconSelector
import com.example.money_manager.presentation.components.TransactionTypeSelector
import com.example.money_manager.presentation.viewmodel.editcategoryviewmdel.EditCategoryViewModel
import com.example.money_manager.utils.helpers.iconsForType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryScreen(
    navController: NavController,
    categoryId: Long,
    viewModel: EditCategoryViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val icons = iconsForType(uiState.value.type)

    LaunchedEffect(categoryId) {
        if (uiState.value.isLoading){
            viewModel.loadCategory(categoryId)
        }
    }

    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            scope.launch {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(uiState.value.error) {
        uiState.value.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Редактировать категорию",
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
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                        text = "Информация о категории",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    BeautifulTextField(
                        value = uiState.value.name,
                        onValueChange = viewModel::onNameChange,
                        label = "Название категории",
                        placeholder = "Например: Продукты, Транспорт",
                        modifier = Modifier.fillMaxWidth()
                    )

                    TransactionTypeSelector(
                        selectedType = uiState.value.type,
                        onTypeSelected = viewModel::onTypeChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Выберите иконку",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconSelector(
                        icons = icons,
                        selected = uiState.value.iconName,
                        onIconSelected = viewModel::onIconChange,
                        navController = navController
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BeautifulButton(
                text = "Сохранить категорию",
                onClick = viewModel::updateCategory,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.value.name.isNotEmpty()
            )
        }
    }

}