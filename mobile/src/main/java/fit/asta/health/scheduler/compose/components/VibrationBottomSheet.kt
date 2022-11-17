package fit.asta.health.scheduler.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
fun VibrationBottomSheetLayout(text: String, onNavigateBack: () -> Unit) {

    var value by remember {
        mutableStateOf(0f)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.IconButton(onClick = onNavigateBack) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.ic_round_close_24),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
            Text(
                text = text,
                fontSize = 20.sp,
                color = Color(0xff132839),
                textAlign = TextAlign.Center
            )
            androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_check_24),
                    contentDescription = null,
                    Modifier.size(24.dp),
                    tint = Color(0xff0088FF)
                )
            }
        }

        Slider(
            value = value,
            onValueChange = { value = it },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xff0088FF),
                activeTrackColor = Color(0xff0088FF)
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))


        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${value.toInt()}%",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xff0088FF)
            )
        }
    }

}