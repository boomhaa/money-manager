package com.example.money_manager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.collections.listOf

@Composable
fun NumberPad(
    modifier: Modifier = Modifier,
    onDigit: (Int) -> Unit,
    onBackspace: () -> Unit,
    onLongBackspace: (() -> Unit)? = null,
    bottomLeftContent: (@Composable BoxScope.() -> Unit)? = null,
    keySize: Dp = 72.dp,
    keyCorner: Dp = 16.dp,
    keySpacing: Dp = 12.dp
) {
    val haptics = LocalHapticFeedback.current
    val rows = remember {
        listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9")
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(keySpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        rows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(keySpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { label ->
                    NPadKey(
                        modifier = Modifier.size(keySize),
                        label = label,
                        onClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onDigit(label.toInt())
                        },
                        corner = keyCorner
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(keySpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(keySize),
                contentAlignment = Alignment.Center
            ) {
                if (bottomLeftContent != null) {
                    bottomLeftContent()
                } else {
                    Spacer(modifier = Modifier.size(1.dp))
                }
            }

            NPadKey(
                modifier = Modifier.size(keySize),
                label = "0",
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onDigit(0)
                },
                corner = keyCorner
            )

            NPadKey(
                modifier = Modifier.size(keySize),
                label = "⌫",
                icon = Icons.AutoMirrored.Outlined.Backspace,
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onBackspace()
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongBackspace?.invoke()
                },
                corner = keyCorner,
                contentDescription = "Удалить"
            )
        }
    }
}

@Composable
private fun NPadKey(
    modifier: Modifier,
    label: String,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    corner: Dp,
    icon: ImageVector? = null,
    contentDescription: String? = null
) {
    val shape = RoundedCornerShape(corner)
    Surface(
        modifier = modifier
            .semantics {
                role = Role.Button
                contentDescription?.let { this.contentDescription = it }
            }
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null && contentDescription != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}