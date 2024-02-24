package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.BooleanIntTypes
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.GenderTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.utils.TwoTogglesGroup
import fit.asta.health.resources.strings.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetGenderSelector(
    isVisible: Boolean,
    sheetState: SheetState,
    gender: Gender?,
    isPregnant: BooleanInt?,
    onPeriod: BooleanInt?,
    pregnancyWeek: Int?,
    pregWeekErrorMessage: String? = null,
    onDismissRequest: () -> Unit,
    onSaveClick: (gender: Gender?, isPregnant: BooleanInt?, onPeriod: BooleanInt?, pregnancyWeek: Int?) -> Unit
) {

    val (updatedGender, onGenderChange) = rememberSaveable {
        mutableStateOf(gender)
    }
    val (pregnantStatus, onPregnantChange) = rememberSaveable {
        mutableStateOf(isPregnant)
    }
    val (periodStatus, onPeriodChange) = rememberSaveable {
        mutableStateOf(onPeriod)
    }

    var pregWeekTextFieldValue by remember {
        mutableStateOf(TextFieldValue(text = pregnancyWeek?.toString() ?: ""))
    }

    val isPeriodVisible by remember {
        derivedStateOf {
            updatedGender == GenderTypes.FEMALE.gender
        }
    }

    val isPregnantVisible by remember {
        derivedStateOf {
            isPeriodVisible && periodStatus == BooleanIntTypes.NO.value
        }
    }

    val isPregnantWeekFieldVisible by remember {
        derivedStateOf {
            isPregnantVisible && pregnantStatus == BooleanIntTypes.YES.value
        }
    }

    AppModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetVisible = isVisible,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            GenderSelector(
                selectedOption = updatedGender ?: -1,
                onStateChange = onGenderChange
            )

            AnimatedVisibility(visible = isPeriodVisible) {
                TwoTogglesGroup(
                    selectionTypeText = stringResource(R.string.periodTitle_profile_creation),
                    selectedOption = periodStatus ?: -1,
                    onStateChange = onPeriodChange
                )
            }

            AnimatedVisibility(visible = isPregnantVisible) {
                TwoTogglesGroup(
                    selectionTypeText = stringResource(R.string.pregnantTitle_profile_creation),
                    selectedOption = pregnantStatus ?: -1,
                    onStateChange = onPregnantChange
                )
            }

            AnimatedVisibility(
                visible = isPregnantWeekFieldVisible
            ) {
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(isPregnantWeekFieldVisible) {
                    if (isPregnantWeekFieldVisible) {
                        focusRequester.requestFocus()
                        //Move cursor at the end
                        pregWeekTextFieldValue = pregWeekTextFieldValue.copy(
                            selection = TextRange(pregWeekTextFieldValue.text.length)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = AppTheme.spacing.level2),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TitleTexts.Level4(
                        text = stringResource(id = R.string.pregnancyWeekInput_profile_creation),
                        color = AppTheme.colors.onTertiaryContainer,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2)
                ) {
                    AppOutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = pregWeekTextFieldValue,
                        onValueChange = {
                            pregWeekTextFieldValue = it
                        },
                        appTextFieldType = AppTextFieldValidator(
                            AppTextFieldType.Custom(
                                isInvalidLogic = { _, _ ->
                                    pregWeekErrorMessage != null
                                },
                                getErrorMessageLogic = { _, _ ->
                                    pregWeekErrorMessage
                                        ?: ""
                                }
                            )
                        ),
                        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                AppTextButton(textToShow = "Cancel") {
                    onDismissRequest()
                }
                AppTextButton(textToShow = "Save") {
                    onSaveClick(
                        updatedGender,
                        pregnantStatus,
                        periodStatus,
                        pregWeekTextFieldValue.text?.toIntOrNull()
                    )
                }
            }
        }
    }

}