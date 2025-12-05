package com.example.combustivelideal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.combustivelideal.ui.theme.CombustivelIdealTheme

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
    var porcentagemCalculada by remember { mutableStateOf(0.0) }

    val calcularCombustivelIdeal = {
        val gasolina = precoGasolina.toDoubleOrNull()
        val etanol = precoEtanol.toDoubleOrNull()

        if (gasolina != null && etanol != null && gasolina > 0) {
            val porcentagem = (etanol / gasolina) * 100
            porcentagemCalculada = porcentagem

            resultado = if (porcentagem <= 70) {
                "⛽ Use ETANOL (${String.format("%.1f", porcentagem)}%)"
            } else {
                "⛽ Use GASOLINA (${String.format("%.1f", porcentagem)}%)"
            }
        } else {
            resultado = "⚠️ Digite valores válidos"
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
        Text(
            text = "⛽ Combustível Ideal",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Compare os preços para economizar",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(
                    value = precoGasolina,
                    onValueChange = { precoGasolina = it },
                    label = { Text("Preço da Gasolina R$/l")},
                    placeholder = {Text("Ex. 5.99")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = precoEtanol,
                    onValueChange = { precoEtanol = it },
                    label = { Text("Preço do Etanol R$/l")},
                    placeholder = {Text("Ex. 3.99")},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                Button(
                    onClick = calcularCombustivelIdeal,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    enabled = precoGasolina.isNotEmpty() && precoEtanol.isNotEmpty()
                ) {
                    Text(
                        text = "CALCULAR",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        resultado?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (it.contains("ETANOL"))
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (porcentagemCalculada > 0) {
                        val cor = if (porcentagemCalculada <= 70)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.secondary
                        Text(
                            text = "Relação: ${String.format("%.1f", porcentagemCalculada)}%",
                            fontSize = 16.sp,
                            color = cor,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Regra: em geral, abastecer com Etanol é vantajoso se estiver" +
                                    " custando até 70% do preço da Gasolina.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        } // Fechamento resultado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Como funciona?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "• Divida o preço do etanol pelo da gasolina\n" +
                            "• Se o resultado for ≤ 70%, escolha etanol\n" +
                            "• Se for > 70%, escolha gasolina\n" +
                            "• Exemplo: Etanol R$ 4,00 / Gasolina R$ 5,71 = 70%",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CombustivelIdealTheme {
        CombustivelIdealApp()
    }
}