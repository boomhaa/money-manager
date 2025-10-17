package com.example.money_manager.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PinDots(
    modifier: Modifier = Modifier,
    count: Int,
    total: Int = 4,
    dotSize: Dp = 12.dp,
    gap: Dp = 12.dp,
    filledColor: Color = MaterialTheme.colorScheme.primary,
    emptyColor: Color = MaterialTheme.colorScheme.outline
) {
    Row(
        modifier = modifier
            .semantics { contentDescription = "PIN $count of $total" },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(gap)
    ) {
        repeat(total) { index ->
            val filled = index < count
            val scale by animateFloatAsState(if (filled) 1f else 0.9f, label = "dotScale")
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .scale(scale)
                    .background(
                        color = if (filled) filledColor else emptyColor,
                        shape = CircleShape
                    )
                    .then(
                        if (!filled)
                            Modifier
                                .background(Color.Transparent, CircleShape)
                        else Modifier
                    )
            )
        }
    }
}