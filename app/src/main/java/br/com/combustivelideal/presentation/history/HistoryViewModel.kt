package br.com.combustivelideal.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.combustivelideal.data.FuelHistoryRepository
import br.com.combustivelideal.data.local.database.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel : ViewModel() {

    private val repository = FuelHistoryRepository(
        AppDatabase.getInstance().fuelHistoryDao()
    )

    val history = repository.getHistory()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )
}