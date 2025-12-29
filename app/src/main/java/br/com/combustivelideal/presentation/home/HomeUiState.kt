package br.com.combustivelideal.presentation.home

import br.com.combustivelideal.domain.model.FuelType

data class HomeUiState(
    val ethanolPrice: String = "",
    val gasolinePrice: String = "",

    val useConsumption: Boolean = false,
    val ethanolConsumption: String = "",
    val gasolineConsumption: String = "",

    val progress: Float = 0f,
    val fuelType: FuelType? = null,

    val resultText: String = "",
    val showResult: Boolean = false,
    val isCalculateEnabled: Boolean = false
)