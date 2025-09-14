package com.example.money_manager.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light theme colors
private val LightColorScheme = lightColorScheme(
    primary = Primary500,
    onPrimary = White,
    primaryContainer = Primary100,
    onPrimaryContainer = Primary900,
    
    secondary = Secondary500,
    onSecondary = White,
    secondaryContainer = Secondary100,
    onSecondaryContainer = Secondary900,
    
    tertiary = Success500,
    onTertiary = White,
    tertiaryContainer = Success100,
    onTertiaryContainer = Success900,
    
    error = Error500,
    onError = White,
    errorContainer = Error100,
    onErrorContainer = Error900,
    
    background = BackgroundLight,
    onBackground = Neutral900,
    surface = SurfaceLight,
    onSurface = Neutral900,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Neutral700,
    
    outline = Neutral300,
    outlineVariant = Neutral200,
    
    scrim = Neutral900.copy(alpha = 0.32f)
)

// Dark theme colors
private val DarkColorScheme = darkColorScheme(
    primary = Primary300,
    onPrimary = Primary900,
    primaryContainer = Primary800,
    onPrimaryContainer = Primary100,
    
    secondary = Secondary300,
    onSecondary = Secondary900,
    secondaryContainer = Secondary800,
    onSecondaryContainer = Secondary100,
    
    tertiary = Success300,
    onTertiary = Success900,
    tertiaryContainer = Success800,
    onTertiaryContainer = Success100,
    
    error = Error300,
    onError = Error900,
    errorContainer = Error800,
    onErrorContainer = Error100,
    
    background = BackgroundDark,
    onBackground = Neutral100,
    surface = SurfaceDark,
    onSurface = Neutral100,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Neutral300,
    
    outline = Neutral600,
    outlineVariant = Neutral700,
    
    scrim = Neutral900.copy(alpha = 0.32f)
)

@Composable
fun MoneyManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MoneyManagerTypography,
        content = content
    )
}
