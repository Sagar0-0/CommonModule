package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R

@Preview
@Composable
fun CreateProfilePage2() {

    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))

    val radioButtonList2 = listOf(ButtonListTypes(buttonType = "Yes"),
        ButtonListTypes(buttonType = "No"),
        ButtonListTypes(buttonType = "Don't Know"))

    val radioButtonList3 = listOf(ButtonListTypes(buttonType = "Morning"),
        ButtonListTypes(buttonType = "Afternoon"),
        ButtonListTypes(buttonType = "Night"))

    val healthHistoryList = listOf("Diabetes", "Heart Disease", "Stroke", "Depression")

    val healthHistoryList2 = listOf("Leg", "Spine", "Head", "Arm")

    val healthHistoryList3 = listOf("6 Months Ago")

    val healthHistoryList4 = listOf("Less", "Moderate", "Very")

    val healthHistoryList5 = listOf("Cycling", "Walking", "Swimming", "Gym", "Dancing", "Bowling")

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.information),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth)
            }

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCardCreateProfile(cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList)

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCardCreateProfile(cardType = "Any Significant health Injuries ?",
                cardList = healthHistoryList2,
                radioButtonList = radioButtonList2)

            Spacer(modifier = Modifier.height(16.dp))

            OnlyChipSelectionCard(cardType = "When were you Injured?",
                cardList = healthHistoryList3)

            Spacer(modifier = Modifier.height(16.dp))

            SelectionOutlineButton(cardType = "Are you physically active ?",
                cardList = healthHistoryList4)

            Spacer(modifier = Modifier.height(16.dp))

            AddressType(selectionTypeText = "What are your working hours?",
                radioButtonList = radioButtonList3)

            Spacer(modifier = Modifier.height(16.dp))

            OnlyChipSelectionCard(cardType = "What activities are indulge in?",
                cardList = healthHistoryList5)

            Spacer(modifier = Modifier.height(16.dp))

            TimerButton()
        }
    }
}


@Composable
fun SelectionCardCreateProfile(
    cardType: String,
    cardList: List<String>,
    radioButtonList: List<ButtonListTypes>,
) {

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = cardType,
                        fontSize = 14.sp,
                        color = Color(0xff132839),
                        fontWeight = FontWeight.Medium)
                }
                AddIcon()
            }

            Spacer(modifier = Modifier.height(16.dp))

            RadioButtonCreateProfile(radioButtonList = radioButtonList)

            FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 4.dp) {
                cardList.forEach {
                    ChipsOnCards(textOnChip = it)
                }
            }
        }
    }
}


@Composable
fun RadioButtonCreateProfile(
    radioButtonList: List<ButtonListTypes>,
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList[0]) }

    Column(Modifier.fillMaxWidth()) {
        FlowRow {
            radioButtonList.forEach { text ->
                Box {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = (text == selectedOption), onClick = {
                            onOptionSelected(text)
                        }, colors = RadioButtonDefaults.colors(Color(0xff2F80ED)))
                        androidx.compose.material3.Text(text = text.buttonType,
                            fontSize = 16.sp,
                            lineHeight = 22.4.sp,
                            color = Color(0xff575757))
                    }
                }
            }
        }
    }
}


@Composable
fun OnlyChipSelectionCard(
    cardType: String,
    cardList: List<String>,
) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = cardType,
                        fontSize = 14.sp,
                        color = Color(0xff132839),
                        fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 4.dp) {
                cardList.forEach {
                    ChipsOnCards(textOnChip = it)
                }
            }

        }
    }
}


@Composable
fun SelectionOutlineButton(
    cardType: String,
    cardList: List<String>,
) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), elevation = 5.dp) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = cardType,
                        fontSize = 14.sp,
                        color = Color(0xff132839),
                        fontWeight = FontWeight.Medium)
                }
                AddIcon()
            }

            Spacer(modifier = Modifier.height(16.dp))


            FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 4.dp) {
                cardList.forEach {
                    OutlineButton(textOnChip = it)
                }
            }
        }
    }
}


@Composable
fun OutlineButton(textOnChip: String) {
    OutlinedButton(onClick = { /*TODO*/ },
        colors = ButtonDefaults.outlinedButtonColors(disabledContentColor = Color(0xffE7EAED)),
        shape = RoundedCornerShape(100.dp)) {
        Text(text = textOnChip,
            fontSize = 14.sp,
            letterSpacing = 0.25.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xDE000000))
    }
}


@Composable
fun TimerButton() {

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            Button(onClick = {},
                content = {
                    Text(text = "Skip",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xffD6D6D6)),
                modifier = Modifier.fillMaxWidth(0.4f))

            Spacer(modifier = Modifier.width(24.dp))

            Button(onClick = {},
                content = {
                    Text(text = "Done",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff0088FF)),
                modifier = Modifier.fillMaxWidth(0.9f))
        }
    }
}