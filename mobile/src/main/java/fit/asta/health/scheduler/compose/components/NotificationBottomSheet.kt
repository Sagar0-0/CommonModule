package fit.asta.health.scheduler.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R


@Composable
fun NotificationBottomSheetLayout(
    text: String,
    onNavigateBack: () -> Unit,
) {

    val radioOptions = listOf("Notification", "Splash")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


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
            androidx.compose.material3.Text(
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

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}