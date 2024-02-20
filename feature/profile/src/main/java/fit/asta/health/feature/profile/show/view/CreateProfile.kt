//@file:OptIn(
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class,
//    ExperimentalCoroutinesApi::class
//)
//
package fit.asta.health.feature.profile.show.view
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.AddCircle
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.ui.Alignment.Companion.CenterVertically
//import androidx.compose.ui.Modifier
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.google.accompanist.flowlayout.FlowRow
//import fit.asta.health.data.profile.remote.model.UserProperties
//import fit.asta.health.designsystem.AppTheme
//import fit.asta.health.designsystem.molecular.button.AppIconButton
//import fit.asta.health.designsystem.molecular.cards.AppCard
//import fit.asta.health.designsystem.molecular.texts.TitleTexts
//import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
//import fit.asta.health.feature.profile.profile.ProfileViewModel
//import fit.asta.health.feature.profile.show.view.components.DisabledChipForList
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//
//data class ButtonListTypes(
//    val buttonType: String,
//)
//
//@OptIn(ExperimentalCoroutinesApi::class)
//@Composable
//fun SelectionCardCreateProfile(
//    cardType: String,
//    cardList: SnapshotStateList<UserProperties>?,
//    onItemsSelect: () -> Unit,
//    selectedOption: TwoRadioBtnSelections?,
//    listName: String = "",
//) {
//    Column(Modifier.fillMaxWidth()) {
//        AppCard(modifier = Modifier.fillMaxWidth()) {
//            Column(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = AppTheme.spacing.level2),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = CenterVertically
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = CenterVertically
//                    ) {
//                        TitleTexts.Level3(text = cardType)
//                    }
//                    if (selectedOption == TwoRadioBtnSelections.First) {
//                        ProfileAddIcon(onClick = onItemsSelect)
//                    }
//                }
//                if (selectedOption == TwoRadioBtnSelections.First) {
//                    FlowRow(
//                        mainAxisSpacing = AppTheme.spacing.level0,
//                        modifier = Modifier.padding(start = AppTheme.spacing.level2),
//                    ) {
//                        cardList?.forEach {
//                            DisabledChipForList(textOnChip = it.name)
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
//            }
//        }
//        if (cardList != null) {
//            ValidateListError(
//                selectedOption = selectedOption, cardList = cardList, listName = listName
//            )
//        }
//    }
//}
//
//
//@Composable
//fun OnlyChipSelectionCard(
//    cardType: String,
//    cardList: SnapshotStateList<UserProperties>?,
//    onItemsSelect: () -> Unit,
//) {
//    AppCard(modifier = Modifier.fillMaxWidth()) {
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
//            Row(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = AppTheme.spacing.level2),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = CenterVertically
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.Center, verticalAlignment = CenterVertically
//                ) {
//                    TitleTexts.Level3(text = cardType)
//                }
//                ProfileAddIcon(onClick = onItemsSelect)
//            }
//            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
//            FlowRow(
//                mainAxisSpacing = AppTheme.spacing.level0,
//                modifier = Modifier.padding(start = AppTheme.spacing.level2),
//            ) {
//                cardList?.forEach {
//                    DisabledChipForList(textOnChip = it.name)
//                }
//            }
//            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
//        }
//    }
//}
//
//
//@Composable
//private fun ProfileAddIcon(
//    onClick: () -> Unit,
//) {
//    AppIconButton(
//        imageVector = Icons.Rounded.AddCircle, iconTint = AppTheme.colors.primary, onClick = onClick
//    )
//}
//
//
//@Composable
//private fun ValidateListError(
//    viewModel: ProfileViewModel = hiltViewModel(),
//    selectedOption: TwoRadioBtnSelections?,
//    cardList: SnapshotStateList<UserProperties>,
//    listName: String,
//) {
//    Row(Modifier.fillMaxWidth()) {
//        if (selectedOption is TwoRadioBtnSelections.First && cardList.isEmpty()) {
//            TitleTexts.Level3(
//                text = viewModel.validateDataList(cardList, listName).asString(),
//                color = AppTheme.colors.error
//            )
//        }
//    }
//}
