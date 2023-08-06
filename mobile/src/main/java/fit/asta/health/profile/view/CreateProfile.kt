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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.createprofile.view.ValidateListError
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.ThreeRadioBtnSelections
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.view.components.RemoveChipOnCard
import fit.asta.health.profile.viewmodel.ProfileEvent
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
                    AppButtons.AppRadioButton(
                        selected = selectedOption == option,
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
                    AppButtons.AppRadioButton(
                        selected = selectedOption == option,
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
    cardIndex: Int? = null,
    composeIndex: ComposeIndex,
    listName: String = "",
) {
    Column(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
        ) {
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
                        Text(
                            text = cardType,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            style = MaterialTheme.typography.titleSmall
                        )
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
                    com.google.accompanist.flowlayout.FlowRow(
                        mainAxisSpacing = spacing.minSmall,
                        modifier = Modifier.padding(start = spacing.medium),
                    ) {
                        cardList?.forEach {
                            RemoveChipOnCard(textOnChip = it.name, onClick = {
                                cardIndex?.let { index ->
                                    ProfileEvent.SetSelectedRemoveItemOption(
                                        it, index = index, composeIndex = composeIndex
                                    )
                                }?.let { event ->
                                    viewModel.onEvent(
                                        event
                                    )
                                }
                            })
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
    checkedState: MutableState<Boolean>? = null,
    onItemsSelect: () -> Unit,
    cardIndex: Int? = null,
    composeIndex: ComposeIndex,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
    ) {
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
                    Text(
                        text = cardType,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                ProfileAddIcon(onClick = onItemsSelect)
            }
            Spacer(modifier = Modifier.height(spacing.small))
            com.google.accompanist.flowlayout.FlowRow(
                mainAxisSpacing = spacing.minSmall,
                modifier = Modifier.padding(start = spacing.medium),
            ) {
                cardList?.forEach {
                    RemoveChipOnCard(textOnChip = it.name, onClick = {
                        cardIndex?.let { index ->
                            ProfileEvent.SetSelectedRemoveItemOption(
                                item = it, index = index, composeIndex = composeIndex
                            )
                        }?.let { event -> viewModel.onEvent(event) }
                    })
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