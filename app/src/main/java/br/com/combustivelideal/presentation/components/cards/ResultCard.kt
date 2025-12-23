package br.com.combustivelideal.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.combustivelideal.domain.model.FuelType
import br.com.combustivelideal.presentation.util.shareText
import kotlin.math.roundToInt

@Composable
fun ResultCard(
    visible: Boolean,
    fuelType: FuelType?,
    progress: Float,
    onRecalculate: () -> Unit
) {
    if (!visible || fuelType == null) return

    val context = LocalContext.current

    val color = when (fuelType) {
        FuelType.ETANOL -> Color(0xFF00FF7F)
        FuelType.GASOLINA -> Color(0xFFFFD700)
    }

    val percentage = (progress * 100).roundToInt()

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Melhor opção",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = fuelType.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = color,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Etanol está $percentage% do valor da gasolina.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🔘 Botões
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // 🔁 Calcular novamente
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = onRecalculate,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = color
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = SolidColor(color)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Recalcular")
                    }

                    // 📤 Compartilhar
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val message = """
                                🚗 Combustível Ideal
                                
                                💡 Melhor opção: ${fuelType.name}
                                📊 Etanol está $percentage% do valor da gasolina.
                                
                                Calcule você também!
                            """.trimIndent()

                            shareText(context, message)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = color
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = SolidColor(color)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Compartilhar")
                    }
                }
            }
        }
    }
}