package br.com.combustivelideal.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = VerdeEtanol,
    secondary = AmareloGasolina,
    tertiary = LaranjaAlerta.copy( alpha = 0.8f ),
    background = CinzaEscuro,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = CinzaClaro,
    onSurface = CinzaClaro
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeEtanolEscuro,
    secondary = AmareloGasolinaEscuro,
    tertiary = LaranjaAlerta,
    background = CinzaClaro,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = CinzaEscuro,
    onSurface = CinzaEscuro,


)

@Composable
fun CombustivelIdealTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Para Android 6.0+ com API moderna
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val statusBarColor = if (darkTheme) {
                Color(0xFF1A3D1C) // Verde escuro para tema escuro
            } else {
                Color(0xFF4CAF50)  // Verde para tema claro
            }

            window.statusBarColor = statusBarColor.toArgb()

            // Define cor dos ícones (claro/escuro)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}