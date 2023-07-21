package fit.asta.health.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import fit.asta.health.main.ui.MainActivityComp
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.settings.ui.SettingsNavigation

@Composable
fun MainNavHost(isConnected: Boolean) {
    val navController = rememberNavController()
    if (!isConnected) {
        Box(modifier = Modifier.fillMaxSize()) {
            ErrorScreenLayout {

            }
        }
    }

    NavHost(
        navController = navController,
        route = Graph.ROOT.route,
        startDestination = Graph.Home.route
    ) {
        MainActivityComp(navController)
        SettingsNavigation(navController)
    }

}