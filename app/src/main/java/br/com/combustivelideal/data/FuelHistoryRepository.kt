package br.com.combustivelideal.data

import br.com.combustivelideal.data.local.dao.FuelHistoryDao
import br.com.combustivelideal.data.local.entity.FuelHistoryEntity
import kotlinx.coroutines.flow.Flow

class FuelHistoryRepository(
    private val dao: FuelHistoryDao
) {

    fun getHistory(): Flow<List<FuelHistoryEntity>> {
        return dao.getAll()
    }

    suspend fun saveHistory(
        ethanolPrice: Float,
        gasolinePrice: Float,
        useConsumption: Boolean,
        ethanolConsumption: Float?,
        gasolineConsumption: Float?,
        bestFuel: String
    ) {
        dao.insert(
            FuelHistoryEntity(
                ethanolPrice = ethanolPrice,
                gasolinePrice = gasolinePrice,
                useConsumption = useConsumption,
                ethanolConsumption = ethanolConsumption,
                gasolineConsumption = gasolineConsumption,
                bestFuel = bestFuel,
                createdAt = System.currentTimeMillis()
            )
        )
    }
}