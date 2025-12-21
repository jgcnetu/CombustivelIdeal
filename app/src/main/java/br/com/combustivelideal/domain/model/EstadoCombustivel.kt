package br.com.combustivelideal.domain.model

import br.com.combustivelideal.presentation.util.FormatadorUtil

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