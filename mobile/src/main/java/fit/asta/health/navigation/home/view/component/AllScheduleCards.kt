@file:Suppress("UNUSED_EXPRESSION")

package fit.asta.health.navigation.home.view.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllSchedulesCards() {

    Scaffold(topBar = {
        BottomNavigation(content = {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                        contentDescription = null,
                        Modifier.size(24.dp))
                }
                Text(text = "My Schedules",
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center)
            }
        }, elevation = 10.dp, backgroundColor = Color.White)
    }) {
        Items(paddingValues = it)
    }

}

@Composable
fun AllSchedulesLayout(idList: List<Int>, dayTime: String) {

    var expanded by remember { mutableStateOf(true) }

    val extraPadding by animateDpAsState(targetValue = if (expanded) 0.dp else 0.dp)

    Column(modifier = Modifier.padding(bottom = extraPadding.coerceAtLeast(0.dp))) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(text = dayTime,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black)

            IconButton(onClick = { expanded = !expanded }) {

                Icon(imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null)

            }
        }

        Row(Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
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
