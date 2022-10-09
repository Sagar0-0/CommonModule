package fit.asta.health.navigation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.profile.components.SelectionCard
import fit.asta.health.navigation.profile.components.SleepSchedule
import fit.asta.health.navigation.profile.components.UpdateButton


// Health Screen Layout

val medicationList = mutableListOf("Anxiety", "Cold", "IBS", "Diabetes", "HBP")
val foodRestrictionsList = mutableListOf("Gluten", "Milk", "Meat", "Mushrooms")
val ailmentsList =
    mutableListOf("Eye Sight", "Diabetes", "Acidity", "Hyper Tension", "HyperThyroid")
val targetList =
    mutableListOf("Skin Glow", "Flexibility", "Weight Loss", "Strength", "Concentration", "Muscles")

@Preview(showSystemUi = true)
@Composable
fun HealthLayout() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {
        SleepSchedule()
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(
            cardImg = R.drawable.medications,
            cardType = "MEDICATION",
            cardList = medicationList)
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(
            cardImg = R.drawable.foodrestrictions,
            cardType = "FOOD RESTRICTIONS",
            cardList = foodRestrictionsList)
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(
            cardImg = R.drawable.ailements,
            cardType = "AILMENTS",
            cardList = ailmentsList)
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(cardImg = R.drawable.targets, cardType = "TARGETS", cardList = targetList)
        Spacer(modifier = Modifier.height(16.dp))
        UpdateButton()
    }
}


