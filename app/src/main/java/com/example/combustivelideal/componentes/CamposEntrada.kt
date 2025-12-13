package com.example.combustivelideal.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CamposEntrada(
    gasolina: String,
    etanol: String,
    aoGasolinaAlterar: (String) -> Unit,
    aoEtanolAlterar: (String) -> Unit,
    habilitado: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Gasolina
            CampoPrecoCombustivel(
                valor = gasolina,
                aoValorAlterar = aoGasolinaAlterar,
                rotulo = "Preço da Gasolina",
                corRotulo = MaterialTheme.colorScheme.secondary,
                placeholder = "Ex.: 5,99",
                icone = Icons.Default.LocalGasStation,
                corIcone = MaterialTheme.colorScheme.secondary,
                habilitado = habilitado
            )

            // Etanol
            CampoPrecoCombustivel(
                valor = etanol,
                aoValorAlterar = aoEtanolAlterar,
                rotulo = "Preço do Etanol",
                corRotulo = MaterialTheme.colorScheme.primary,
                placeholder = "Ex.: 3,49",
                icone = Icons.Default.LocalDrink,
                corIcone = MaterialTheme.colorScheme.primary,
                habilitado = habilitado
            )
        }
    }
}