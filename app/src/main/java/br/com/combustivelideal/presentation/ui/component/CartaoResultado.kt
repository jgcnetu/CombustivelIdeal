package br.com.combustivelideal.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.combustivelideal.domain.model.ResultadoCalculo
import br.com.combustivelideal.domain.model.TipoCombustivel
import br.com.combustivelideal.presentation.util.FormatadorUtil

@Composable
fun CartaoResultado(
    resultado: ResultadoCalculo,
    onCompartilharClick: (() -> Unit)? = null,
) {
    val corFundo = when (resultado.recomendacao) {
        TipoCombustivel.ETANOL -> MaterialTheme.colorScheme.primaryContainer
        TipoCombustivel.GASOLINA -> MaterialTheme.colorScheme.secondaryContainer
        null -> MaterialTheme.colorScheme.surfaceVariant
    }

    val corTexto = when (resultado.recomendacao) {
        TipoCombustivel.ETANOL -> MaterialTheme.colorScheme.onPrimaryContainer
        TipoCombustivel.GASOLINA -> MaterialTheme.colorScheme.onSecondaryContainer
        null -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = corFundo)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Ícone
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Recomendação",
                tint = corTexto,
                modifier = Modifier.size(48.dp)
            )

            // Mensagem
            Text(
                text = resultado.mensagem,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = corTexto,
                textAlign = TextAlign.Center
            )

            // Porcentagem
            resultado.porcentagem?.let { porcentagem ->
                Text(
                    text = "Relação: ${FormatadorUtil.formatarPorcentagem(porcentagem)}",
                    fontSize = 16.sp,
                    color = corTexto.copy(alpha = 0.8f)
                ) //Relação %

                // Barra visual
                BarraPorcentagem(modifier = Modifier.fillMaxWidth())

                // Preços
                if (resultado.precoGasolina != null && resultado.precoEtanol != null) {
                    Text(
                        text = "Gasolina: ${FormatadorUtil.formatarMoeda(resultado.precoGasolina)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = corTexto.copy(alpha = 0.7f)
                    ) //Gasolina

                    Text(
                        text = "Etanol: ${FormatadorUtil.formatarMoeda(resultado.precoEtanol)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = corTexto.copy(alpha = 0.7f)
                    ) //Etanol
                }
                // Regra dos 70%
                Text(
                    text = "Regra: Etanol vantajoso até 70%",
                    fontSize = 14.sp,
                    color = corTexto.copy(alpha = 0.6f)
                )
            }

            // Botão de compartilhar (opcional)
            onCompartilharClick?.let {
                Button(
                    onClick = it,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = corTexto,
                        contentColor = corFundo
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartilhar",
                        modifier = Modifier.size(20.dp)
                    ) //Compartilhar
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compartilhar Resultado")
                }
            }
        }
    }
}