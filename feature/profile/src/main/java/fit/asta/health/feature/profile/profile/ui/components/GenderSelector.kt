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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.BooleanIntTypes
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.GenderTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
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
    onSaveClick: (Gender?, BooleanInt?, BooleanInt?, Int?) -> Unit
) {

    val (updatedGender, onGenderChange) = rememberSaveable {
        mutableStateOf(gender)
    }
    val (updatedIsPregnant, onPregnantChange) = rememberSaveable {
        mutableStateOf(isPregnant)
    }
    val (updatedPeriod, onPeriodChange) = rememberSaveable {
        mutableStateOf(onPeriod)
    }

    var updatedPregWeek by rememberSaveable {
        mutableStateOf(pregnancyWeek)
    }

    AppModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetVisible = isVisible,
        sheetState = sheetState,
        dragHandle = null,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            GenderSelector(
                title = R.string.gender.toStringFromResId(),
                selectedOption = updatedGender ?: -1,
                onStateChange = onGenderChange
            )

            AnimatedVisibility(visible = updatedGender == GenderTypes.FEMALE.gender) {
                Column {
                    TwoTogglesGroup(
                        selectionTypeText = stringResource(R.string.periodTitle_profile_creation),
                        selectedOption = updatedPeriod ?: -1,
                        onStateChange = onPeriodChange
                    )
                    AnimatedVisibility(visible = updatedPeriod == BooleanIntTypes.NO.value) {
                        TwoTogglesGroup(
                            selectionTypeText = stringResource(R.string.pregnantTitle_profile_creation),
                            selectedOption = updatedIsPregnant ?: -1,
                            onStateChange = onPregnantChange
                        )
                        AnimatedVisibility(updatedIsPregnant == BooleanIntTypes.YES.value) {
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
                                AppTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = "" + updatedPregWeek,
                                    onValueChange = {
                                        updatedPregWeek = it.toIntOrNull()
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
                    }

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
                    onSaveClick(updatedGender, updatedPeriod, updatedIsPregnant, updatedPregWeek)
                }
            }
        }
    }

}

@Composable
fun GenderSelector(
    title: String,
    selectedOption: Gender,
    onStateChange: (Gender) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2)
        ) {
            TitleTexts.Level3(
                text = title, color = AppTheme.colors.onTertiaryContainer
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            GenderTypes.entries.forEach { option ->
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)
                ) {
                    AppRadioButton(
                        selected = selectedOption == option.gender
                    ) {
                        onStateChange(option.gender)
                    }
                    CaptionTexts.Level3(
                        text = option.title,
                        color = AppTheme.colors.onPrimaryContainer
                    )
                }
            }
        }
    }
}