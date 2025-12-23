package br.com.combustivelideal.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.combustivelideal.domain.model.FuelType
import br.com.combustivelideal.presentation.ui.theme.FuelColors

@Composable
fun NeonRingWithDrop(
    progress: Float,
    fuelType: FuelType?,
    modifier: Modifier = Modifier
) {
    val neonColor = when (fuelType) {
        FuelType.ETANOL -> Color(0xFF00FF7F)   // verde neon
        FuelType.GASOLINA -> Color(0xFFFFD700) // amarelo
        null -> MaterialTheme.colorScheme.tertiary
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(180.dp)
    ) {

        // 🔥 Glow pulsante
        PulsingGlow(
            color = neonColor,
            modifier = Modifier.size(90.dp)
        )

        // 🔵 Ring
        AnimatedNeonRing(
            progress = progress,
            color = neonColor
        )

        // 📊 Texto animado
        AnimatedPercentageText(progress = progress)

        // 💧 Gota
        /*Icon(
            painter = painterResource(id = R.drawable.ic_drop),
            contentDescription = null,
            tint = neonColor,
            modifier = Modifier.size(64.dp)
        )*/
    }
}

@Composable
fun PulsingGlow(
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(modifier = modifier) {
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = size.minDimension / 2 * scale
        )
    }
}

@Composable
fun animatedFuelColor(fuelType: FuelType?): Color {
    val target = when (fuelType) {
        FuelType.ETANOL -> FuelColors.Etanol
        FuelType.GASOLINA -> FuelColors.Gasolina
        null -> FuelColors.Etanol
    }

    return animateColorAsState(
        targetValue = target,
        animationSpec = tween(600),
        label = "fuelColor"
    ).value
}
