@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.feature.profile.create.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import fit.asta.health.common.utils.InputWrapper
import fit.asta.health.common.utils.UiString
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.functional.AppTextFieldValidate
import fit.asta.health.designsystem.components.functional.RowToggleButtonGroup
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.create.MultiRadioBtnKeys
import fit.asta.health.feature.profile.create.view.components.CreateProfileTwoButtonLayout
import fit.asta.health.feature.profile.create.vm.ProfileEvent
import fit.asta.health.feature.profile.create.vm.ThreeRadioBtnSelections
import fit.asta.health.feature.profile.create.vm.TwoRadioBtnSelections
import fit.asta.health.feature.profile.show.view.ThreeTogglesGroups
import fit.asta.health.feature.profile.show.view.TwoTogglesGroup
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.format.DateTimeFormatter
import java.util.Calendar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhysiqueCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit = {},
    eventNext: () -> Unit = {},
) {

    //Calendar
    val calendarInstance = Calendar.getInstance()
    val calendarState = rememberUseCaseState()

    //Basic Input

    val userWeight by viewModel.weight.collectAsStateWithLifecycle()
    val userDOB by viewModel.dob.collectAsStateWithLifecycle()
    val userAge by viewModel.age.collectAsStateWithLifecycle()
    val userHeight by viewModel.height.collectAsStateWithLifecycle()
    val pregnancyWeek by viewModel.pregnancyWeek.collectAsStateWithLifecycle()

    //Radio Buttons
    val radioButtonSelections by viewModel.radioButtonSelections.collectAsStateWithLifecycle()

    val selectedGenderOptionDemo =
        radioButtonSelections[MultiRadioBtnKeys.GENDER.key] as ThreeRadioBtnSelections?
    val selectedIsPregOptionDemo =
        radioButtonSelections[MultiRadioBtnKeys.ISPREG.key] as TwoRadioBtnSelections?
    val selectedIsOnPeriodOptionDemo =
        radioButtonSelections[MultiRadioBtnKeys.ISONPERIOD.key] as TwoRadioBtnSelections?

    val focusManager = LocalFocusManager.current

    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.medium)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                    AgeSection(userAge, userDOB, calendarState)
                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                    MeasurementSection(userWeight, focusManager, viewModel, userHeight)
                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                    GenderSection(
                        selectedGenderOptionDemo,
                        viewModel,
                        selectedIsOnPeriodOptionDemo,
                        selectedIsPregOptionDemo,
                        pregnancyWeek,
                        focusManager
                    )
                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))

                    CreateProfileTwoButtonLayout(eventPrevious, eventNext)

                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                }
            }
        }
    }

    CalendarSection(calendarState, viewModel, calendarInstance)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CalendarSection(
    calendarState: UseCaseState,
    viewModel: ProfileViewModel,
    calendarInstance: Calendar,
) {
    CalendarDialog(state = calendarState, selection = CalendarSelection.Date {
        viewModel.onEvent(
            ProfileEvent.OnUserDOBChange(
                dob = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
            )
        )
        viewModel.onEvent(ProfileEvent.OnUserAGEChange(age = "${calendarInstance.get(Calendar.YEAR) - it.year}"))
    }, config = CalendarConfig(monthSelection = true, yearSelection = true))
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun GenderSection(
    selectedGenderOptionDemo: ThreeRadioBtnSelections?,
    viewModel: ProfileViewModel,
    selectedIsOnPeriodOptionDemo: TwoRadioBtnSelections?,
    selectedIsPregOptionDemo: TwoRadioBtnSelections?,
    pregnancyWeek: InputWrapper,
    focusManager: FocusManager,
) {
    Row(Modifier.fillMaxWidth()) {
        AppCard {
            Column(
                Modifier.fillMaxWidth()
            ) {
                ThreeTogglesGroups(selectionTypeText = stringResource(id = R.string.gender),
                    selectedOption = selectedGenderOptionDemo,
                    onStateChange = { state ->
                        viewModel.updateRadioButtonSelection(
                            MultiRadioBtnKeys.GENDER.key, state
                        )
                    })
                if (selectedGenderOptionDemo == ThreeRadioBtnSelections.Second) {
                    TwoTogglesGroup(selectionTypeText = stringResource(R.string.periodTitle_profile_creation),
                        selectedOption = selectedIsOnPeriodOptionDemo,
                        onStateChange = { state ->
                            viewModel.updateRadioButtonSelection(
                                MultiRadioBtnKeys.ISONPERIOD.key, state
                            )
                        })
                    TwoTogglesGroup(selectionTypeText = stringResource(R.string.pregnantTitle_profile_creation),
                        selectedOption = selectedIsPregOptionDemo,
                        onStateChange = { state ->
                            viewModel.updateRadioButtonSelection(
                                radioButtonName = MultiRadioBtnKeys.ISPREG.key, selection = state
                            )
                        })
                    if (selectedIsPregOptionDemo == TwoRadioBtnSelections.First) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = AppTheme.spacing.medium),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TitleTexts.Level4(
                                text = stringResource(id = R.string.pregnancyWeekInput_profile_creation),
                                color = AppTheme.colors.onTertiaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppTheme.spacing.medium)
                        ) {
                            AppTextFieldValidate(
                                value = pregnancyWeek.value,
                                onValueChange = {
                                    viewModel.onEvent(ProfileEvent.OnUserPregWeekChange(week = it))
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                isError = pregnancyWeek.error !is UiString.Empty,
                                errorMessage = pregnancyWeek.error,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
        }
    }
}

@Composable
private fun MeasurementSection(
    userWeight: InputWrapper,
    focusManager: FocusManager,
    viewModel: ProfileViewModel,
    userHeight: InputWrapper,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.small)
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
                Spacer(modifier = Modifier.height(AppTheme.spacing.small))
                AppTextField(
                    value = userWeight.value,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Next
                        )
                    }),
                    onValueChange = {
                        viewModel.onEvent(
                            ProfileEvent.OnUserWeightChange(
                                weight = it
                            )
                        )
                    },
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                )
                if (userWeight.error !is UiString.Empty) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.minSmall))
                    BodyTexts.Level1(
                        text = userWeight.error.asString(), color = AppTheme.colors.error
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
                Spacer(modifier = Modifier.height(AppTheme.spacing.small))
                AppTextField(
                    value = userHeight.value,
                    onValueChange = {
                        viewModel.onEvent(
                            ProfileEvent.OnUserHeightChange(
                                height = it
                            )
                        )
                    },
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                )
                if (userHeight.error !is UiString.Empty) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.minSmall))
                    BodyTexts.Level1(
                        text = userHeight.error.asString(), color = AppTheme.colors.error
                    )
                }
            }
        }
    }
}

