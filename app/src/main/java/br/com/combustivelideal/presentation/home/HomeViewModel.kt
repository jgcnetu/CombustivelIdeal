package br.com.combustivelideal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.combustivelideal.data.FuelHistoryRepository
import br.com.combustivelideal.domain.model.FuelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FuelHistoryRepository
) : ViewModel() {

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

        val ethanolPrice = state.ethanolPrice.replace(",", ".").toFloatOrNull() ?: return
        val gasolinePrice = state.gasolinePrice.replace(",", ".").toFloatOrNull() ?: return

        val progress: Float
        val fuelType: FuelType

        if (state.useConsumption) {
            val ethanolCons =
                state.ethanolConsumption.replace(",", ".").toFloatOrNull() ?: return
            val gasolineCons =
                state.gasolineConsumption.replace(",", ".").toFloatOrNull() ?: return

            val costEthanol = ethanolPrice / ethanolCons
            val costGasoline = gasolinePrice / gasolineCons

            progress = (costEthanol / costGasoline).coerceIn(0f, 1f)
            fuelType =
                if (costEthanol <= costGasoline) FuelType.ETANOL else FuelType.GASOLINA
        } else {
            progress = (ethanolPrice / gasolinePrice).coerceIn(0f, 1f)
            fuelType =
                if (progress <= 0.7f) FuelType.ETANOL else FuelType.GASOLINA
        }

        _uiState.update {
            it.copy(
                progress = progress,
                fuelType = fuelType,
                showResult = true
            )
        }

        salvarHistorico(
            ethanolPrice = ethanolPrice,
            gasolinePrice = gasolinePrice,
            fuelType = fuelType
        )
    }

    /* ---------- HISTORY ---------- */

    private fun salvarHistorico(
        ethanolPrice: Float,
        gasolinePrice: Float,
        fuelType: FuelType
    ) {
        val state = _uiState.value

        viewModelScope.launch {
            repository.saveHistory(
                ethanolPrice = ethanolPrice,
                gasolinePrice = gasolinePrice,
                useConsumption = state.useConsumption,
                ethanolConsumption = state.ethanolConsumption.toFloatOrNull(),
                gasolineConsumption = state.gasolineConsumption.toFloatOrNull(),
                bestFuel = fuelType.name
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