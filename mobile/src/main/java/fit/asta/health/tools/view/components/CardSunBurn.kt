package fit.asta.health.tools.view.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.sunlight.view.components.CircularSlider

@Composable
fun CardSunBurn(
    cardTitle: String = "Total",
    cardValue: String = "6 Litres",
    recommendedTitle: String = "Recommended",
    remainingValue: String = "3500 mL",
    goalTitle: String = "Goal",
    goalValue: String = "4000 mL",
    remainingTitle: String = "Remaining",
    recommendedValue: String = "2000 mL",
    valueChanged:((Float)->Unit)?
) {

    val angle = -60f + ((300f * cardValue[0].code) / 6)

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)) {
        Column(modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                CircularSlider(
                    modifier = Modifier.size(200.dp),
                    onChange = {
                        if (valueChanged != null) {
                            valueChanged(it)
                        }
                        Log.i("Circular SeeBar",it.toString())
                    },
                    angle1 = angle
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
            color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 19.6.sp,
            textAlign = TextAlign.Center)
    }

}