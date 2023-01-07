package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.view.AddressType
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.OnlyChipSelectionCard
import fit.asta.health.profile.view.SelectionOutlineButton

@Preview
@Composable
fun LifeStyleContent(eventSkip: (() -> Unit)? = null, eventNext: (() -> Unit)? = null) {

    val healthHistoryList4 = listOf("Less", "Moderate", "Very")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList3 = listOf(ButtonListTypes(buttonType = "Morning"),
        ButtonListTypes(buttonType = "Afternoon"),
        ButtonListTypes(buttonType = "Night"))
    val healthHistoryList5 = listOf("Cycling", "Walking", "Swimming", "Gym", "Dancing", "Bowling")

    Card(shape = RoundedCornerShape(16.dp)) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                SelectionOutlineButton(cardType = "Are you physically active ?",
                    cardList = healthHistoryList4)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                SelectionOutlineButton(cardType = "Are you physically active ?",
                    cardList = healthHistoryList4)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                AddressType(selectionTypeText = "What are your working hours?",
                    radioButtonList = radioButtonList3)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                OnlyChipSelectionCard(cardType = "What activities are indulge in?",
                    cardList = healthHistoryList5,
                    checkedState)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                OnlyChipSelectionCard(cardType = "What activities are indulge in?",
                    cardList = healthHistoryList5,
                    checkedState)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                CreateProfileButtons(eventSkip, eventNext)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}