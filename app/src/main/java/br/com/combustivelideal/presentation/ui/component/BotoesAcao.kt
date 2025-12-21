package br.com.combustivelideal.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.combustivelideal.R

@Composable
fun BotoesAcao(
    habilitado: Boolean,
    carregando: Boolean,
    aoCalcular: () -> Unit,
    aoLimpar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Botão Limpar (30%)
        Button(
            onClick = aoLimpar,
            modifier = Modifier.weight(3.5f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            enabled = !carregando
        ){
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Limpar",
                modifier = Modifier.size(18.dp)
            ) //Clear

            Spacer(modifier = Modifier.width(8.dp))

            Text("Limpar")
        }

        // Botão Calcular (70%)
        Button(
            onClick = aoCalcular,
            modifier = Modifier.weight(6.5f),
            enabled = habilitado && !carregando
        ) {
            if (carregando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Icon(
                    painter = painterResource( R.drawable.calculate ),
                    contentDescription = "Calcular",
                    modifier = Modifier.size(20.dp)
                ) //calculate

                Spacer(modifier = Modifier.width(8.dp))

                Text("CALCULAR", fontWeight = FontWeight.Bold)
            }
        }
    }
}