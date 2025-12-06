package com.example.combustivelideal.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VerdeEtanolEscuro ,
    secondary = AmareloGasolinaEscuro ,
    tertiary = Color(0xFF90CAF9),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurface = Color(0xFFFFFFFF),
    onBackground = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF1B5E20),
    secondaryContainer = Color(0xFFF57F17)
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeEtanolEscuro ,
    secondary = AmareloGasolinaEscuro ,
    tertiary = Color(0xFF2196F3),
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFF5F5F5),
    onSurface = Color(0xFF212121),
    onBackground = Color(0xFF212121),
    primaryContainer = VerdeEtanol ,
    secondaryContainer = AmareloGasolina
)

@Composable
fun CombustivelIdealTheme(
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}