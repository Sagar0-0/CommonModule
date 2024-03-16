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
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.BodyTypes
import fit.asta.health.data.profile.remote.model.HeightUnit
import fit.asta.health.data.profile.remote.model.WeightUnit
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.appRememberModalBottomSheetState
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetPhysique
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetRadioList
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@Composable
fun PhysiqueScreen(
    userProfileState: UserProfileState
) {
    val heightBottomSheetState = appRememberModalBottomSheetState()
    val heightBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    val weightBottomSheetState = appRememberModalBottomSheetState()
    val bodyTypeBottomSheetState = appRememberModalBottomSheetState()
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
                userProfileState.physiqueScreenState.userHeight!!.toString() + HeightUnit.getName(
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
                userProfileState.physiqueScreenState.userWeight!!.toString() + WeightUnit.getName(
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

        ClickableTextBox(
            label = "BMI",
            value = userProfileState.physiqueScreenState.bmi?.toString() ?: "Not Available",
            leadingIcon = Icons.Default.Hub,
            trailingIcon = null
        )

        ClickableTextBox(
            label = "Body Type",
            value = if (userProfileState.physiqueScreenState.bodyType != null) {
                BodyTypes.getBodyType(userProfileState.physiqueScreenState.bodyType!!).name
            } else "Select Body Type",
            leadingIcon = Icons.Default.Person
        ) {
            userProfileState.openSheet(
                bodyTypeBottomSheetState,
                userProfileState.physiqueScreenState.bodyTypeBottomSheetVisible
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
            isValid = { height: Double?, unit: Int? ->
                height != null && unit != null && if (unit == HeightUnit.INCH.value) height > 3.5 else height > 8.8
            },
            label = "Height",
            text = userProfileState.physiqueScreenState.userHeight?.toString() ?: "",
            units = HeightUnit.entries,
            selectedUnitIndex = HeightUnit.indexOf(userProfileState.physiqueScreenState.heightUnit),
            onDismissRequest = {
                userProfileState.closeSheet(
                    heightBottomSheetState,
                    heightBottomSheetVisible
                )
            },
            onSaveClick = { height: Double?, unit: Int? ->
                if (height != null && unit != null) {
                    userProfileState.physiqueScreenState.saveHeight(height, unit)
                    userProfileState.closeSheet(
                        heightBottomSheetState,
                        heightBottomSheetVisible
                    )
                }
            }
        )

        BottomSheetPhysique(
            isVisible = weightBottomSheetVisible.value,
            sheetState = weightBottomSheetState,
            isValid = { weight: Double?, unit: Int? ->
                weight != null && unit != null && if (unit == WeightUnit.KG.value) weight > 30 else weight > 66
            },
            label = "Weight",
            text = userProfileState.physiqueScreenState.userWeight?.toString() ?: "",
            units = WeightUnit.entries,
            selectedUnitIndex = WeightUnit.indexOf(userProfileState.physiqueScreenState.weightUnit),
            onDismissRequest = {
                userProfileState.closeSheet(
                    weightBottomSheetState,
                    weightBottomSheetVisible
                )
            },
            onSaveClick = { weight: Double?, unit: Int? ->
                if (weight != null && unit != null) {
                    userProfileState.physiqueScreenState.saveWeight(weight ?: 0.0, unit)
                    userProfileState.closeSheet(
                        weightBottomSheetState,
                        weightBottomSheetVisible
                    )
                }
            }
        )

        BottomSheetRadioList(
            isVisible = userProfileState.physiqueScreenState.bodyTypeBottomSheetVisible.value,
            sheetState = bodyTypeBottomSheetState,
            isValid = {
                it != null
            },
            selectedIndex = BodyTypes.indexOf(userProfileState.physiqueScreenState.bodyType),
            list = BodyTypes.entries.map { it.name },
            onDismissRequest = {
                userProfileState.closeSheet(
                    bodyTypeBottomSheetState,
                    userProfileState.physiqueScreenState.bodyTypeBottomSheetVisible
                )
            }
        ) {
            if (it != null) {
                userProfileState.physiqueScreenState.saveBodyType(it)
            }
        }

    }
}

