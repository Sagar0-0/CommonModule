package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.navigation.home.view.component.WeatherCardImage
import fit.asta.health.tools.view.components.CardSunBurn

@Composable
fun WalkingTypeLayout(paddingValues: PaddingValues) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = "Total Duration",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardSunBurn(cardTitle = "Duration",
            cardValue = "30 Minute",
            recommendedTitle = "Recommended",
            recommendedValue = "60 min",
            goalTitle = "Goal",
            goalValue = "90 min",
            remainingTitle = "Achieved",
            remainingValue = "40 min")

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = "Health Details",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        HealthAndCalorieCard()

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = "Weather Details",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        WeatherCardImage(temperature = "18",
            location = "Bengaluru",
            date = "Friday 04, November",
            modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(64.dp))

    }

}