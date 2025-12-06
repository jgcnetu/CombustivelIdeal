package com.example.combustivelideal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.combustivelideal.ui.theme.AmareloGasolina
import com.example.combustivelideal.ui.theme.AmareloGasolinaEscuro
import com.example.combustivelideal.ui.theme.CombustivelIdealTheme
import com.example.combustivelideal.ui.theme.VerdeEtanol
import com.example.combustivelideal.ui.theme.VerdeEtanolEscuro

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CombustivelIdealTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CombustivelIdealApp()
                }
            }
        }
    }
}

@Composable
fun CombustivelIdealApp() {

    var precoGasolina by remember { mutableStateOf("") }
    var precoEtanol by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<String?>(null) }
    var porcentagemCalculada by remember { mutableDoubleStateOf(0.0) }
    var combustivelRecomendado by remember { mutableStateOf("") }

    val calcularCombustivelIdeal = {
        val gasolina = precoGasolina.replace(",",".").toDoubleOrNull()
        val etanol = precoEtanol.replace(",",".").toDoubleOrNull()

        if (gasolina != null && etanol != null && gasolina > 0) {
            val porcentagem = (etanol / gasolina) * 100
            porcentagemCalculada = porcentagem

            if (porcentagem <= 70) {
                resultado = "⛽ ETANOL é mais vantajoso!)"
                combustivelRecomendado = "etanol"
            } else {
                resultado =  "⛽ GASOLINA é mais vantajosa!"
                combustivelRecomendado = "gasolina"
            }
        } else {
            resultado = null
            combustivelRecomendado = ""
        }
    } // Fechamento calcularCombustivelIdeal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabeçalho
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.LocalGasStation,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Titulo
                Text(
                    text = "Combustível Ideal",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                // Descrição
                Text(
                    text = "Descubra qual combustível vale mais a pena",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
        // Card Entrada de Dados
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Entrada Gasolina
                PrecoInput(
                    value = precoGasolina,
                    onValueChange = { precoGasolina = it },
                    label = "Preço da Gasolina R$/l",
                    placeholder = "Ex. 5.99",
                    icon = Icons.Filled.LocalGasStation,
                    containerColor = AmareloGasolina,
                    iconTint = AmareloGasolinaEscuro
                )
                // Entrada Etanol
                PrecoInput(
                    value = precoEtanol,
                    onValueChange = { precoEtanol = it },
                    label = "Preço do Etanol R$/l",
                    placeholder = "Ex. 3.99",
                    icon = Icons.Filled.LocalGasStation,
                    containerColor = VerdeEtanol,
                    iconTint = VerdeEtanolEscuro
                )
                // Botão Calcular
                Button(
                    onClick = calcularCombustivelIdeal,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    enabled = precoGasolina.isNotEmpty() && precoEtanol.isNotEmpty()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Calculate,
                        contentDescription = "Calcular",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CALCULAR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } // Fechamento Card Entrada de Dados
        // Resultado
        if (resultado != null) {
            val (containerColor, textColor, iconColor) = when (combustivelRecomendado) {
                "etanol" -> Triple(
                    VerdeEtanol.copy(alpha = 0.3f),
                    VerdeEtanolEscuro,
                    VerdeEtanolEscuro
                )
                "gasolina" -> Triple(
                    AmareloGasolina.copy(alpha = 0.3f),
                    AmareloGasolinaEscuro,
                    AmareloGasolinaEscuro
                )
                else -> Triple(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = containerColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalGasStation,
                        contentDescription = "Combustível Recomendado",
                        tint = iconColor,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = resultado ?: "",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )
                    if (porcentagemCalculada > 0) {
                        Text(
                            text = "Relação: ${String.format("%.1f", porcentagemCalculada)}%",
                            fontSize = 16.sp,
                            color = textColor.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        )
                        // Indicador Visual
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(70f)
                                    .fillMaxHeight()
                                    .background(VerdeEtanolEscuro.copy(alpha = 0.5f))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(70f)
                                    .fillMaxHeight()
                                    .background(AmareloGasolinaEscuro.copy(alpha = 0.5f))
                            )
                        }
                        // Regra dos 70%
                        Text(
                            text = "│─────────70%─────────│",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Etanol vantajoso até 70%.\nAcima de 70%, gasolina é melhor.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        } // Fechamento resultado
        // Informações
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Informações",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Como funciona a regra dos 70%?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    "1. Divida o preço do etanol pelo da gasolina;\n" +
                            "2. Multiplique por 100 para obter a porcentagem;\n" +
                            "3. Se o resultado for 70% ou menos, use ETANOL.\n" +
                            "4. Se for acima de 70%, use GASOLINA.\n\n" +
                            "Exemplo: Etanol R$ 4,00 ÷ Gasolina R$ 5,71 = 70%.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
                // Legenda de cores
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ColorLegend(
                        color = VerdeEtanolEscuro,
                        label = "Etanol Vantajoso"
                    )
                    ColorLegend(
                        color = AmareloGasolinaEscuro,
                        label = "Gasolina Vantajosa"
                    )
                }
            }
        }
    }
}

@Composable
fun PrecoInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    containerColor: Color,
    iconTint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = {Text(
                    label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )},
                placeholder = {Text(
                    placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )},
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = iconTint,
                    unfocusedBorderColor = iconTint.copy(alpha = 0.5f),
                    focusedLabelColor = iconTint,
                    cursorColor = iconTint
                )
            )
        }
    }
} // Fechamento fun PrecoInput

@Composable
fun ColorLegend(
    color: Color,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = androidx.compose.foundation.shape.CircleShape)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} // Fechamento Color Legend

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CombustivelIdealTheme {
        CombustivelIdealApp()
    }
}