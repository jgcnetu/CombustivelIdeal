package com.example.combustivelideal.tema

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Cores personalizadas
val VerdeEtanol = Color(0xFF4CAF50)
val AmareloGasolina = Color(0xFFFFC107)
val LaranjaAlerta = Color(0xFFFF9800)
val CinzaClaro = Color(0xFFF5F5F5)
val CinzaEscuro = Color(0xFF212121)

private val EsquemaCoresClaro = lightColorScheme(
    primary = VerdeEtanol,
    secondary = AmareloGasolina,
    tertiary = LaranjaAlerta,
    background = CinzaClaro,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = CinzaEscuro,
    onSurface = CinzaEscuro
)

private val EsquemaCoresEscuro = darkColorScheme(
    primary = VerdeEtanol.copy(alpha = 0.8f),
    secondary = AmareloGasolina.copy(alpha = 0.8f),
    tertiary = LaranjaAlerta.copy(alpha = 0.8f),
    background = Color.Black,
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun TemaCombustivelIdeal(
    usarTemaEscuro: Boolean = isSystemInDarkTheme(),
    conteudo: @Composable () -> Unit
) {
    val esquemaCores = if (usarTemaEscuro) EsquemaCoresEscuro else EsquemaCoresClaro

    MaterialTheme(
        colorScheme = esquemaCores,
        typography = Typography(),
        content = conteudo
    )
}