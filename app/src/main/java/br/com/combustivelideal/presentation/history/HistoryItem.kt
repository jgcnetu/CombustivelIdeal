package br.com.combustivelideal.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.combustivelideal.data.local.entity.FuelHistoryEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryItem(
    item: FuelHistoryEntity
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            // 📅 Data e hora
            Text(
                text = formatDate(item.createdAt),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ⛽ Preços
            Text(
                text = "Etanol: R$ ${item.ethanolPrice}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Gasolina: R$ ${item.gasolinePrice}",
                style = MaterialTheme.typography.bodyMedium
            )

            // 🚗 Consumo (se usado)
            if (item.useConsumption) {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Consumo Etanol: ${item.ethanolConsumption} km/l",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Consumo Gasolina: ${item.gasolineConsumption} km/l",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ Resultado
            Text(
                text = "Melhor opção: ${item.bestFuel}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/* ---------- Utils ---------- */

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat(
        "dd/MM/yyyy • HH:mm",
        Locale.getDefault()
    )
    return formatter.format(Date(timestamp))
}