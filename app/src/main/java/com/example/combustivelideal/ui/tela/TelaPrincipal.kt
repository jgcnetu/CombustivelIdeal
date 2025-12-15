package com.example.combustivelideal.ui.tela

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.combustivelideal.ui.componente.*
import com.example.combustivelideal.viewmodel.CombustivelViewModel
import com.example.combustivelideal.util.GeradorMensagem
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState

@Composable
fun TelaPrincipal(
    viewModel: CombustivelViewModel = viewModel()
) {
    val estado = viewModel.estado
    val contexto = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var mostrarDialogoCompartilhar by remember { mutableStateOf(false) }
    var resultadoParaCompartilhar by remember { mutableStateOf<com.example.combustivelideal.modelo.ResultadoCalculo?>(null) }

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
            gasolina = estado.collectAsState().value.precoGasolina,
            etanol = estado.collectAsState().value.precoEtanol,
            aoGasolinaAlterar = viewModel::atualizarGasolina,
            aoEtanolAlterar = viewModel::atualizarEtanol,
            habilitado = !estado.collectAsState().value.carregando
        )

        // Botões
        BotoesAcao(
            habilitado = estado.collectAsState().value.camposPreenchidos && !estado.collectAsState().value.carregando,
            carregando = estado.collectAsState().value.carregando,
            aoCalcular = viewModel::calcular,
            aoLimpar = viewModel::limpar
        )

        // Resultado
        estado.collectAsState().value.resultado?.let { resultado ->
            if (resultado.sucesso) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CartaoResultado(resultado)

                    // Botão de compartilhar
                    BotaoCompartilhar(
                        resultado = resultado,
                        onCompartilharClick = {
                            resultadoParaCompartilhar = resultado
                            mostrarDialogoCompartilhar = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Erro
        estado.collectAsState().value.erro?.let { mensagemErro ->
            CartaoErro(mensagemErro)
        }

        // Informações
        CartaoInformacoes()

        // Espaço no final
        Spacer(modifier = Modifier.height(20.dp))
    }

    // Diálogo de compartilhamento
    DialogoCompartilhamento(
        resultado = resultadoParaCompartilhar,
        onCompartilharTexto = { texto ->
            compartilharTexto(contexto, texto)
        },
        onCompartilharWhatsApp = { texto ->
            compartilharWhatsApp(contexto, texto)
        },
        onCopiarTexto = { texto ->
            copiarParaAreaTransferencia(contexto, texto)
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Texto copiado!")
            }
        },
        onSalvarImagem = { texto ->
            // Implementar geração de imagem futuramente
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Funcionalidade em desenvolvimento!")
            }
        },
        onFechar = {
            mostrarDialogoCompartilhar = false
            resultadoParaCompartilhar = null
        }
    )

    // Snackbar
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    )
}

private fun compartilharTexto(contexto: Context, texto: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, texto)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(intent, "Compartilhar resultado")
    contexto.startActivity(shareIntent)
}

private fun compartilharWhatsApp(contexto: Context, texto: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, texto)
        type = "text/plain"
        setPackage("com.whatsapp")
    }
    try {
        contexto.startActivity(intent)
    } catch (e: Exception) {
        // WhatsApp não instalado, compartilhar normalmente
        compartilharTexto(contexto, texto)
    }
}

private fun copiarParaAreaTransferencia(contexto: Context, texto: String) {
    val clipboard = contexto.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Resultado Combustível", texto)
    clipboard.setPrimaryClip(clip)
}