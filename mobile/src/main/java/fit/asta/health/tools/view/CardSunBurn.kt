package fit.asta.health.tools.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.sunlight.view.components.CircularSlider

@Composable
fun CardSunBurn(
    cardTitle: String,
    cardValue: String,
    recommendedTitle: String,
    goalTitle: String,
    remainingTitle: String,
    recommendedValue: String,
    goalValue: String,
    remainingValue: String,
) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x66959393))) {
        Column(modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                CircularSlider(
                    modifier = Modifier.size(200.dp),
                )
                Column {
                    Text(text = cardTitle,
                        fontSize = 14.sp,
                        lineHeight = 19.6.sp,
                        letterSpacing = 1.25.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center)
                    Text(text = cardValue,
                        fontSize = 18.sp,
                        lineHeight = 25.2.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TimingMeter(recommendedTitle,
                goalTitle,
                remainingTitle,
                recommendedValue,
                goalValue,
                remainingValue)
        }
    }

}

@Composable
fun TimingMeter(
    recommendedTitle: String,
    goalTitle: String,
    remainingTitle: String,
    recommendedValue: String,
    goalValue: String,
    remainingValue: String,
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        TimingMeterLayout(title = recommendedTitle, titleValue = recommendedValue)
        Spacer(modifier = Modifier.width(24.dp))
        TimingMeterLayout(title = goalTitle, titleValue = goalValue)
        Spacer(modifier = Modifier.width(24.dp))
        TimingMeterLayout(title = remainingTitle, titleValue = recommendedValue)
    }

}

@Composable
fun TimingMeterLayout(title: String, titleValue: String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = titleValue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 19.6.sp,
            color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 19.6.sp,
            textAlign = TextAlign.Center)
    }

}


