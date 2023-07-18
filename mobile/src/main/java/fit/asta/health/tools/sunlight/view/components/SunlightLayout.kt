package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.view.components.CardSunBurn

@Composable
fun SunlightLayout(it: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(it)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(70.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {

            Text(text = "Upcoming Slots",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                //UpcomingSlotsCard()
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                //UpcomingSlotsCard()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
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
            cardValue = "1 hr ",
            recommendedTitle = "Vitamin D\nRecommended",
            recommendedValue = "1hr 30 min",
            goalTitle = "Vitamin D\nDaily Goal",
            goalValue = "50 min",
            remainingTitle = "Sunburn\nTime Remaining",
            remainingValue = "30 min",
        valueChanged = null)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
            Text(text = "Total Vitamin D ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TotalVitaminDCard()

        Spacer(modifier = Modifier.height(24.dp))

    }
}