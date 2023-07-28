package fit.asta.health.navigation.home.view.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.components.AppScaffold
import fit.asta.health.common.ui.components.AppTopBar

@Preview
@Composable
fun AllSchedulesCards() {

    AppScaffold(topBar = {
        AppTopBar(
            title = "My Schedules",
            onBack = { }
        )
    }) {
        Items(paddingValues = it)
    }
}

@Composable
fun AllSchedulesLayout(idList: List<Int>, dayTime: String) {

    var expanded by remember { mutableStateOf(true) }

    val extraPadding by animateDpAsState(targetValue = if (expanded) 0.dp else 0.dp)

    Column(modifier = Modifier
        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
        ) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = dayTime,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground)

            IconButton(onClick = { expanded = !expanded }) {

                Icon(imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null)

            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(8.dp)) {
            Column {
                if (expanded) {
                    idList.forEach { id ->
                        when (id) {
                            0 -> WaterScheduleCard()
                            1 -> MediaTaskCard(cardTtl = "Breathing",
                                cardValue = "44%",
                                cardTodo = "20mins • 16 mins left",
                                cardBtn = "Continue Video")
                            2 -> SimpleTaskCard(cardTtl = "Steps",
                                cardValue = "84%",
                                cardTodo = "7 hr 30 minutes • 30 minutes left",
                                cardBtn = "Reschedule")
                            3 -> TaskDoneCard(cardTtl = "Fasting",
                                cardValue = "44%",
                                cardTodo = "Fasting to cleanse your body",
                                cardBtn = "Done",
                                time = "9:00am - 2:00pm",
                                scheduleTtl = "Reschedule")
                            else -> AppointmentDone(cardTtl = "Appointment",
                                cardTodo = "44%",
                                cardBtn = "Done",
                                time = "4:00pm",
                                scheduleTtl = "Reschedule",
                                doctorName = "Dr Deepika",
                                doctorSpecialization = "Physiotherapist")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }

    }

}

@Composable
fun Items(paddingValues: PaddingValues) {

    val morningIdList = listOf(0, 1, 2, 3, 4)
    val afterNoonIdList = listOf(3, 2, 4, 1, 0)
    val eveningIdList = listOf(2, 3, 1, 4, 0)

    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        item {
            for (i in 0..2) {
                when (i) {
                    0 -> AllSchedulesLayout(idList = morningIdList, dayTime = "Morning")
                    1 -> AllSchedulesLayout(idList = afterNoonIdList, dayTime = "Afternoon")
                    else -> AllSchedulesLayout(idList = eveningIdList, dayTime = "Evening")
                }
            }
        }

    }

}
