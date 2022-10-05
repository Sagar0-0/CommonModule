package fit.asta.health.navigation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R

val medicationList = mutableListOf("Anxiety", "Cold", "IBS", "Diabetes", "HBP")
val foodRestrictionsList = mutableListOf("Gluten", "Milk", "Meat", "Mushrooms")
val ailmentsList =
    mutableListOf("Eye Sight", "Diabetes", "Acidity", "Hyper Tension", "HyperThyroid")
val targetList =
    mutableListOf("Skin Glow", "Flexibility", "Weight Loss", "Strength", "Concentration", "Muscles")


@Preview
@Composable
fun HealthLayout() {
    Card(modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xffF7F7F7)) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)) {
            SleepSchedule()
            Spacer(modifier = Modifier.height(16.dp))
            SelectionCard(cardImg = R.drawable.medications,
                cardType = "MEDICATION",
                cardList = medicationList)
            Spacer(modifier = Modifier.height(16.dp))
            SelectionCard(cardImg = R.drawable.foodrestrictions,
                cardType = "FOOD RESTRICTIONS",
                cardList = foodRestrictionsList)
            Spacer(modifier = Modifier.height(16.dp))
            SelectionCard(cardImg = R.drawable.ailements,
                cardType = "AILMENTS",
                cardList = ailmentsList)
            Spacer(modifier = Modifier.height(16.dp))
            SelectionCard(cardImg = R.drawable.targets, cardType = "TARGETS", cardList = targetList)
            Spacer(modifier = Modifier.height(16.dp))
            UpdateButton()
        }
    }
}


@Composable
fun SleepSchedule() {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp, shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "SLEEP SCHEDULE",
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xDE000000))
                Image(painter = painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            UserSleepHours()
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                UserSleepCycles(columnType = "BED TIME", columnValue = "10:30 PM")
                Spacer(modifier = Modifier.width(40.dp))
                UserSleepCycles(columnType = "WAKE UP", columnValue = "07:30 AM")
            }
        }
    }
}

@Composable
private fun UserSleepCycles(
    columnType: String,
    columnValue: String,
) {
    Column {
        Text(text = columnType,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xDE000000))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = columnValue,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xDE000000))
    }
}

@Composable
private fun UserSleepHours() {
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

@Composable
fun SelectionCard(
    cardImg: Int,
    cardType: String,
    cardList: MutableList<String>,
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
                    Image(painter = painterResource(id = cardImg),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = cardType,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp,
                        color = Color(0xDE000000))
                }
                AddIcon()
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
private fun AddIcon() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(24.dp)
            .clip(shape = CircleShape)
            .background(color = Color(0xff0088FF))) {
        Image(painter = painterResource(id = R.drawable.add),
            contentDescription = null,
            modifier = Modifier.size(14.dp))
    }
}

@Composable
private fun DeleteIcon() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(18.dp)
            .clip(shape = CircleShape)
            .background(color = Color(0x99000000))) {
        Image(painter = painterResource(id = R.drawable.removeiconv2),
            contentDescription = null,
            modifier = Modifier.size(7.5.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipsOnCards(
    textOnChip: String,
) {
    Chip(onClick = {},
        shape = RoundedCornerShape(32.dp),
        colors = ChipDefaults.chipColors(backgroundColor = Color(0x80D6D6D6))) {
        Text(text = textOnChip,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
            color = Color(0x99000000))
        Spacer(modifier = Modifier.width(4.dp))
        DeleteIcon()
    }
}