package br.com.combustivelideal.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
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
        typography = Typography,
        content = content
    )
}