package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.MultiToggleWithTitle
import fit.asta.health.profile.view.OnlyChipSelectionCard
import fit.asta.health.profile.view.SelectionOutlineButton
import fit.asta.health.ui.spacing

@Composable
fun LifeStyleContent(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    val healthHistoryList4 = listOf("Less", "Moderate", "Very")
    val checkedState = remember { mutableStateOf(true) }
    val radioButtonList3 = listOf(
        ButtonListTypes(buttonType = "Morning"),
        ButtonListTypes(buttonType = "Afternoon"),
        ButtonListTypes(buttonType = "Night")
    )
    val healthHistoryList5 = listOf("Cycling", "Walking", "Swimming", "Gym", "Dancing", "Bowling")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(spacing.extraSmall))

        SelectionOutlineButton(
            cardType = "Are you physically active ?", cardList = healthHistoryList4
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        SelectionOutlineButton(
            cardType = "Are you physically active ?", cardList = healthHistoryList4
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioButtonList3[0]) }


        MultiToggleWithTitle(
            selectionTypeText = "What are your working hours?",
            radioButtonList = radioButtonList3,
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        OnlyChipSelectionCard(
            cardType = "What activities are indulge in?",
            cardList = healthHistoryList5,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        OnlyChipSelectionCard(
            cardType = "What activities are indulge in?",
            cardList = healthHistoryList5,
            checkedState
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        CreateProfileButtons(eventPrevious, eventNext, text = "Next")

        Spacer(modifier = Modifier.height(spacing.medium))

        SkipPage(onSkipEvent = onSkipEvent)

        Spacer(modifier = Modifier.height(spacing.medium))
    }

}