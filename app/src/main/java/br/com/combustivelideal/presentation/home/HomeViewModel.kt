package br.com.combustivelideal.presentation.home

import androidx.lifecycle.ViewModel
import br.com.combustivelideal.domain.model.FuelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    /* ---------- PRICE INPUTS ---------- */

    fun onEthanolPriceChange(value: String) {
        _uiState.update { it.copy(ethanolPrice = value) }
        updateCalculateEnabled()
    }

    fun onGasolinePriceChange(value: String) {
        _uiState.update { it.copy(gasolinePrice = value) }
        updateCalculateEnabled()
    }

    /* ---------- CONSUMPTION INPUTS ---------- */

    fun onUseConsumptionToggle(enabled: Boolean) {
        _uiState.update {
            it.copy(
                useConsumption = enabled,
                ethanolConsumption = "",
                gasolineConsumption = ""
            )
        }
        updateCalculateEnabled()
    }

    fun onEthanolConsumptionChange(value: String) {
        _uiState.update { it.copy(ethanolConsumption = value) }
        updateCalculateEnabled()
    }

    fun onGasolineConsumptionChange(value: String) {
        _uiState.update { it.copy(gasolineConsumption = value) }
        updateCalculateEnabled()
    }

    /* ---------- CALCULATION ---------- */

    fun calcularMelhorOpcao() {
        val state = _uiState.value

        val ethanolPrice = state.ethanolPrice.toFloatOrNull() ?: return
        val gasolinePrice = state.gasolinePrice.toFloatOrNull() ?: return

        val progress: Float
        val fuelType: FuelType

        if (state.useConsumption) {
            val ethanolCons = state.ethanolConsumption.toFloatOrNull() ?: return
            val gasolineCons = state.gasolineConsumption.toFloatOrNull() ?: return

            val costEthanol = ethanolPrice / ethanolCons
            val costGasoline = gasolinePrice / gasolineCons

            progress = (costEthanol / costGasoline).coerceIn(0f, 1f)
            fuelType = if (costEthanol <= costGasoline) FuelType.ETANOL else FuelType.GASOLINA
        } else {
            progress = (ethanolPrice / gasolinePrice).coerceIn(0f, 1f)
            fuelType = if (progress <= 0.7f) FuelType.ETANOL else FuelType.GASOLINA
        }

        _uiState.update {
            it.copy(
                progress = progress,
                fuelType = fuelType,
                showResult = true
            )
        }
    }

    fun calcularNovamente() {
        _uiState.update { it.copy(showResult = false) }
    }

    fun limparCampos() {
        _uiState.value = HomeUiState()
    }

    /* ---------- VALIDATION ---------- */

    private fun updateCalculateEnabled() {
        val state = _uiState.value

        val pricesFilled =
            state.ethanolPrice.isNotBlank() &&
                    state.gasolinePrice.isNotBlank()

        val consumptionFilled =
            !state.useConsumption ||
                    (state.ethanolConsumption.isNotBlank() &&
                            state.gasolineConsumption.isNotBlank())

        _uiState.update {
            it.copy(isCalculateEnabled = pricesFilled && consumptionFilled)
        }
    }
}