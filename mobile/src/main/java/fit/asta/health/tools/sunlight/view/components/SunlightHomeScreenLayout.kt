package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun SunlightHomeScreenLayout() {
    Column(Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        Row(Modifier.fillMaxWidth()) {
            Text(text = "Upcoming Slots",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color(0xffFFFFFF))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                UpcomingSlotsCard()
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(Modifier
                .fillMaxWidth()
                .weight(1f)) {
                UpcomingSlotsCard()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier.fillMaxWidth()) {
            Text(text = "Total Duration",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color(0xffFFFFFF))
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardSunBurn()

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier.fillMaxWidth()) {
            Text(text = "Total Vitamin D ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.4.sp,
                color = Color(0xffFFFFFF))
        }

        Spacer(modifier = Modifier.height(24.dp))

        TotalVitaminDCard()

    }
}