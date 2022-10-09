package fit.asta.health.navigation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.profile.components.SelectionCard
import fit.asta.health.navigation.profile.components.UserLifeStyle

val daysList =
    mutableListOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
val pref = mutableListOf("Veg","Non-Veg")

val cuisinesList = mutableListOf("North Indian", "Chinese", "Yoga", "South Indian", "Maharastrian")

val foodAllergiesList = mutableListOf("Sea Food", "Wheat", "Eggs", "Weight Loss", "Peanuts")

@Composable
fun DietLayout(m:Map<String,Any?>) {

    val l= mutableListOf<String>()
    for (i in m["nVeg"].toString()){
        if(i!=','){
            l.add(daysList[i.digitToInt()])
        }
    }
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {
        UserLifeStyle(cardImg = R.drawable.nonveg,
            cardType = "DIETARY PREFERENCE",
            cardValue = pref[m["pref"].toString().toDouble().toInt()])
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(
            cardImg = R.drawable.age,
            cardType = "DAYS YOU CONSUME NON-VEG",
            cardList = l)
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(
            cardImg = R.drawable.cuisine,
            cardType = "CUISINES",
            cardList = m["cns"] as List<String>
        )
        Spacer(modifier = Modifier.height(16.dp))
        SelectionCard(
            cardImg = R.drawable.foodrestrictions,
            cardType = "FOOD ALLERGIES",
            cardList = m["alg"] as List<String>)
    }
}
