package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.feature.profile.utils.TwoTogglesGroup
import fit.asta.health.resources.strings.R

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

    val (updatedGender, onGenderChange) = rememberSaveable(isVisible) {
        mutableStateOf(gender)
    }
    val (pregnantStatus, onPregnantChange) = rememberSaveable(isVisible) {
        mutableStateOf(isPregnant)
    }
    val (periodStatus, onPeriodChange) = rememberSaveable(isVisible) {
        mutableStateOf(onPeriod)
    }

    var pregWeekTextFieldValue by remember(isVisible) {
        mutableStateOf(TextFieldValue(text = pregnancyWeek?.toString() ?: ""))
    }

    val isPeriodVisible = rememberSaveable(isVisible, updatedGender) {
        updatedGender == GenderTypes.FEMALE.gender
    }

    val isPregnantVisible = rememberSaveable(isVisible, isPeriodVisible, periodStatus) {
        isPeriodVisible && periodStatus == BooleanIntTypes.NO.value
    }

    val isPregnantWeekFieldVisible =
        rememberSaveable(isVisible, isPregnantVisible, pregnantStatus) {
            isPregnantVisible && pregnantStatus == BooleanIntTypes.YES.value
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
                onStateChange = {
                    if (it != GenderTypes.FEMALE.gender) {
                        onPregnantChange(null)
                        onPeriodChange(null)
                        pregWeekTextFieldValue = TextFieldValue("")
                    }
                    onGenderChange(it)
                }
            )

            AnimatedVisibility(visible = isPeriodVisible) {
                TwoTogglesGroup(
                    selectionTypeText = stringResource(R.string.periodTitle_profile_creation),
                    selectedOption = periodStatus ?: -1,
                    onStateChange = {
                        if (it != BooleanIntTypes.YES.value) {
                            onPregnantChange(null)
                            pregWeekTextFieldValue = TextFieldValue("")
                        }
                        onPeriodChange(it)
                    }
                )
            }

            AnimatedVisibility(visible = isPregnantVisible) {
                TwoTogglesGroup(
                    selectionTypeText = stringResource(R.string.pregnantTitle_profile_creation),
                    selectedOption = pregnantStatus ?: -1,
                    onStateChange = {
                        if (it != BooleanIntTypes.NO.value) {
                            pregWeekTextFieldValue = TextFieldValue("")
                        }
                        onPregnantChange(it)
                    }
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
                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = pregWeekTextFieldValue,
                    label = stringResource(id = R.string.pregnancyWeekInput_profile_creation),
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
                    //  colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                )
            }
            BottomSheetSaveButtons(
                onSave = {
                    onSaveClick(
                        updatedGender,
                        pregnantStatus,
                        periodStatus,
                        pregWeekTextFieldValue.text.toIntOrNull()
                    )
                }
            ) {
                onDismissRequest()
            }

        }
    }

}
