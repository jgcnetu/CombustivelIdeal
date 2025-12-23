package br.com.combustivelideal.presentation.util.modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

fun Modifier.shake(
    trigger: Boolean,
    onShakeEnd: () -> Unit = {}
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(trigger) {
        if (trigger) {
            scope.launch {
                offsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(400),
                    initialVelocity = 1000f
                )
                onShakeEnd()
            }
        }
    }

    this.graphicsLayer {
        translationX = offsetX.value
    }
}