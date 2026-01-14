package br.com.combustivelideal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.combustivelideal.presentation.navigation.AppNavGraph
import br.com.combustivelideal.presentation.ui.theme.CombustivelIdealTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            CombustivelIdealTheme {
                AppNavGraph()
            }
        }
    }
}