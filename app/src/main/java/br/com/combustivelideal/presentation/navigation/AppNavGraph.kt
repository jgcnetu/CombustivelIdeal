package br.com.combustivelideal.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.combustivelideal.presentation.history.HistoryScreen
import br.com.combustivelideal.presentation.home.HomeScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onHistoryClick = {
                        navController.navigate(Screen.History.route)
                    }
                )
            }

            composable(Screen.History.route) {
                HistoryScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}