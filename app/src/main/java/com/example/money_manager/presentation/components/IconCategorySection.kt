package com.example.money_manager.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.money_manager.utils.helpers.CategoryIcons
import com.example.money_manager.utils.helpers.toImageVector

@Composable
fun IconCategorySection(
    categoryName: String,
    icons: List<CategoryIcons>,
    onIconSelected: (CategoryIcons) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )

        BeautifulCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2,
            cornerRadius = 12
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 5
            ) {
                icons.forEach { icon ->
                    Icon(
                        imageVector = icon.toImageVector(),
                        contentDescription = icon.name,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { onIconSelected(icon) }
                    )
                }
            }
        }
    }
}