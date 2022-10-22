package fit.asta.health.onboarding_screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationSection(currentPage: Int) {
    Row(modifier = Modifier
        .padding(bottom = 20.dp)
        .fillMaxWidth(),
        horizontalArrangement = if (currentPage != 2) Arrangement.SpaceBetween else Arrangement.Center) {

        if (currentPage == 2) {
            OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(percent = 50)) {
                Text(text = "Get Started",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp),
                    color = MaterialTheme.colors.primary)
            }
        } else {
            SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp))
            SkipNextButton(text = "Next", modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        }
    }
}

@Composable
fun SkipNextButton(text: String, modifier: Modifier) {
    Text(text = text, modifier = modifier, fontSize = 18.sp, fontWeight = FontWeight.Medium)
}