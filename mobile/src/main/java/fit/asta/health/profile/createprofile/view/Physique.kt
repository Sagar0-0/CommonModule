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

package fit.asta.health.profile.createprofile.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
import fit.asta.health.common.ui.components.PrimaryButton
import fit.asta.health.common.ui.components.functional.AppTextFieldValidate
import fit.asta.health.common.ui.components.functional.RowToggleButtonGroup
import fit.asta.health.common.ui.components.functional.TwoButtonLayout
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTextField
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.profile.MultiRadioBtnKeys
import fit.asta.health.profile.model.domain.ThreeRadioBtnSelections
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.view.ThreeTogglesGroups
import fit.asta.health.profile.view.TwoTogglesGroup
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.model.domain.InputWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhysiqueCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
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
        radioButtonSelections[MultiRadioBtnKeys.GENDER] as ThreeRadioBtnSelections?
    val selectedIsPregOptionDemo =
        radioButtonSelections[MultiRadioBtnKeys.ISPREG] as TwoRadioBtnSelections?
    val selectedIsOnPeriodOptionDemo =
        radioButtonSelections[MultiRadioBtnKeys.ISONPERIOD] as TwoRadioBtnSelections?

    val focusManager = LocalFocusManager.current

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(spacing.medium))
            AgeSection(userAge, userDOB, calendarState)
            Spacer(modifier = Modifier.height(spacing.medium))
            MeasurementSection(userWeight, focusManager, viewModel, userHeight)
            Spacer(modifier = Modifier.height(spacing.medium))
            GenderSection(
                selectedGenderOptionDemo,
                viewModel,
                selectedIsOnPeriodOptionDemo,
                selectedIsPregOptionDemo,
                pregnancyWeek,
                focusManager
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            TwoButtonLayout(onClickButton1 = eventPrevious, contentButton1 = {
                AppTexts.LabelLarge(
                    text = "Previous", color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }, onClickButton2 = eventNext, contentButton2 = {
                AppTexts.LabelLarge(
                    text = "Next", color = MaterialTheme.colorScheme.onPrimary
                )
            })
            Spacer(modifier = Modifier.height(spacing.medium))
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
                ThreeTogglesGroups(selectionTypeText = "Gender",
                    selectedOption = selectedGenderOptionDemo,
                    onStateChange = { state ->
                        viewModel.updateRadioButtonSelection(
                            MultiRadioBtnKeys.GENDER, state
                        )
                    })
                if (selectedGenderOptionDemo == ThreeRadioBtnSelections.Second) {
                    TwoTogglesGroup(selectionTypeText = "Are you having periods?",
                        selectedOption = selectedIsOnPeriodOptionDemo,
                        onStateChange = { state ->
                            viewModel.updateRadioButtonSelection(
                                MultiRadioBtnKeys.ISONPERIOD, state
                            )
                        })
                    TwoTogglesGroup(selectionTypeText = "Are you Pregnant",
                        selectedOption = selectedIsPregOptionDemo,
                        onStateChange = { state ->
                            viewModel.updateRadioButtonSelection(
                                radioButtonName = MultiRadioBtnKeys.ISPREG, selection = state
                            )
                        })
                    if (selectedIsPregOptionDemo == TwoRadioBtnSelections.First) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = spacing.medium),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                "Please Enter your Pregnancy Week",
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Spacer(modifier = Modifier.height(spacing.medium))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = spacing.medium)
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
                                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.onSurface)

                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(spacing.medium))
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
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppTexts.TitleMedium(
                        text = "Weight",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                    RowToggleButtonGroup(
                        buttonCount = 2,
                        onButtonClick = { index -> println(index) },
                        buttonTexts = arrayOf("kg", "lb"),
                        modifier = Modifier.size(width = 80.dp, height = 24.dp),
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(spacing.small))
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
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.onSurface)
                )
                if (userWeight.error !is UiString.Empty) {
                    Spacer(modifier = Modifier.height(spacing.minSmall))
                    AppTexts.BodyLarge(
                        text = userWeight.error.asString(), color = MaterialTheme.colorScheme.error
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppTexts.TitleMedium(
                        text = "Height",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                    )
                    RowToggleButtonGroup(
                        buttonCount = 2,
                        onButtonClick = { index -> println(index) },
                        buttonTexts = arrayOf("cm", "in"),
                        modifier = Modifier.size(width = 80.dp, height = 24.dp),
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(spacing.small))
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
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.onSurface)
                )
                if (userHeight.error !is UiString.Empty) {
                    Spacer(modifier = Modifier.height(spacing.minSmall))
                    AppTexts.BodyLarge(
                        text = userHeight.error.asString(), color = MaterialTheme.colorScheme.error
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
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.error
    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppTexts.TitleMedium(
            text = "Age", color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        if (userAge.value.isNotEmpty()) {
            AppTexts.BodyLarge(
                text = "${userAge.value} years old",
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }

    Spacer(modifier = Modifier.height(spacing.small))

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
                    MaterialTheme.colorScheme.onBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
            AppTexts.BodyMedium(
                text = userDOB.value.ifEmpty { "Date of Birth" },
                modifier = Modifier.padding(spacing.small),
                color = ageColorSelection
            )
        }
    }

    if (userAge.error !is UiString.Empty) {
        Spacer(modifier = Modifier.height(spacing.minSmall))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppTexts.BodyLarge(
                text = userAge.error.asString(), color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun CreateProfileButtons(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    text: String? = null,
    enableButton: Boolean = false,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
        PrimaryButton(
            text = "Previous",
            modifier = Modifier.fillMaxWidth(0.5f),
            event = eventPrevious,
            enableButton = true
        )
        if (text != null) {
            PrimaryButton(
                text = text,
                modifier = Modifier.fillMaxWidth(1f),
                event = eventNext,
                enableButton = enableButton
            )
        }
    }
}