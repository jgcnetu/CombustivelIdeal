package br.com.combustivelideal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.combustivelideal.data.local.entity.FuelHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelHistoryDao {

    @Insert
    suspend fun insert(history: FuelHistoryEntity)

    @Query("SELECT * FROM fuel_history ORDER BY createdAt DESC")
    fun getAll(): Flow<List<FuelHistoryEntity>>
}