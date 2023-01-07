package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.OnlyChipSelectionCard
import fit.asta.health.profile.view.SelectionCardCreateProfile


@Preview
@Composable
fun HealthContent(eventSkip: (() -> Unit)? = null, eventNext: (() -> Unit)? = null) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val healthHistoryList = listOf("Diabetes", "Heart Disease", "Stroke", "Depression")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))
    val healthHistoryList3 = listOf("6 Months Ago")

    Card(shape = RoundedCornerShape(16.dp)) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {

            SelectionCardCreateProfile(cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState)

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCardCreateProfile(cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState)

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCardCreateProfile(cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState)

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCardCreateProfile(cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState)

            Spacer(modifier = Modifier.height(16.dp))

            OnlyChipSelectionCard(cardType = "When were you Injured?",
                cardList = healthHistoryList3,
                checkedState)

            Spacer(modifier = Modifier.height(16.dp))

            SelectionCardCreateProfile(cardType = "Any Significant Health history?",
                cardList = healthHistoryList,
                radioButtonList = radioButtonList,
                checkedState)

            Spacer(modifier = Modifier.height(16.dp))

            CreateProfileButtons(eventSkip, eventNext)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}