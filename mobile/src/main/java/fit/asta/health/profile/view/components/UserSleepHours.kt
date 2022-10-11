package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun UserSleepHours() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Box(contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.timer),
                contentDescription = null,
                modifier = Modifier.size(240.dp))
            Text(text = "8:30\nHours",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.18.sp,
                lineHeight = 24.sp,
                color = Color(0xff707070),
                textAlign = TextAlign.Center)
        }
    }
}