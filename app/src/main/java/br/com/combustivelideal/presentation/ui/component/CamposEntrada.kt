package br.com.combustivelideal.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.combustivelideal.R
import br.com.combustivelideal.presentation.util.FormatadorUtil

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
                icone = painterResource( R.drawable.local_gas_station ),
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
                icone = painterResource( R.drawable.local_drink ),
                corIcone = MaterialTheme.colorScheme.primary,
                habilitado = habilitado
            )
        }
    }
}

@Composable
fun CampoPrecoCombustivel(
    valor: String,
    aoValorAlterar: (String) -> Unit,
    rotulo: String,
    corRotulo: Color,
    placeholder: String,
    icone: Painter,
    corIcone: Color,
    habilitado: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = icone,
            contentDescription = rotulo,
            tint = corIcone,
            modifier = Modifier.size(24.dp)
        )

        OutlinedTextField(
            value = valor,
            onValueChange = aoValorAlterar,
            label = { Text(rotulo) },
            placeholder = { Text(placeholder) },
            modifier = Modifier.weight(1f),
            singleLine = true,
            enabled = habilitado,
            isError = valor.isNotBlank() && !FormatadorUtil.validarPreco(valor),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = corRotulo,
                unfocusedLabelColor = corRotulo.copy(alpha = 0.5f),
                focusedBorderColor = corIcone,
                unfocusedBorderColor = corIcone.copy(alpha = 0.5f)
            )
        )
    }
}