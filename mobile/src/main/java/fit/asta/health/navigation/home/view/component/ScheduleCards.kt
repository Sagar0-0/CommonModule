package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaterScheduleCard(modifier: Modifier = Modifier) {

    val list = listOf("200 ml", "500 ml", "1 L", "More")


    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {

            ScheduleCardsImage()

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween) {

                ScheduleCardTitle(title = "Drink Water", value = "75%")


                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    ScheduleTodo(todo = "Target - 2.25L • Consumed - 1.75L")
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                    list.forEach {
                        WaterQuantitySelection(value = it)
                    }
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    ScheduleCardSelectionText(title = "Reschedule")
                }

            }

        }

    }

}

// Breathing, Sleep
@Composable
fun MediaTaskCard(
    modifier: Modifier = Modifier,
    cardTtl: String,
    cardValue: String,
    cardTodo: String,
    cardBtn: String,
) {

    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            ScheduleCardsImage()

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween) {

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    ScheduleCardTitle(title = cardTtl, value = cardValue)
                }

                Row(Modifier
                    .fillMaxWidth()
                    .padding(bottom = 35.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    ScheduleTodo(todo = cardTodo)
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    OutlineBtn(text = cardBtn)
                }

            }

        }

    }

}

//Steps, Sunlight
@Composable
fun SimpleTaskCard(
    modifier: Modifier = Modifier,
    cardTtl: String,
    cardValue: String,
    cardTodo: String,
    cardBtn: String,
) {

    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            ScheduleCardsImage()

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween) {

                ScheduleCardTitle(title = cardTtl, value = cardValue)

                Row(Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    ScheduleTodo(todo = cardTodo)
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    ScheduleCardSelectionText(title = cardBtn)
                }

            }

        }

    }

}

//Workout, fasting,Diet,Face Wash, Power Nap, Medicines
@Composable
fun TaskDoneCard(
    modifier: Modifier = Modifier,
    cardTtl: String,
    cardValue: String,
    cardTodo: String,
    cardBtn: String,
    time: String,
    scheduleTtl: String,
) {


    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {

        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Row(modifier = modifier.fillMaxWidth()) {

                ScheduleCardsImage()

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween) {

                    Row(Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = cardTtl,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff131723),
                            textAlign = TextAlign.Left)

                        CardValue(cardValue)
                    }

                    Row(Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        ScheduleTodo(todo = cardTodo)
                    }

                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        OutlineBtnTick(text = cardBtn)
                    }

                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            TodoAndSelection(time, scheduleTtl)

        }

    }


}

//Appointment Done or Cancel
@Composable
fun AppointmentDone(
    modifier: Modifier = Modifier,
    cardTtl: String,
    cardValue: String,
    cardTodo: String,
    cardBtn: String,
    time: String,
    scheduleTtl: String,
    doctorName: String,
    doctorSpecialization: String,
) {


    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {

        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)) {

            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {

                DoctorImg()

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween) {

                    Row(Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = cardTtl,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff131723),
                            textAlign = TextAlign.Left)

                        CardValue(cardTodo)
                    }

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "$doctorName \n$doctorSpecialization",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xff585964),
                            textAlign = TextAlign.Left)
                    }

                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        OutlineBtnTick(text = cardBtn)
                    }

                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            TodoAndSelection(time, scheduleTtl)

        }

    }

}