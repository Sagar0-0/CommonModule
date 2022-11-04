package fit.asta.health.tools.sunlight.view.components

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

@Composable
fun SunlightLayout(it: PaddingValues) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(it)
        .verticalScroll(rememberScrollState())) {

        Spacer(modifier = Modifier.height(70.dp))

        Row(Modifier
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

        CardSunBurn()

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier
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