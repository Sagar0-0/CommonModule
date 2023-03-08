package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.OnlyChipSelectionCard
import fit.asta.health.profile.view.SelectionCardCreateProfile
import fit.asta.health.ui.spacing


@Composable
fun HealthContent(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val healthHistoryList = listOf("Diabetes", "Heart Disease", "Stroke", "Depression")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))
    val healthHistoryList3 = listOf("6 Months Ago")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(spacing.extraSmall))

        SelectionCardCreateProfile(
            cardType = "Any Significant Health history?",
            cardList = healthHistoryList,
            radioButtonList = radioButtonList,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        SelectionCardCreateProfile(
            cardType = "Any Significant Health history?",
            cardList = healthHistoryList,
            radioButtonList = radioButtonList,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        SelectionCardCreateProfile(
            cardType = "Any Significant Health history?",
            cardList = healthHistoryList,
            radioButtonList = radioButtonList,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        SelectionCardCreateProfile(
            cardType = "Any Significant Health history?",
            cardList = healthHistoryList,
            radioButtonList = radioButtonList,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        OnlyChipSelectionCard(
            cardType = "When were you Injured?", cardList = healthHistoryList3, checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        SelectionCardCreateProfile(
            cardType = "Any Significant Health history?",
            cardList = healthHistoryList,
            radioButtonList = radioButtonList,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        CreateProfileButtons(eventPrevious, eventNext, text = "Next")

        Spacer(modifier = Modifier.height(spacing.medium))

        SkipPage(onSkipEvent = onSkipEvent)

        Spacer(modifier = Modifier.height(spacing.medium))
    }

}