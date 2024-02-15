@file:OptIn(
    ExperimentalMaterial3Api::class
)

package fit.asta.health.feature.profile.create.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.BooleanIntTypes
import fit.asta.health.data.profile.remote.model.GenderTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.RowToggleButtonGroup
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.profile.ui.UserProfileState
import fit.asta.health.feature.profile.profile.utils.ThreeTogglesGroups
import fit.asta.health.feature.profile.profile.utils.TwoTogglesGroup
import fit.asta.health.resources.strings.R
import java.time.format.DateTimeFormatter
import java.util.Calendar


@Composable
fun PhysiqueCreateScreen(
    userProfileState: UserProfileState
) {
    val calendarUseCaseState = rememberUseCaseState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        Spacer(modifier = Modifier)
        AgeSection(userProfileState, calendarUseCaseState)
        MeasurementSection(userProfileState)
        GenderSection(userProfileState)
        CreateProfileTwoButtonLayout(userProfileState)
        Spacer(modifier = Modifier)
    }

    CalendarSection(userProfileState, calendarUseCaseState)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CalendarSection(
    userProfileState: UserProfileState,
    useCaseState: UseCaseState
) {
    CalendarDialog(
        state = useCaseState, selection = CalendarSelection.Date {
            userProfileState.userDob =
                it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
            userProfileState.userAge =
                (userProfileState.calendar.get(Calendar.YEAR) - it.year)
        },
        config = CalendarConfig(monthSelection = true, yearSelection = true)
    )
}

@Composable
private fun GenderSection(
    userProfileState: UserProfileState,
) {
    val focusManager = LocalFocusManager.current
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            ThreeTogglesGroups(
                title = R.string.gender.toStringFromResId(),
                selectedOption = userProfileState.userGender,
            ) {
                userProfileState.userGender = it
            }

            AnimatedVisibility(userProfileState.userGender == GenderTypes.FEMALE.gender) {
                Column {
                    TwoTogglesGroup(
                        selectionTypeText = stringResource(R.string.periodTitle_profile_creation),
                        selectedOption = userProfileState.onPeriod,
                        onStateChange = { state ->
                            userProfileState.onPeriod = state
                        }
                    )
                    TwoTogglesGroup(
                        selectionTypeText = stringResource(R.string.pregnantTitle_profile_creation),
                        selectedOption = userProfileState.isPregnant,
                        onStateChange = { state ->
                            userProfileState.isPregnant = state
                        }
                    )
                    AnimatedVisibility(userProfileState.isPregnant == BooleanIntTypes.YES.value) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = AppTheme.spacing.level2),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TitleTexts.Level4(
                                text = stringResource(id = R.string.pregnancyWeekInput_profile_creation),
                                color = AppTheme.colors.onTertiaryContainer
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
                                value = userProfileState.userPregnancyWeek ?: "",
                                onValueChange = {
                                    userProfileState.userPregnancyWeek = it
                                },
                                appTextFieldType = AppTextFieldValidator(
                                    AppTextFieldType.Custom(
                                        isInvalidLogic = { _, _ ->
                                            userProfileState.userPregnancyWeekErrorMessage != null
                                        },
                                        getErrorMessageLogic = { _, _ ->
                                            userProfileState.userPregnancyWeekErrorMessage ?: ""
                                        }
                                    )
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                            )
                        }
                    }
                }
            }
        }
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
                        buttonCount = 2,
                        onButtonClick = { index -> println(index) },
                        buttonTexts = arrayOf("kg", "lb"),
                        modifier = Modifier.size(width = 80.dp, height = 24.dp),
                        selectedColor = AppTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                AppOutlinedTextField(
                    value = userProfileState.userWeight,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    onValueChange = {
                        userProfileState.userWeight = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = AppTheme.colors.onSurface
                    )
                )
                userProfileState.userWeightErrorMessage?.let {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                    BodyTexts.Level1(
                        text = userProfileState.userWeightErrorMessage!!,
                        color = AppTheme.colors.error
                    )
                }
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
                        buttonCount = 2,
                        onButtonClick = { index -> println(index) },
                        buttonTexts = arrayOf("cm", "in"),
                        modifier = Modifier.size(width = 80.dp, height = 24.dp),
                        selectedColor = AppTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                AppOutlinedTextField(
                    value = userProfileState.userHeight,
                    onValueChange = {
                        userProfileState.userHeight = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                )
                userProfileState.userHeightErrorMessage?.let {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                    BodyTexts.Level1(
                        text = userProfileState.userHeightErrorMessage!!,
                        color = AppTheme.colors.error
                    )
                }
            }
        }
    }
}

@Composable
private fun AgeSection(
    userProfileState: UserProfileState,
    calendarUseCaseState: UseCaseState
) {
    val ageColorSelection = if (userProfileState.userAgeErrorMessage == null) {
        AppTheme.colors.onSurface
    } else {
        AppTheme.colors.error
    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TitleTexts.Level3(
            text = stringResource(id = R.string.age), color = AppTheme.colors.onTertiaryContainer
        )
        BodyTexts.Level1(
            text = "${userProfileState.userAge} years old",
            color = AppTheme.colors.onTertiaryContainer,
        )
    }

    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))


    AppOutlinedButton(
        onClick = { calendarUseCaseState.show() },
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
        border = BorderStroke(
            width = AppTheme.spacing.level0, color = ageColorSelection
        )
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppIcon(
                imageVector = Icons.Rounded.EditCalendar,
                contentDescription = "Calendar Icon",
                tint = if (userProfileState.userAgeErrorMessage == null) {
                    AppTheme.colors.onBackground
                } else {
                    AppTheme.colors.error
                }
            )
            BodyTexts.Level2(
                text = userProfileState.userDob.ifEmpty { stringResource(R.string.select_date_of_birth) },
                modifier = Modifier.padding(AppTheme.spacing.level1),
                color = ageColorSelection
            )
        }
    }

    userProfileState.userAgeErrorMessage?.let {
        Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyTexts.Level1(text = it, color = AppTheme.colors.error)
        }
    }
}