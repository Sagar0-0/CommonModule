package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun SleepSchedule(
    cardTitle: String,
    bedTime: String,
    wakeUpTime: String,
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp, shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = cardTitle,
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xDE000000))
                Image(painter = painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                UserSleepCycles(columnType = "BED TIME", columnValue = "$bedTime PM")
                Spacer(modifier = Modifier.width(40.dp))
                UserSleepCycles(columnType = "WAKE UP", columnValue = "$wakeUpTime AM")
            }
        }
    }
}