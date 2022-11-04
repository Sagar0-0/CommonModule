package fit.asta.health.tools.walking.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.navigation.home.view.component.WeatherCardImage
import fit.asta.health.tools.sunlight.view.components.CardSunBurn
import fit.asta.health.tools.walking.view.component.HealthAndCalorieCard

@Composable
fun WalkingTypeLayout(it: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(it)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(72.dp))

        CardSunBurn()

        Spacer(modifier = Modifier.height(16.dp))

        HealthAndCalorieCard()

        Spacer(modifier = Modifier.height(16.dp))

        WeatherCardImage(temperature = "18",
            location = "Bengaluru",
            date = "Friday 04, November",
            modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(16.dp))

    }
}