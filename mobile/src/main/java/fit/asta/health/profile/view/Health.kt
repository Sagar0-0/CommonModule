package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.profile.model.domain.ProfileItem
import fit.asta.health.profile.view.components.SelectionCard
import fit.asta.health.profile.view.components.SleepSchedule
import fit.asta.health.profile.view.components.UpdateButton
import fit.asta.health.profile.view.components.UserLifeStyle


// Health Screen Layout

val medicationList = mutableListOf("Anxiety", "Cold", "IBS", "Diabetes", "HBP")
val foodRestrictionsList = mutableListOf("Gluten", "Milk", "Meat", "Mushrooms")
val ailmentsList =
    mutableListOf("Eye Sight", "Diabetes", "Acidity", "Hyper Tension", "HyperThyroid")
val targetList =
    mutableListOf("Skin Glow", "Flexibility", "Weight Loss", "Strength", "Concentration", "Muscles")

@Composable
fun HealthLayout(userHealth: ArrayList<ProfileItem>) {

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {


        userHealth.forEach {
            Display(profileItem = it)

        }

        Spacer(modifier = Modifier.height(16.dp))
        UpdateButton()
    }
}

@Composable
fun Display(profileItem: ProfileItem) {

    Spacer(modifier = Modifier.height(16.dp))
    when (profileItem) {
        is ProfileItem.ChipCard -> SelectionCard(cardImg = R.drawable.medications,
            cardType = profileItem.title,
            cardList = profileItem.value)
        is ProfileItem.PlainCard -> UserLifeStyle(cardImg = R.drawable.indoorwork,
            cardType = "CURRENT WORK",
            cardValue = "SITTING")
        is ProfileItem.SessionCard -> SleepSchedule()
    }
}