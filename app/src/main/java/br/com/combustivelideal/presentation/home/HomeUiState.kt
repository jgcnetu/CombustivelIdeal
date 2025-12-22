package br.com.combustivelideal.presentation.home

import br.com.combustivelideal.domain.model.FuelType

data class HomeUiState(
    val ethanolPrice: String = "",
    val gasolinePrice: String = "",
    val progress: Float = 0f,
    val fuelType: FuelType? = null,
    val resultText: String = "",
    val showResult: Boolean = false
) {

    /**
     * Botão "Calcular" só habilita
     * quando ambos os campos estão preenchidos
     */
    val isCalculateEnabled: Boolean
        get() = ethanolPrice.isNotBlank() && gasolinePrice.isNotBlank()
}