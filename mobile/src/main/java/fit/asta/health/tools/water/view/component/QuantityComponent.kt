package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantityComponent(value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .clip(shape = CircleShape)
            .size(72.dp)) {
            Text(text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center)
        }
    }
}