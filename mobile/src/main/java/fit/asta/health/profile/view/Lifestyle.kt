package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.profile.view.components.UserLifeStyle

val currentActivitiesList = mutableListOf("Badminton", "Walking", "Cricket")
val preferredActivitiesList = mutableListOf("Workout", "Walking", "Yoga", "Meditation", "Dance")

@Preview(showSystemUi = true)
@Composable
fun LifeStyleLayout() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {
        UserLifeStyle(cardImg = R.drawable.indoorwork,
            cardType = "CURRENT WORK",
            cardValue = "SITTING")
        Spacer(modifier = Modifier.height(16.dp))
//        SelectionCard(
//            cardImg = R.drawable.currentactivities,
//            cardType = "CURRENT ACTIVITIES",
//            cardList = currentActivitiesList)
//        Spacer(modifier = Modifier.height(16.dp))
//        SelectionCard(
//            cardImg = R.drawable.preferredactivities,
//            cardType = "PREFERRED ACTIVITIES",
//            cardList = preferredActivitiesList
//        )
    }
}