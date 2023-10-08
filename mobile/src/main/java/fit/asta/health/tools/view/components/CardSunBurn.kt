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
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
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

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.secondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularSlider(
                    modifier = Modifier.size(200.dp),
                    onChange = {
                        if (valueChanged != null) {
                            valueChanged(it)
                        }
                        Log.i("Circular SeeBar", it.toString())
                    },
                    angle1 = angle
                )
                Column {
                    BodyTexts.Level2(
                        text = cardTitle,
                        textAlign = TextAlign.Center
                    )
                    BodyTexts.Level2(
                        text = cardValue,
                        textAlign = TextAlign.Center
                    )
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
        BodyTexts.Level2(
            text = titleValue,
            color = AppTheme.colors.onPrimary
        )
        Spacer(modifier = Modifier.height(10.dp))
        BodyTexts.Level2(
            text = title,
            textAlign = TextAlign.Center
        )
    }

}