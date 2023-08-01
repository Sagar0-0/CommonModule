package fit.asta.health.tools.breathing.view.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.components.functional.WeatherCardImage
import fit.asta.health.tools.view.components.CardSunBurn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreathingSliderCard()
        }
    }
}

@Composable
fun BreathingSliderCard() {
    //paddingValues: PaddingValues
    Column(modifier = Modifier
        .fillMaxWidth()
        // .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
            Text(text = "Total Duration",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardSunBurn(cardTitle = "Duration",
            cardValue = "30 Minute",
            recommendedTitle = "Recommended",
            recommendedValue = "60 min",
            goalTitle = "Goal",
            goalValue = "90 min",
            remainingTitle = "Achieved",
            remainingValue = "40 min",
        valueChanged = null)

        Spacer(modifier = Modifier.height(24.dp))

        /* Row(Modifier
             .fillMaxWidth()
             .padding(horizontal = 16.dp)) {
             Text(text = "Health Details",
                 fontWeight = FontWeight.Bold,
                 fontSize = 16.sp,
                 lineHeight = 22.4.sp,
                 color = Color.Black)
         }*/

        Spacer(modifier = Modifier.height(24.dp))

        // HealthAndCalorieCard()

        // Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
            Text(text = "Weather Details",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(24.dp))

        WeatherCardImage(temperature = "18",
            location = "Bengaluru",
            date = "Friday 04, November",
            modifier = Modifier.padding(horizontal = 16.dp))

        Spacer(modifier = Modifier.height(64.dp))

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(Modifier.fillMaxSize()) {
        BreathingSliderCard()
    }

}