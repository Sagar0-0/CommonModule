package fit.asta.health.tools.walking.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Preview
@Composable
fun HealthAndCalorieCard() {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        HealthComponentLayout()
    }

}

@Composable
fun HealthComponentLayout() {
    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        HealthComponent(title = "Distance", titleValue = "5 Km")
        HealthComponent(title = "Calories", titleValue = "400 kal")
        HealthComponent(title = "Heart Rate", titleValue = "72 bpm")
        HealthComponent(title = "Blood Pressure", titleValue = "120/80 hhmg")
    }
}


@Composable
fun HealthComponent(
    title: String,
    titleValue: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Icon(painter = painterResource(id = R.drawable.ic_baseline_favorite_24),
            contentDescription = null,
            modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, fontSize = 12.sp, color = Color.White, lineHeight = 19.6.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = titleValue,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 19.6.sp)
    }
}