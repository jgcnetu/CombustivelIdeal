package br.com.combustivelideal.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = NeonGreen,
    secondary = NeonGreenSecondary,
    tertiary = NeonYellow,

    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,

    onPrimary = OnNeon,
    onSecondary = Color.Black,
    onTertiary = Color.Black,

    onBackground = OnDark,
    onSurface = OnSurface,

    outline = OutlineGreen,
    error = ErrorRed,
    onError = Color.Black
)

@Composable
fun CombustivelIdealTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography(),
        content = content
    )
}
    // Para Android 6.0+ com API moderna
    /*val view = LocalView.current
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
    }*/