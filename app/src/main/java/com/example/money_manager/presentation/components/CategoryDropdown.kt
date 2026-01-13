package com.example.money_manager.presentation.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.money_manager.domain.model.Category
import com.example.money_manager.presentation.navigation.Screens
import com.example.money_manager.utils.TransactionType
import com.example.money_manager.utils.helpers.toImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier,
    transactionType: TransactionType,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    val filteredCategories = categories.filter { it.type == transactionType }.take(6)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedCategory?.name ?: "Выберите категорию",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
            label = { Text("Категория") }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredCategories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    },
                    leadingIcon = {
                        category.iconName?.let {icon ->
                            Icon( imageVector = icon.toImageVector(),
                                contentDescription = category.name,
                                modifier = Modifier.size(20.dp))
                        }
                    }
                )
            }
            DropdownMenuItem(
                text = { Text("Ещё…", color = MaterialTheme.colorScheme.primary) },
                onClick = {
                    expanded = false
                    navController.navigate(Screens.SelectCategory.createRoute(
                        transactionType
                    ))
                }
            )
        }
    }
}