package br.com.combustivelideal.presentation.components.effects

import android.view.MotionEvent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private data class WaterDrop(
    val position: Offset,
    val radius: Animatable<Float, *>,
    val alpha: Animatable<Float, *>
)

@Composable
fun WaterDropEffect(
    color: Color,
    maxRadiusDp: Float = 100f,
    durationMs: Int = 1400
) {
    val scope = rememberCoroutineScope()
    val drops = remember { mutableStateListOf<WaterDrop>() }
    val density = LocalDensity.current
    val maxRadiusPx = with(density) { maxRadiusDp.dp.toPx() }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                if (event.action == MotionEvent.ACTION_DOWN) {

                    val drop = WaterDrop(
                        position = Offset(event.x, event.y),
                        radius = Animatable(0f),
                        alpha = Animatable(0.45f) // MAIS VISÍVEL
                    )

                    drops.add(drop)

                    scope.launch {
                        drop.radius.animateTo(
                            maxRadiusPx,
                            animationSpec = tween(durationMs)
                        )
                    }

                    scope.launch {
                        drop.alpha.animateTo(
                            0f,
                            animationSpec = tween(durationMs)
                        )
                        drops.remove(drop)
                    }
                }

                false // 👈 CRÍTICO: nunca consome o evento
            }
    ) {
        drops.forEach { drop ->
            // Glow externo
            drawCircle(
                color = color.copy(alpha = drop.alpha.value * 0.4f),
                radius = drop.radius.value,
                center = drop.position,
                style = Stroke(width = 10f)
            )
            // Borda principal
            drawCircle(
                color = color.copy(alpha = drop.alpha.value),
                radius = drop.radius.value,
                center = drop.position,
                style = Stroke(width = 6f)
            )
        }
    }
}