package com.example.combustivelideal.principal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.combustivelideal.componentes.BotoesAcao
import com.example.combustivelideal.componentes.CabecalhoApp
import com.example.combustivelideal.componentes.CamposEntrada
import com.example.combustivelideal.componentes.CartaoErro
import com.example.combustivelideal.componentes.CartaoInformacoes
import com.example.combustivelideal.componentes.CartaoResultado
import com.example.combustivelideal.principal.viewmodel.CombustivelViewModel

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