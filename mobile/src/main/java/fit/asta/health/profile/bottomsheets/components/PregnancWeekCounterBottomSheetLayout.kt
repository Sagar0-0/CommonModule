package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun PregnancyWeekCounterBottomSheetLayout() {

    Column(Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DividerLine()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Please input Pregnancy Week", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        WeekCounterLayout()

        Spacer(modifier = Modifier.height(20.dp))

        DoneButton()
    }
}

@Composable
fun WeekCounterLayout() {
    Row(Modifier
        .fillMaxWidth()
        .height(210.dp)) {
        Box(modifier = Modifier.fillMaxWidth(0.7f)) {
            Column(Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {
                for (i in 1..40) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = i.toString(), fontSize = 32.sp, lineHeight = 35.2.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 2.5.dp)) {
            Button(onClick = {},
                enabled = true,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)) {
                Text(text = "Week", fontSize = 16.sp, lineHeight = 22.4.sp, color = Color.White)
            }
        }

    }
}