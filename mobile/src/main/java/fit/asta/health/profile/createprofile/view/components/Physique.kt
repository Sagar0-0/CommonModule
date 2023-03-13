package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.view.ButtonListTypes
import fit.asta.health.profile.view.MultiToggleLayout
import fit.asta.health.profile.view.NextButton
import fit.asta.health.testimonials.view.components.ValidateNumberField
import fit.asta.health.testimonials.view.components.ValidatedTextField
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.ui.spacing
import fit.asta.health.utils.UiString


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhysiqueContent(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    onSkipEvent: (Int) -> Unit,
) {

    val placeHolderDOB = listOf("DAY", "MONTH", "YEAR")

    val buttonTypeList = listOf(
        ButtonListTypes(buttonType = "Female"),
        ButtonListTypes(buttonType = "Male"),
        ButtonListTypes(buttonType = "Others")
    )

    val isPregnantList =
        listOf(ButtonListTypes(buttonType = "Yes"), ButtonListTypes(buttonType = "No"))

    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    val (isPregnantSelectedOption, onPregnantOptionSelected) = remember {
        mutableStateOf(
            ""
        )
    }

    var text by remember { mutableStateOf(("")) }
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

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Date of Birth",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "24yr",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(spacing.small))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                userScrollEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {

                placeHolderDOB.forEach { placeHolder ->
                    item {
                        ValidateNumberField(
                            value = text,
                            onValueChange = { text = it },
                            placeholder = placeHolder,
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
                }

            }

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            ValidatedTextField(
                value = text,
                onValueChange = { text = it },
                errorMessage = UiString.Empty,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Height",
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
                RowToggleButtonGroup(
                    buttonCount = 2,
                    onButtonClick = { index -> println(index) },
                    buttonTexts = arrayOf("in", "cm"),
                    modifier = Modifier.size(width = 80.dp, height = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(spacing.extraSmall))

            ValidatedTextField(
                value = text,
                onValueChange = { text = it },
                errorMessage = UiString.Empty,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(cardElevation.extraSmall)
                ) {

                    MultiToggleLayout("Gender", buttonTypeList, selectedOption, onOptionSelected)

                    if (selectedOption == buttonTypeList[0].buttonType) {

                        MultiToggleLayout(
                            selectionTypeText = "Are you Pregnant",
                            radioButtonList = isPregnantList,
                            selectedOption = isPregnantSelectedOption,
                            onOptionSelected = onPregnantOptionSelected
                        )

                        if (isPregnantSelectedOption == isPregnantList[0].buttonType) {

                            Spacer(modifier = Modifier.height(spacing.medium))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = spacing.medium),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    "Please Enter your Pregnancy Week",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }

                            ValidateNumberField(
                                value = text,
                                onValueChange = { text = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = spacing.medium, vertical = spacing.medium
                                    ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                            )
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            CreateProfileButtons(eventPrevious, eventNext, text = "Next")

            Spacer(modifier = Modifier.height(spacing.medium))

            SkipPage(onSkipEvent = onSkipEvent)

            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }

}

@Composable
fun CreateProfileButtons(
    eventPrevious: (() -> Unit)? = null,
    eventNext: (() -> Unit)? = null,
    text: String? = null,
) {

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
        NextButton(text = "Previous", modifier = Modifier.fillMaxWidth(0.5f), event = eventPrevious)
        if (text != null) {
            NextButton(text = text, modifier = Modifier.fillMaxWidth(1f), event = eventNext)
        }
    }

}