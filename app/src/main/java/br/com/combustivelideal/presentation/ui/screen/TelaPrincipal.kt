package br.com.combustivelideal.presentation.ui.screen

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.combustivelideal.domain.model.ResultadoCalculo
import br.com.combustivelideal.presentation.viewmodel.CombustivelViewModel
import br.com.combustivelideal.presentation.ui.component.BotaoCompartilhar
import br.com.combustivelideal.presentation.ui.component.BotoesAcao
import br.com.combustivelideal.presentation.ui.component.CabecalhoApp
import br.com.combustivelideal.presentation.ui.component.CamposEntrada
import br.com.combustivelideal.presentation.ui.component.CartaoErro
import br.com.combustivelideal.presentation.ui.component.CartaoInformacoes
import br.com.combustivelideal.presentation.ui.component.CartaoResultado
import br.com.combustivelideal.presentation.ui.component.DialogoCompartilhamento
import kotlinx.coroutines.launch

@Composable
fun TelaPrincipal(
    viewModel: CombustivelViewModel = viewModel()
) {
    val estado = viewModel.estado
    val contexto = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var resultadoParaCompartilhar by remember { mutableStateOf< ResultadoCalculo? >( null ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

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
                        onCompartilharClick = {
                            resultadoParaCompartilhar = resultado
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
        onFechar = {
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
    runCatching {
        contexto.startActivity(intent)
    }.onFailure { e ->
        when (e) {
            is ActivityNotFoundException -> {
                // Log specifically for missing app
                compartilharTexto(contexto, texto)
            }
            else -> {
                // Log or handle unexpected errors
                Log.e("ShareError", "Erro inesperado: ${e.message}")
            }
        }
    }
}

private fun copiarParaAreaTransferencia( contexto: Context, texto: String ) {
    val clipboard = contexto.getSystemService( Context.CLIPBOARD_SERVICE ) as ClipboardManager
    val clip = ClipData.newPlainText( "Resultado Combustível", texto )
    clipboard.setPrimaryClip( clip )
}