package com.example.combustivelideal.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.combustivelideal.util.FormatadorUtil

@Composable
fun CampoPrecoCombustivel(
    valor: String,
    aoValorAlterar: (String) -> Unit,
    rotulo: String,
    corRotulo: androidx.compose.ui.graphics.Color,
    placeholder: String,
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    corIcone: androidx.compose.ui.graphics.Color,
    habilitado: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icone,
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