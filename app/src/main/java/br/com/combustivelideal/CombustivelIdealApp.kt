package br.com.combustivelideal

import android.app.Application
import br.com.combustivelideal.data.local.database.AppDatabase

class CombustivelIdealApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}