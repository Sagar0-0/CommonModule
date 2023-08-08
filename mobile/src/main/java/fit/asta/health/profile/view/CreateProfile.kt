@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.ThreeRadioBtnSelections
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.view.components.DisabledChipForList
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class ButtonListTypes(
    val buttonType: String,
)

@Composable
fun TwoTogglesGroup(
    selectionTypeText: String?,
    selectedOption: TwoRadioBtnSelections?,
    onStateChange: (TwoRadioBtnSelections) -> Unit,
    firstOption: String = "Yes",
    secondOption: String = "No",
) {
    Column(Modifier.fillMaxWidth()) {
        if (!selectionTypeText.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(spacing.small))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                AppTexts.TitleSmall(
                    text = selectionTypeText, color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf(
                TwoRadioBtnSelections.First, TwoRadioBtnSelections.Second
            ).forEach { option ->
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)
                ) {
                    AppButtons.AppRadioButton(selected = selectedOption == option,
                        onClick = { onStateChange(option) })
                    AppTexts.LabelSmall(
                        text = when (option) {
                            TwoRadioBtnSelections.First -> firstOption
                            TwoRadioBtnSelections.Second -> secondOption
                        }, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}


@Composable
fun ThreeTogglesGroups(
    selectionTypeText: String?,
    selectedOption: ThreeRadioBtnSelections?,
    onStateChange: (ThreeRadioBtnSelections) -> Unit,
    firstOption: String = "Male",
    secondOption: String = "Female",
    thirdOption: String = "Others",
) {
    Column(Modifier.fillMaxWidth()) {
        if (!selectionTypeText.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(spacing.small))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium)
            ) {
                AppTexts.TitleLarge(
                    text = selectionTypeText, color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf(
                ThreeRadioBtnSelections.First,
                ThreeRadioBtnSelections.Second,
                ThreeRadioBtnSelections.Third
            ).forEach { option ->
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)
                ) {
                    AppButtons.AppRadioButton(selected = selectedOption == option,
                        onClick = { onStateChange(option) })
                    AppTexts.LabelSmall(
                        text = when (option) {
                            ThreeRadioBtnSelections.First -> firstOption
                            ThreeRadioBtnSelections.Second -> secondOption
                            ThreeRadioBtnSelections.Third -> thirdOption
                        }, color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SelectionCardCreateProfile(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardType: String,
    cardList: SnapshotStateList<HealthProperties>?,
    onItemsSelect: () -> Unit,
    selectedOption: TwoRadioBtnSelections?,
    onStateChange: (TwoRadioBtnSelections) -> Unit,
    cardIndex: Int,
    composeIndex: ComposeIndex,
    listName: String = "",
) {
    Column(Modifier.fillMaxWidth()) {
        AppCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(spacing.small))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.medium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = CenterVertically
                    ) {
                        AppTexts.TitleSmall(text = cardType)
                    }
                    if (selectedOption == TwoRadioBtnSelections.First) {
                        ProfileAddIcon(onClick = onItemsSelect)
                    }
                }
                TwoTogglesGroup(
                    selectionTypeText = null,
                    selectedOption = selectedOption,
                    onStateChange = onStateChange
                )
                if (selectedOption == TwoRadioBtnSelections.First) {
                    FlowRow(
                        mainAxisSpacing = spacing.minSmall,
                        modifier = Modifier.padding(start = spacing.medium),
                    ) {
                        cardList?.forEach {
                            DisabledChipForList(textOnChip = it.name)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(spacing.small))
            }
        }
        if (cardList != null) {
            ValidateListError(
                selectedOption = selectedOption, cardList = cardList, listName = listName
            )
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun OnlyChipSelectionCard(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardType: String,
    cardList: SnapshotStateList<HealthProperties>?,
    onItemsSelect: () -> Unit,
    cardIndex: Int,
    composeIndex: ComposeIndex,
) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(spacing.small))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = CenterVertically
                ) {
                    AppTexts.TitleSmall(text = cardType)
                }
                ProfileAddIcon(onClick = onItemsSelect)
            }
            Spacer(modifier = Modifier.height(spacing.small))
            FlowRow(
                mainAxisSpacing = spacing.minSmall,
                modifier = Modifier.padding(start = spacing.medium),
            ) {
                cardList?.forEach {
                    DisabledChipForList(textOnChip = it.name)
                }
            }
            Spacer(modifier = Modifier.height(spacing.small))
        }
    }
}


@Composable
private fun ProfileAddIcon(
    onClick: () -> Unit,
) {
    AppButtons.AppIconButton(onClick = onClick) {
        AppDefaultIcon(
            imageVector = Icons.Rounded.AddCircle,
            contentDescription = "Add Item",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ValidateListError(
    viewModel: ProfileViewModel = hiltViewModel(),
    selectedOption: TwoRadioBtnSelections?,
    cardList: SnapshotStateList<HealthProperties>,
    listName: String,
) {
    Row(Modifier.fillMaxWidth()) {
        if (selectedOption is TwoRadioBtnSelections.First && cardList.isEmpty()) {
            AppTexts.TitleSmall(
                text = viewModel.validateDataList(cardList, listName).asString(),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}