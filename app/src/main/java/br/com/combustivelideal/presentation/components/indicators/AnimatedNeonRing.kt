package br.com.combustivelideal.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedNeonRing(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress.coerceIn(0f, 1f),
            animationSpec = tween(800)
        )
    }

    Canvas(modifier = modifier.size(180.dp)) {

        val stroke = 12.dp.toPx()

        drawArc(
            color = color.copy(alpha = 0.2f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(stroke)
        )

        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress.value,
            useCenter = false,
            style = Stroke(stroke, cap = StrokeCap.Round)
        )
    }
}