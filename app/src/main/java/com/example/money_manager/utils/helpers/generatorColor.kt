package com.example.money_manager.utils.helpers

import androidx.compose.ui.graphics.Color
import kotlin.random.Random


fun generateColor(index: Int): Color {
    val random = Random(index)
    return Color(
        red = random.nextInt(0, 256),
        green = random.nextInt(0, 256),
        blue = random.nextInt(0, 256)
    )
}