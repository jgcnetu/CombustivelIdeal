package br.com.combustivelideal.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.combustivelideal.data.local.dao.FuelHistoryDao
import br.com.combustivelideal.data.local.entity.FuelHistoryEntity

@Database(
    entities = [FuelHistoryEntity::class],
    version = 2, // ⬅️ aumente a versão
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun fuelHistoryDao(): FuelHistoryDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: AppDatabase

        fun init(context: Context) {
            if (!::INSTANCE.isInitialized) {
                synchronized(this) {
                    if (!::INSTANCE.isInitialized) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "combustivel_ideal.db"
                        )
                            .fallbackToDestructiveMigration(false)
                            .build()
                    }
                }
            }
        }

        fun getInstance(): AppDatabase {
            check(::INSTANCE.isInitialized) {
                "AppDatabase not initialized. Call AppDatabase.init(context) in Application"
            }
            return INSTANCE
        }
    }
}