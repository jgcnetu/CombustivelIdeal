package com.example.combustivelideal.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.combustivelideal.modelo.TipoCombustivel
import com.example.combustivelideal.principal.viewmodel.CombustivelViewModel
import com.example.combustivelideal.tema.TemaCombustivelIdeal
import com.example.combustivelideal.util.FormatadorUtil

@Composable
fun TelaPrincipal(viewModel: CombustivelViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Cabeçalho
        CabecalhoApp()

        // Campos de entrada
        CamposEntrada(
            gasolina = estado.precoGasolina,
            etanol = estado.precoEtanol,
            aoGasolinaAlterar = viewModel::atualizarGasolina,
            aoEtanolAlterar = viewModel::atualizarEtanol,
            habilitado = !estado.carregando
        )

        // Botões
        BotoesAcao(
            habilitado = estado.camposPreenchidos && !estado.carregando,
            carregando = estado.carregando,
            aoCalcular = viewModel::calcular,
            aoLimpar = viewModel::limpar
        )

        // Resultado
        estado.resultado?.let { resultado ->
            if (resultado.sucesso) {
                CartaoResultado(resultado)
            }
        }

        // Erro
        estado.erro?.let { mensagemErro ->
            CartaoErro(mensagemErro)
        }

        // Informações
        CartaoInformacoes()

        // Espaço no final
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun CabecalhoApp() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.LocalGasStation,
                contentDescription = "Combustível",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "COMBUSTÍVEL IDEAL",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = "Descubra qual vale mais a pena!",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CamposEntrada(
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

@Composable
private fun CampoPrecoCombustivel(
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

@Composable
private fun BotoesAcao(
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
            )

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
                    color = androidx.compose.ui.graphics.Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = "Calcular",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("CALCULAR", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun CartaoResultado(resultado: com.example.combustivelideal.modelo.ResultadoCalculo) {
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
                )

                // Barra visual
                BarraPorcentagem(porcentagem = porcentagem)

                // Regra dos 70%
                Text(
                    text = "Regra: Etanol vantajoso até 70%",
                    fontSize = 14.sp,
                    color = corTexto.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
private fun BarraPorcentagem(porcentagem: Double) {
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
        val posicao = (porcentagem / 100).coerceIn(0.0, 1.0)
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
        }
    }
}

@Composable
private fun CartaoErro(mensagem: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Erro",
                tint = MaterialTheme.colorScheme.error
            )

            Text(
                text = mensagem,
                color = MaterialTheme.colorScheme.onErrorContainer,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun CartaoInformacoes() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Informações",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Como funciona?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Conteúdo
            Text(
                text = "• Divida o preço do Etanol pelo da Gasolina;\n" +
                        "• Multiplique por 100 para obter a porcentagem;\n" +
                        "• Se o resultado for 70% ou menos, use ETANOL;\n" +
                        "• Se for acima de 70%, use GASOLINA.\n\n",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            // Legendas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                LegendaCor(
                    cor = MaterialTheme.colorScheme.primary,
                    texto = "Etanol vantajoso"
                )

                LegendaCor(
                    cor = MaterialTheme.colorScheme.secondary,
                    texto = "Gasolina vantajosa"
                )
            }
        }
    }
}


@Composable
private fun LegendaCor(
    cor: androidx.compose.ui.graphics.Color,
    texto: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(cor, shape = androidx.compose.foundation.shape.CircleShape)
        )

        Text(
            text = texto,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}