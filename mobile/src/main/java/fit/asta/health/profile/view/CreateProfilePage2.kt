@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.view.components.AddIcon
import fit.asta.health.profile.view.components.RemoveChipOnCard
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SelectionCardCreateProfile(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardType: String,
    cardList: SnapshotStateList<HealthProperties>,
    radioButtonList: List<ButtonListTypes>,
    checkedState: MutableState<Boolean>? = null,
    onItemsSelect: () -> Unit,
    selectedOption: TwoToggleSelections?,
    onStateChange: (TwoToggleSelections) -> Unit,
    enabled: Boolean?,
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

                if (selectedOption == TwoToggleSelections.First) {
                    AddIcon(onClick = onItemsSelect)
                }

            }


            TwoTogglesGroup(
                selectionTypeText = null,
                selectedOption = selectedOption,
                onStateChange = onStateChange
            )


            if (selectedOption == TwoToggleSelections.First) {

                FlowRow(
                    mainAxisSpacing = spacing.minSmall,
                    modifier = Modifier.padding(start = spacing.medium),
                ) {
                    cardList.forEach {
                        RemoveChipOnCard(textOnChip = it.name,
                            checkedState = checkedState,
                            onClick = {
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
}

@Composable
fun OnlyChipSelectionCard(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardType: String,
    cardList: SnapshotStateList<HealthProperties>,
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
                AddIcon(onClick = onItemsSelect)
            }

            Spacer(modifier = Modifier.height(spacing.small))

            FlowRow(
                mainAxisSpacing = spacing.minSmall,
                modifier = Modifier.padding(start = spacing.medium),
            ) {
                cardList.forEach {
                    RemoveChipOnCard(textOnChip = it.name, checkedState = checkedState, onClick = {
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
fun SelectionOutlineButton(
    cardType: String,
    cardList: List<String>,
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
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = cardType,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )

            }

            Spacer(modifier = Modifier.height(spacing.small))

            LazyVerticalGrid(
                columns = GridCells.Fixed(cardList.size),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.small)
                    .height(customSize.extraLarge),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall),
            ) {
                cardList.forEach { text ->
                    item {
                        OutlineButton(textOnChip = text, modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(spacing.small))
        }
    }
}

@Composable
fun OutlineButton(textOnChip: String, modifier: Modifier = Modifier) {

    OutlinedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.outlinedButtonColors(disabledContentColor = Color(0xffE7EAED)),
        shape = RoundedCornerShape(customSize.extraLarge4),
        modifier = modifier
    ) {
        Text(
            text = textOnChip,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xDE000000)
        )
    }

}
