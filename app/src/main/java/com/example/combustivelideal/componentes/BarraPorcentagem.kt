package com.example.combustivelideal.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarraPorcentagem(porcentagem: Double) {
    val corEtanol = MaterialTheme.colorScheme.primary
    val corGasolina = MaterialTheme.colorScheme.secondary

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // Barra
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                )
        ) {

            // Parte do etanol (até 70%)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(70f)
                    .background(
                        color = corEtanol.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = 6.dp,
                            bottomStart = 6.dp
                        )
                    )
            )

            // Parte da gasolina (acima de 70%)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(30f)
                    .background(
                        color = corGasolina.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topEnd = 6.dp,
                            bottomEnd = 6.dp
                        )
                    )
            )
        }

        // Marcadores
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text("0%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("70%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("100%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        // Indicador da porcentagem atual
        /*val posicao = (porcentagem / 100).coerceIn(0.0, 1.0)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = (posicao * 100).dp)
                    .size(8.dp)
                    .background(
                        color = if (porcentagem <= 70) corEtanol else corGasolina,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }*/
    }
}