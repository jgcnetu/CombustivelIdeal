package br.com.combustivelideal.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.combustivelideal.R
import br.com.combustivelideal.presentation.components.buttons.ClearButton
import br.com.combustivelideal.presentation.components.buttons.NeonButton
import br.com.combustivelideal.presentation.components.cards.InfoCard
import br.com.combustivelideal.presentation.components.cards.ResultCard
import br.com.combustivelideal.presentation.components.effects.WaterDropEffect
import br.com.combustivelideal.presentation.components.indicators.NeonRingWithDrop

@Composable

fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // ⛽ Ícone
        Icon(
            painter = painterResource(R.drawable.local_gas_station),
            contentDescription = "Combustível",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )

        // 🔤 Título
        Text(
            text = "COMBUSTÍVEL IDEAL",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )

        Text(
            text = "Descubra qual vale mais a pena!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔥 Ring
        NeonRingWithDrop(
            progress = uiState.progress,
            fuelType = uiState.fuelType
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ⛽ Etanol
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.local_drink),
                contentDescription = "Etanol",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            OutlinedTextField(
                value = uiState.ethanolPrice,
                onValueChange = viewModel::onEthanolPriceChange,
                label = { Text("Preço do Etanol (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // ⛽ Gasolina
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.local_gas_station),
                contentDescription = "Gasolina",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(32.dp)
            )
            OutlinedTextField(
                value = uiState.gasolinePrice,
                onValueChange = viewModel::onGasolinePriceChange,
                label = { Text("Preço da Gasolina (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                    focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                    focusedTextColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = uiState.useConsumption,
                    onCheckedChange = viewModel::onUseConsumptionToggle
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text("Usar consumo do veículo")
            }

            if (uiState.useConsumption) {

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                    painter = painterResource(R.drawable.speed),
                    contentDescription = "Consumo em Etanol",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                    OutlinedTextField(
                        value = uiState.ethanolConsumption,
                        onValueChange = viewModel::onEthanolConsumptionChange,
                        label = { Text("Consumo em Etanol (km/l)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                    painter = painterResource(R.drawable.speed),
                    contentDescription = "Consumo em Gasolina",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(32.dp)
                )
                    OutlinedTextField(
                        value = uiState.gasolineConsumption,
                        onValueChange = viewModel::onGasolineConsumptionChange,
                        label = { Text("Consumo em Gasolina (km/l)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                            cursorColor = MaterialTheme.colorScheme.tertiary,
                            focusedTextColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔘 Botões
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ClearButton(
                text = "Limpar",
                onClick = viewModel::limparCampos,
                modifier = Modifier.weight(0.4f)
            )
            NeonButton(
                text = "Calcular",
                onClick = viewModel::calcularMelhorOpcao,
                enabled = uiState.isCalculateEnabled,
                modifier = Modifier.weight(0.6f)
            )
        }
        // 📊 Resultado (FORA DO ROW)
            ResultCard(
                visible = uiState.showResult,
                fuelType = uiState.fuelType,
                progress = uiState.progress,
                useConsumption = uiState.useConsumption,
                ethanolPrice = uiState.ethanolPrice,
                gasolinePrice = uiState.gasolinePrice,
                ethanolConsumption = uiState.ethanolConsumption,
                gasolineConsumption = uiState.gasolineConsumption,
                onRecalculate = viewModel::calcularNovamente
            )

            // ℹ️ Info
        InfoCard(
            visible = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        }
        // 🌊 Efeito de gota d’água em tela cheia
        WaterDropEffect(
            color = MaterialTheme.colorScheme.primary
        )
    }
}