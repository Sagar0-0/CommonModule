@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.feature.profile.show.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.create.vm.ComposeIndex
import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
import fit.asta.health.feature.profile.show.view.components.DisabledChipForList
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class ButtonListTypes(
    val buttonType: String,
)

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
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = CenterVertically
                    ) {
                        TitleTexts.Level3(text = cardType)
                    }
                    if (selectedOption == TwoRadioBtnSelections.First) {
                        ProfileAddIcon(onClick = onItemsSelect)
                    }
                }
//                TwoTogglesGroup(
//                    selectionTypeText = null,
//                    selectedOption = selectedOption,
//                    onStateChange = onStateChange
//                )
                if (selectedOption == TwoRadioBtnSelections.First) {
                    FlowRow(
                        mainAxisSpacing = AppTheme.spacing.level0,
                        modifier = Modifier.padding(start = AppTheme.spacing.level2),
                    ) {
                        cardList?.forEach {
                            DisabledChipForList(textOnChip = it.name)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
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
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = CenterVertically
                ) {
                    TitleTexts.Level3(text = cardType)
                }
                ProfileAddIcon(onClick = onItemsSelect)
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            FlowRow(
                mainAxisSpacing = AppTheme.spacing.level0,
                modifier = Modifier.padding(start = AppTheme.spacing.level2),
            ) {
                cardList?.forEach {
                    DisabledChipForList(textOnChip = it.name)
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        }
    }
}


@Composable
private fun ProfileAddIcon(
    onClick: () -> Unit,
) {
    AppIconButton(
        imageVector = Icons.Rounded.AddCircle, iconTint = AppTheme.colors.primary, onClick = onClick
    )
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
            TitleTexts.Level3(
                text = viewModel.validateDataList(cardList, listName).asString(),
                color = AppTheme.colors.error
            )
        }
    }
}
