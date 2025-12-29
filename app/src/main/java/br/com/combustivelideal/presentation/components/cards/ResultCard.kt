package br.com.combustivelideal.presentation.components.cards

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
import androidx.compose.ui.text.font.FontWeight
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
    useConsumption: Boolean,
    ethanolPrice: String,
    gasolinePrice: String,
    ethanolConsumption: String,
    gasolineConsumption: String,
    onRecalculate: () -> Unit
) {
    if (!visible || fuelType == null) return

    val context = LocalContext.current

    val color = when (fuelType) {
        FuelType.ETANOL -> Color(0xFF00FF7F)
        FuelType.GASOLINA -> Color(0xFFFFD700)
    }

    val percentage = (progress * 100).roundToInt()

    val ethanolCostKm =
        ethanolPrice.toFloatOrNull()?.let { price ->
            ethanolConsumption.toFloatOrNull()?.let { cons ->
                price / cons
            }
        }

    val gasolineCostKm =
        gasolinePrice.toFloatOrNull()?.let { price ->
            gasolineConsumption.toFloatOrNull()?.let { cons ->
                price / cons
            }
        }

    AnimatedVisibility(
        visible = true,
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
                    .padding(12.dp)
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

                if (useConsumption) {
                    Text(
                        text = "Cálculo baseado no consumo do veículo",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider()

                    CostRow(
                        label = "Etanol",
                        value = ethanolCostKm,
                        highlight = fuelType == FuelType.ETANOL
                    )

                    CostRow(
                        label = "Gasolina",
                        value = gasolineCostKm,
                        highlight = fuelType == FuelType.GASOLINA
                    )
                } else {
                    Text(
                        text = "Etanol está $percentage% do valor da gasolina",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // 🔁 Recalcular
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = onRecalculate,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = color
                        ),
                        border = ButtonDefaults.outlinedButtonBorder().copy(
                            brush = SolidColor(color)
                        )
                    ) {
                        Icon(Icons.Default.Refresh, null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Recalcular")
                    }

                    // 📤 Compartilhar
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val message = buildString {
                                appendLine("🚗 Combustível Ideal")
                                appendLine()

                                if (useConsumption && ethanolCostKm != null && gasolineCostKm != null) {
                                    appendLine("📊 Cálculo por consumo")
                                    appendLine()
                                    appendLine("⛽ Preços:")
                                    appendLine("• Etanol: R$ $ethanolPrice")
                                    appendLine("• Gasolina: R$ $gasolinePrice")
                                    appendLine()
                                    appendLine("🚘 Consumo:")
                                    appendLine("• Etanol: $ethanolConsumption km/l")
                                    appendLine("• Gasolina: $gasolineConsumption km/l")
                                    appendLine()
                                    appendLine("💰 Custo por km:")
                                    appendLine("• Etanol: R$ %.2f/km".format(ethanolCostKm))
                                    appendLine("• Gasolina: R$ %.2f/km".format(gasolineCostKm))
                                } else {
                                    appendLine("📊 Etanol está $percentage% do valor da gasolina")
                                }

                                appendLine()
                                appendLine("✅ Melhor opção: ${fuelType.name}")
                                appendLine()
                                appendLine("Calcule você também!")
                            }

                            shareText(context, message)
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = color
                        ),
                        border = ButtonDefaults.outlinedButtonBorder().copy(
                            brush = SolidColor(color)
                        )
                    ) {
                        Icon(Icons.Default.Share, null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Compartilhar")
                    }
                }
            }
        }
    }
}

@Composable
private fun CostRow(
    label: String,
    value: Float?,
    highlight: Boolean
) {
    val color = if (highlight)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(
            text = value?.let { "R$ %.2f / km".format(it) } ?: "--",
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal,
            color = color
        )
    }
}