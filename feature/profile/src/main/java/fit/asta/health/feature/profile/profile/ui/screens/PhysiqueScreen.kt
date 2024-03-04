package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.HeightUnit
import fit.asta.health.data.profile.remote.model.WeightUnit
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetPhysique
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@Composable
fun PhysiqueScreen(
    userProfileState: UserProfileState
) {

    val heightBottomSheetState = rememberModalBottomSheetState()
    val heightBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    val weightBottomSheetState = rememberModalBottomSheetState()
    val weightBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        Spacer(modifier = Modifier)

        ClickableTextBox(
            label = "Height",
            value = if (
                userProfileState.physiqueScreenState.userHeight != null
                && userProfileState.physiqueScreenState.heightUnit != null
            ) {
                userProfileState.physiqueScreenState.userHeight + HeightUnit.getName(
                    userProfileState.physiqueScreenState.heightUnit!!
                )
            } else {
                "Select Height"
            },
            leadingIcon = Icons.Default.Height
        ) {
            userProfileState.openSheet(
                heightBottomSheetState,
                heightBottomSheetVisible
            )
        }

        ClickableTextBox(
            label = "Weight",
            value = if (
                userProfileState.physiqueScreenState.userWeight != null
                && userProfileState.physiqueScreenState.weightUnit != null
            ) {
                userProfileState.physiqueScreenState.userWeight + WeightUnit.getName(
                    userProfileState.physiqueScreenState.weightUnit!!
                )
            } else {
                "Select Weight"
            },
            leadingIcon = Icons.Default.MonitorWeight
        ) {
            userProfileState.openSheet(
                weightBottomSheetState,
                weightBottomSheetVisible
            )
        }

        PageNavigationButtons(
            onPrevious = {
                userProfileState.currentPageIndex--
            },
            onNext = {
                userProfileState.currentPageIndex++
            }
        )
        Spacer(modifier = Modifier)

        //Dialogs
        BottomSheetPhysique(
            isVisible = heightBottomSheetVisible.value,
            sheetState = heightBottomSheetState,
            label = "Height",
            text = userProfileState.physiqueScreenState.userHeight ?: "",
            units = HeightUnit.entries,
            selectedUnitIndex = HeightUnit.indexOf(userProfileState.physiqueScreenState.heightUnit)
                ?: -1,
            onDismissRequest = {
                userProfileState.closeSheet(
                    heightBottomSheetState,
                    heightBottomSheetVisible
                )
            },
            onSaveClick = { height: Float, unit: Int ->
                userProfileState.physiqueScreenState.saveHeight(height, unit)
                userProfileState.closeSheet(
                    heightBottomSheetState,
                    heightBottomSheetVisible
                )
            }
        )

        BottomSheetPhysique(
            isVisible = weightBottomSheetVisible.value,
            sheetState = weightBottomSheetState,
            label = "Weight",
            text = userProfileState.physiqueScreenState.userWeight ?: "",
            units = WeightUnit.entries,
            selectedUnitIndex = WeightUnit.indexOf(userProfileState.physiqueScreenState.weightUnit)
                ?: -1,
            onDismissRequest = {
                userProfileState.closeSheet(
                    weightBottomSheetState,
                    weightBottomSheetVisible
                )
            },
            onSaveClick = { weight: Float, unit: Int ->
                userProfileState.physiqueScreenState.saveWeight(weight, unit)
                userProfileState.closeSheet(
                    weightBottomSheetState,
                    weightBottomSheetVisible
                )
            }
        )

    }
}

