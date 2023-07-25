@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.createprofile.view.components.RowToggleButtonGroup
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.TwoTogglesGroup
import fit.asta.health.profile.view.components.ProfileAddIcon
import fit.asta.health.profile.view.components.RemoveChipOnCard
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.components.ValidateNumberField
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun InjuriesLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardType: String,
    cardType2: String,
    cardList: SnapshotStateList<HealthProperties>?,
    cardList2: SnapshotStateList<HealthProperties>?,
    radioButtonList: List<ButtonListTypes>,
    checkedState: MutableState<Boolean>? = null,
    checkedState2: MutableState<Boolean>? = null,
    onItemsSelect: () -> Unit,
    onItemsSelect2: () -> Unit,
    selectedOption: TwoRadioBtnSelections?,
    onStateChange: (TwoRadioBtnSelections) -> Unit,
    cardIndex1: Int,
    cardIndex2: Int,
    time: String,
    listName: String,
    listName2: String,
) {

    val focusManager = LocalFocusManager.current

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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
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

                    FlowRow(
                        mainAxisSpacing = spacing.minSmall,
                        modifier = Modifier.padding(start = spacing.medium),
                    ) {
                        cardList?.forEach {
                            RemoveChipOnCard(
                                textOnChip = it.name,
                                checkedState = checkedState,
                                onClick = {
                                    viewModel.onEvent(
                                        ProfileEvent.SetSelectedRemoveItemOption(
                                            item = it,
                                            index = cardIndex1,
                                            composeIndex = ComposeIndex.First
                                        )
                                    )
                                })
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            "Please Enter when you were Injured",
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(spacing.medium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(spacing.small)
                    ) {

                        Box(Modifier.fillMaxWidth(0.5f)) {
                            ValidateNumberField(
                                value = time,
                                onValueChange = {
                                    viewModel.onEvent(
                                        ProfileEvent.OnUserInjuryTimeChange(
                                            time = it
                                        )
                                    )
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                            )
                        }

                        RowToggleButtonGroup(
                            buttonCount = 2,
                            onButtonClick = { index -> println(index) },
                            buttonTexts = arrayOf("Month", "Year"),
                            modifier = Modifier.size(width = 130.dp, height = 24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(spacing.small))

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.medium),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = cardType2,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        ProfileAddIcon(onClick = onItemsSelect2)
                    }

                    Spacer(modifier = Modifier.height(spacing.small))

                    FlowRow(
                        mainAxisSpacing = spacing.minSmall,
                        modifier = Modifier.padding(start = spacing.medium),
                    ) {
                        cardList2?.forEach {
                            RemoveChipOnCard(
                                textOnChip = it.name,
                                checkedState = checkedState2,
                                onClick = {
                                    viewModel.onEvent(
                                        ProfileEvent.SetSelectedRemoveItemOption(
                                            item = it,
                                            index = cardIndex2,
                                            composeIndex = ComposeIndex.First
                                        )
                                    )
                                })
                        }
                    }

                    Spacer(modifier = Modifier.height(spacing.small))

                }

            }
        }

        if (cardList != null) {
            ValidateListError(selectedOption = selectedOption, cardList = cardList, listName = listName)
        }

        Spacer(modifier = Modifier.height(spacing.minSmall))

        if (cardList2 != null) {
            ValidateListError(
                selectedOption = selectedOption, cardList = cardList2, listName = listName2
            )
        }

    }


}

@Composable
fun ValidateListError(
    viewModel: ProfileViewModel = hiltViewModel(),
    selectedOption: TwoRadioBtnSelections?,
    cardList: SnapshotStateList<HealthProperties>,
    listName: String,
) {
    Row(Modifier.fillMaxWidth()) {
        if (selectedOption is TwoRadioBtnSelections.First && cardList.isEmpty()) {
            Text(
                text = viewModel.validateDataList(
                    list = cardList, listName = listName
                ).asString(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}