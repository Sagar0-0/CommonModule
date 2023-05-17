@file:OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalCoroutinesApi::class
)

package fit.asta.health.profile.createprofile.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import fit.asta.health.common.ui.components.PrimaryButton
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.profile.createprofile.view.components.BodyTypeLayout
import fit.asta.health.profile.createprofile.view.components.RowToggleButtonGroup
import fit.asta.health.profile.model.domain.ThreeToggleSelections
import fit.asta.health.profile.model.domain.TwoToggleSelections
import fit.asta.health.profile.view.ThreeTogglesGroups
import fit.asta.health.profile.view.TwoTogglesGroup
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.testimonials.view.components.ValidateNumberField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhysiqueCreateScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    //Calendar
    val c = Calendar.getInstance()
    val calendarState = rememberUseCaseState()

    //Basic Input
    val userWeight by viewModel.weight.collectAsStateWithLifecycle()
    val userDOB by viewModel.dob.collectAsStateWithLifecycle()
    val userAge by viewModel.age.collectAsStateWithLifecycle()
    val userHeight by viewModel.height.collectAsStateWithLifecycle()
    val pregnancyWeek by viewModel.pregnancyWeek.collectAsStateWithLifecycle()

    //Selection
    val selectedIsPregnantOption by viewModel.selectedIsPregnant.collectAsStateWithLifecycle()
    val selectedOnPeriodOption by viewModel.selectedOnPeriod.collectAsStateWithLifecycle()
    val selectedGenderOption by viewModel.selectedGender.collectAsStateWithLifecycle()


    //Inputs Validity
    val areBasicInputsValid by viewModel.areBasicPhysiqueInputsValid.collectAsStateWithLifecycle()
    val areFemaleInputsNull by viewModel.areFemaleInputNull.collectAsStateWithLifecycle()
    val arePregInputsValid by viewModel.arePregnancyInputValid.collectAsStateWithLifecycle()
    val phyValid by viewModel.phyInputsValid.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    if (selectedGenderOption == ThreeToggleSelections.Second) {
        viewModel.onEvent(ProfileEvent.IsPhyValid(areFemaleInputsNull))
        if (selectedIsPregnantOption == TwoToggleSelections.First) {
            viewModel.onEvent(ProfileEvent.IsPhyValid(arePregInputsValid))
        }
    } else {
        viewModel.onEvent(ProfileEvent.IsPhyValid(areBasicInputsValid))
    }

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

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Age",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )

                if (userAge.value.isNotEmpty()) {
                    Text(
                        text = userAge.value,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

            }

            Spacer(modifier = Modifier.height(spacing.small))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp, color = if (userAge.error is UiString.Empty) {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        ), shape = MaterialTheme.shapes.medium
                    )
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = userDOB.value.ifEmpty {
                            "Date of Birth"
                        }, modifier = Modifier.padding(spacing.small))
                        IconButton(onClick = { calendarState.show() }) {
                            Icon(
                                imageVector = Icons.Filled.EditCalendar,
                                contentDescription = null,
                                tint = if (userAge.error is UiString.Empty) {
                                    MaterialTheme.colorScheme.onBackground
                                } else {
                                    MaterialTheme.colorScheme.error
                                }
                            )
                        }
                    }

                }
            }


            if (userAge.error !is UiString.Empty) {

                Spacer(modifier = Modifier.height(spacing.minSmall))

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userAge.error.asString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Weight",
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.titleSmall
                            )
                            RowToggleButtonGroup(
                                buttonCount = 2,
                                onButtonClick = { index -> println(index) },
                                buttonTexts = arrayOf("kg", "lb"),
                                modifier = Modifier.size(width = 80.dp, height = 24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(spacing.small))
                        ValidateNumberField(
                            value = userWeight.value,
                            onValueChange = {
                                viewModel.onEvent(ProfileEvent.OnUserWeightChange(weight = it))
                            },
                            singleLine = true,
                            modifier = Modifier.height(48.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(
                                    FocusDirection.Next
                                )
                            })
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Height",
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.titleSmall
                            )
                            RowToggleButtonGroup(
                                buttonCount = 2,
                                onButtonClick = { index -> println(index) },
                                buttonTexts = arrayOf("cm", "in"),
                                modifier = Modifier.size(width = 80.dp, height = 24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(spacing.small))
                        ValidateNumberField(
                            value = userHeight.value,
                            onValueChange = {
                                viewModel.onEvent(ProfileEvent.OnUserHeightChange(height = it))
                            },
                            singleLine = true,
                            modifier = Modifier.height(48.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                focusManager.clearFocus()
                            })
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (userWeight.error !is UiString.Empty) {
                        Spacer(modifier = Modifier.height(spacing.minSmall))
                        Text(
                            text = userWeight.error.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (userHeight.error !is UiString.Empty) {
                        Spacer(modifier = Modifier.height(spacing.minSmall))
                        Text(
                            text = userHeight.error.asString(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))


            Row(Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
                ) {

                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        ThreeTogglesGroups(selectionTypeText = "Gender",
                            selectedOption = selectedGenderOption,
                            onStateChange = { state ->
                                viewModel.onEvent(ProfileEvent.SetSelectedGenderOption(state))
                            })

                        if (selectedGenderOption == ThreeToggleSelections.Second) {


                            TwoTogglesGroup(selectionTypeText = "Are you having periods?",
                                selectedOption = selectedOnPeriodOption,
                                onStateChange = { state ->
                                    viewModel.onEvent(ProfileEvent.SetSelectedIsOnPeriodOption(state))
                                })

                            TwoTogglesGroup(selectionTypeText = "Are you Pregnant",
                                selectedOption = selectedIsPregnantOption,
                                onStateChange = { state ->
                                    viewModel.onEvent(ProfileEvent.SetSelectedIsPregnantOption(state))
                                })

                            if (selectedIsPregnantOption == TwoToggleSelections.First) {

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
                                    ValidateNumberField(
                                        value = pregnancyWeek.value,
                                        onValueChange = {
                                            viewModel.onEvent(
                                                ProfileEvent.OnUserPregWeekChange(
                                                    week = it
                                                )
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                        showError = pregnancyWeek.error !is UiString.Empty,
                                        errorMessage = pregnancyWeek.error
                                    )
                                }

                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(spacing.medium))

                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
                ) {
                    BodyTypeLayout()
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(
                eventPrevious, eventNext, text = "Next", enableButton = phyValid
            )

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }

    CalendarDialog(state = calendarState, selection = CalendarSelection.Date {
        viewModel.onEvent(
            ProfileEvent.OnUserDOBChange(
                dob = it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
            )
        )
        viewModel.onEvent(ProfileEvent.OnUserAGEChange(age = "${c.get(Calendar.YEAR) - it.year}"))
    }, config = CalendarConfig(monthSelection = true, yearSelection = true))

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
