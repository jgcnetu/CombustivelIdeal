package br.com.combustivelideal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.combustivelideal.domain.model.FuelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onGasolinePriceChange(value: String) {
        _uiState.update {
            it.copy(gasolinePrice = value)
        }
    }

    fun onEthanolPriceChange(value: String) {
        _uiState.update {
            it.copy(ethanolPrice = value)
        }
    }

    fun limparCampos() {
        _uiState.update {
            it.copy(
                ethanolPrice = "",
                gasolinePrice = "",
                progress = 0f,
                fuelType = null,
                resultText = "",
                showResult = false
            )
        }
    }

    fun calcularMelhorOpcao() {
        viewModelScope.launch {
            val gasolina = _uiState.value.gasolinePrice.toFloatOrNull()
            val etanol = _uiState.value.ethanolPrice.toFloatOrNull()

            if (gasolina == null || etanol == null || gasolina <= 0f) {
                _uiState.update {
                    it.copy(
                        resultText = "Valores inválidos",
                        progress = 0f,
                        fuelType = null
                    )
                }
                return@launch
            }

            val ratio = etanol / gasolina

            val fuelType = if (ratio < 0.7f) {
                FuelType.ETANOL
            } else {
                FuelType.GASOLINA
            }

            val resultText = if (fuelType == FuelType.ETANOL) {
                "Melhor opção: Etanol"
            } else {
                "Melhor opção: Gasolina"
            }

            _uiState.update {
                it.copy(
                    progress = ratio.coerceIn(0f, 1f),
                    fuelType = fuelType,
                    resultText = resultText,
                    showResult = true
                )
            }
        }
    }
    fun calcularNovamente() {
        _uiState.update {
            it.copy(
                showResult = false,
                progress = 0f,
                fuelType = null,
                resultText = ""
            )
        }
    }
}