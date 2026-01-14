package br.com.combustivelideal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_history")
data class FuelHistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val ethanolPrice: Float,
    val gasolinePrice: Float,

    val useConsumption: Boolean,

    val ethanolConsumption: Float?,
    val gasolineConsumption: Float?,

    val bestFuel: String,

    val createdAt: Long
)