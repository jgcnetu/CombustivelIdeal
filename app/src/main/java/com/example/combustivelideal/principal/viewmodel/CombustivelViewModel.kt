package com.example.combustivelideal.principal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.combustivelideal.modelo.ResultadoCalculo
import com.example.combustivelideal.util.FormatadorUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EstadoCombustivel(
    val precoGasolina: String = "",
    val precoEtanol: String = "",
    val carregando: Boolean = false,
    val resultado: ResultadoCalculo? = null,
    val erro: String? = null
) {
    // Verifica se ambos os campos estão preenchidos
    val camposPreenchidos: Boolean
        get() = precoGasolina.isNotBlank() && precoEtanol.isNotBlank()

    // Verifica se os valores são válidos
    val valoresValidos: Boolean
        get() = FormatadorUtil.validarPreco(precoGasolina) &&
                FormatadorUtil.validarPreco(precoEtanol)
}

class CombustivelViewModel : ViewModel() {

    // Estado da tela
    private val _estado = MutableStateFlow(EstadoCombustivel())
    val estado: StateFlow<EstadoCombustivel> = _estado.asStateFlow()

    // Atualiza preço da gasolina
    fun atualizarGasolina(valor: String) {
        _estado.value = _estado.value.copy(
            precoGasolina = valor,
            erro = null
        )
    }

    // Atualiza preço do etanol
    fun atualizarEtanol(valor: String) {
        _estado.value = _estado.value.copy(
            precoEtanol = valor,
            erro = null
        )
    }

    // Realiza cálculo
    fun calcular() {
        viewModelScope.launch {
            // Validação inicial
            if (!_estado.value.valoresValidos) {
                _estado.value = _estado.value.copy(
                    erro = "❌ Digite valores válidos para ambos os combustíveis",
                    resultado = null
                )
                return@launch
            }

            // Marcar como carregando
            _estado.value = _estado.value.copy(carregando = true)

            try {
                // Converter strings para double
                val gasolina = FormatadorUtil.stringParaDouble(_estado.value.precoGasolina)!!
                val etanol = FormatadorUtil.stringParaDouble(_estado.value.precoEtanol)!!

                // Realizar cálculo
                val resultado = ResultadoCalculo.calcular(gasolina, etanol)

                // Atualizar estado com resultado
                _estado.value = _estado.value.copy(
                    carregando = false,
                    resultado = resultado,
                    erro = if (!resultado.sucesso) resultado.erro else null
                )
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(
                    carregando = false,
                    erro = "⚠️ Erro no cálculo: ${e.message}",
                    resultado = null
                )
            }
        }
    }

    // Limpa todos os campos
    fun limpar() {
        _estado.value = EstadoCombustivel()
    }
}