@Composable
private fun AgeSection(
    userAge: InputWrapper,
    userDOB: InputWrapper,
    calendarState: UseCaseState,
) {

    val ageColorSelection = if (userAge.error is UiString.Empty) {
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
        if (userAge.value.isNotEmpty()) {
            BodyTexts.Level1(
                text = "${userAge.value} years old",
                color = AppTheme.colors.onTertiaryContainer,
            )
        }
    }

    Spacer(modifier = Modifier.height(AppTheme.spacing.small))

    AppButtons.AppOutlinedButton(
        onClick = { calendarState.show() }, border = BorderStroke(
            width = 2.dp, color = ageColorSelection
        ), colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppDefaultIcon(
                imageVector = Icons.Rounded.EditCalendar,
                contentDescription = "Calendar Icon",
                tint = if (userAge.error is UiString.Empty) {
                    AppTheme.colors.onBackground
                } else {
                    AppTheme.colors.error
                }
            )
            BodyTexts.Level2(
                text = userDOB.value.ifEmpty { stringResource(R.string.date_of_birth) },
                modifier = Modifier.padding(AppTheme.spacing.small),
                color = ageColorSelection
            )
        }
    }

    if (userAge.error !is UiString.Empty) {
        Spacer(modifier = Modifier.height(AppTheme.spacing.minSmall))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyTexts.Level1(text = userAge.error.asString(), color = AppTheme.colors.error)
        }
    }
}