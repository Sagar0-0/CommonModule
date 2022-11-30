package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Diet
import fit.asta.health.profile.view.components.UpdateButton


// Health Screen Layout

@Composable
fun DietLayout(
    diet: Diet,
    checkedState: MutableState<Boolean>,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        /*SleepSchedule(
            cardTitle = diet.title,
            bedTime = props[0].from.toString(),
            wakeUpTime = props[0].to.toString(),
            checkedState
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserLifeStyle(
            cardImg = profileItem.icon,
            cardType = profileItem.title,
            cardValue = props[0].name,
            checkedState
        )

        Spacer(modifier = Modifier.height(16.dp))

        SelectionCard(
            cardImg = profileItem.icon,
            cardType = profileItem.title,
            cardList = props,
            checkedState
        )*/

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            if (checkedState.value) {
                UpdateButton()
            }
        }
    }
}