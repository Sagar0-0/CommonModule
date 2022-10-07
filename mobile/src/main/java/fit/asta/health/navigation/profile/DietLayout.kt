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
import fit.asta.health.navigation.profile.components.UserLifeStyle

val daysList =
    mutableListOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

val cuisinesList = mutableListOf("North Indian", "Chinese", "Yoga", "South Indian", "Maharastrian")

val foodAllergiesList = mutableListOf("Sea Food", "Wheat", "Eggs", "Weight Loss", "Peanuts")

@Preview(showSystemUi = true)
@Composable
fun DietLayout() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {
        UserLifeStyle(cardImg = R.drawable.nonveg,
            cardType = "DIETARY PREFERENCE",
            cardValue = "NON-VEG")
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(cardImg = R.drawable.age,
            cardType = "DAYS YOU CONSUME NON-VEG",
            cardList = daysList)
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(cardImg = R.drawable.cuisine,
            cardType = "CUISINES",
            cardList = cuisinesList)
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(cardImg = R.drawable.foodrestrictions,
            cardType = "FOOD ALLERGIES",
            cardList = foodAllergiesList)
    }
}
