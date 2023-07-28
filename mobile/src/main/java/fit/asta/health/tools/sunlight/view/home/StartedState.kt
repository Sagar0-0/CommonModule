package fit.asta.health.tools.sunlight.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.R
import fit.asta.health.common.ui.components.AppScaffold
import fit.asta.health.common.ui.components.AppTopBarWithHelp
import fit.asta.health.tools.sunlight.view.components.RoundedColorButton
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import fit.asta.health.tools.view.components.CardSunBurn

@Composable
fun StatedStateComposable(navController: NavController, homeViewModel: SunlightViewModel) {

    AppScaffold(
        topBar = {
            AppTopBarWithHelp(
                title = "Duration",
                onBack = { navController.popBackStack() },
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            item {
                Spacer(modifier = Modifier.height(150.dp))
            }
            item {
                Row(
                    Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CardSunBurn(
                        cardTitle = "Duration",
                        cardValue = "1 hr ",
                        recommendedTitle = "Vitamin D\nRecommended",
                        recommendedValue = "1hr 30 min",
                        goalTitle = "Vitamin D\nDaily Goal",
                        goalValue = "50 min",
                        remainingTitle = "Sunburn\nTime Remaining",
                        remainingValue = "30 min",
                        valueChanged = null
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Row {
                    RoundedColorButton(
                        color = Color(0XFF959393),
                        iconResId = R.drawable.pause,
                        text = "pause",
                        onClick = {})
                    Spacer(modifier = Modifier.width(8.dp))
                    RoundedColorButton(
                        color = Color(0XFF959393),
                        iconResId = R.drawable.stop,
                        text = "stop",
                        onClick = {
                            navController.navigate(route = "Home")
                        }
                    )
                }
            }
        }
    }
}