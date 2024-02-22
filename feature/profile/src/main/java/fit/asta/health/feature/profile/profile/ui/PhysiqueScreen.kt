package fit.asta.health.feature.profile.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fit.asta.health.data.profile.remote.model.HeightUnit
import fit.asta.health.data.profile.remote.model.WeightUnit
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.RowToggleButtonGroup
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import fit.asta.health.resources.strings.R

@Composable
fun PhysiqueScreen(
    userProfileState: UserProfileState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        Spacer(modifier = Modifier)
        MeasurementSection(userProfileState)
        PageNavigationButtons(
            onPrevious = {
                userProfileState.currentPageIndex--
            },
            onNext = {
                userProfileState.currentPageIndex++
            }
        )
        Spacer(modifier = Modifier)
    }
}

@Composable
private fun MeasurementSection(
    userProfileState: UserProfileState
) {
    val focusManager = LocalFocusManager.current
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level3(
                        text = stringResource(id = R.string.weight),
                        color = AppTheme.colors.onTertiaryContainer
                    )
                    RowToggleButtonGroup(
                        selectedIndex = WeightUnit.indexOf(userProfileState.physiqueScreenState.weightUnit),
                        onButtonClick = { index ->
                            userProfileState.physiqueScreenState.weightUnit =
                                WeightUnit.entries[index].value
                        },
                        buttonTexts = WeightUnit.entries.map { it.title },
                        modifier = Modifier.size(width = 80.dp, height = 24.dp),
                        selectedColor = AppTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                AppOutlinedTextField(
                    value = userProfileState.physiqueScreenState.userWeight ?: "",
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    onValueChange = {
                        userProfileState.physiqueScreenState.setWeight(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = AppTheme.colors.onSurface
                    ),
                    appTextFieldType = AppTextFieldValidator(
                        AppTextFieldType.Custom(
                            isInvalidLogic = { _, _ ->
                                userProfileState.physiqueScreenState.userWeightErrorMessage != null
                            },
                            getErrorMessageLogic = { _, _ ->
                                userProfileState.physiqueScreenState.userWeightErrorMessage
                                    ?: ""
                            }
                        )
                    ),
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level3(
                        text = stringResource(id = R.string.height),
                        color = AppTheme.colors.onTertiaryContainer
                    )
                    RowToggleButtonGroup(
                        selectedIndex = HeightUnit.indexOf(userProfileState.physiqueScreenState.heightUnit),
                        buttonTexts = HeightUnit.entries.map { it.title },
                        onButtonClick = { index ->
                            userProfileState.physiqueScreenState.heightUnit =
                                HeightUnit.entries[index].value
                        },
                        modifier = Modifier.size(width = 80.dp, height = 24.dp),
                        selectedColor = AppTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                AppOutlinedTextField(
                    value = userProfileState.physiqueScreenState.userHeight ?: "",
                    onValueChange = {
                        userProfileState.physiqueScreenState.setHeight(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface),
                    appTextFieldType = AppTextFieldValidator(
                        AppTextFieldType.Custom(
                            isInvalidLogic = { _, _ ->
                                userProfileState.physiqueScreenState.userHeightErrorMessage != null
                            },
                            getErrorMessageLogic = { _, _ ->
                                userProfileState.physiqueScreenState.userHeightErrorMessage
                                    ?: ""
                            }
                        )
                    ),
                )
            }
        }
    }
}

