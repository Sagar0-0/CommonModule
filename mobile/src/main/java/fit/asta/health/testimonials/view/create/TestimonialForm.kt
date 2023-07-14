package fit.asta.health.testimonials.view.create

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.jetpack.HandleBackPress
import fit.asta.health.common.ui.theme.boxSize
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.UiString
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.view.components.ValidatedTextField
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialSubmitState
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialForm(
    editViewModel: TestimonialViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onNavigateTstHome: () -> Unit,
) {

    val type by editViewModel.type.collectAsStateWithLifecycle()
    val title by editViewModel.title.collectAsStateWithLifecycle()
    val testimonial by editViewModel.testimonial.collectAsStateWithLifecycle()
    val organization by editViewModel.org.collectAsStateWithLifecycle()
    val role by editViewModel.role.collectAsStateWithLifecycle()
    val areInputsValid by editViewModel.areInputsValid.collectAsStateWithLifecycle()
    val areMediaValid by editViewModel.areMediaValid.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }

    val radioButtonList = listOf(
        TestimonialType.TEXT, TestimonialType.IMAGE, TestimonialType.VIDEO
    )

    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val selectedOption = radioButtonList.find {
        it == type
    } ?: radioButtonList[0]

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val event = editViewModel.stateSubmit.collectAsState()
    val events = event.value


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = spacing.medium,
                end = spacing.medium
            )
            .verticalScroll(scrollState), verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(spacing.medium))

        TestimonialsRadioButton(
            selectionTypeText = "Testimonial Type",
            radioButtonList = radioButtonList,
            selectedOption = selectedOption,
            content = {
                ValidatedTextField(
                    value = testimonial.value,
                    onValueChange = {
                        editViewModel.onEvent(
                            TestimonialEvent.OnTestimonialChange(
                                it
                            )
                        )
                    },
                    label = "Testimonial",
                    showError = testimonial.error !is UiString.Empty,
                    errorMessage = testimonial.error,
                    keyboardOptions = if (testimonial.value.length < 512) {
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Default
                        )
                    } else {
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        )
                    },
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier.height(boxSize.extraMedium)
                )
            },
            titleTestimonial = {
                ValidatedTextField(
                    value = title.value,
                    onValueChange = { editViewModel.onEvent(TestimonialEvent.OnTitleChange(it)) },
                    label = "Title",
                    showError = title.error !is UiString.Empty,
                    errorMessage = title.error,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                )
            },
            onOptionSelected = {
                editViewModel.onEvent(TestimonialEvent.OnTypeChange(it))
            })

        Spacer(modifier = Modifier.height(spacing.medium))

        ValidatedTextField(
            value = organization.value,
            onValueChange = { editViewModel.onEvent(TestimonialEvent.OnOrgChange(it)) },
            label = "Organisation",
            showError = organization.error !is UiString.Empty,
            errorMessage = organization.error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )

        Spacer(modifier = Modifier.height(spacing.medium))

        ValidatedTextField(
            value = role.value,
            onValueChange = { editViewModel.onEvent(TestimonialEvent.OnRoleChange(it)) },
            label = "Role",
            showError = role.error !is UiString.Empty,
            errorMessage = role.error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            modifier = Modifier.focusRequester(focusRequester = focusRequester),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        if (selectedOption == radioButtonList[1]) {
            Spacer(modifier = Modifier.height(spacing.medium))
            ImageLayout()
        } else if (selectedOption == radioButtonList[2]) {
            Spacer(modifier = Modifier.height(spacing.medium))
            TestGetVideo()
        }

        androidx.compose.material.Button(
            onClick = {
                showCustomDialogWithResult = !showCustomDialogWithResult
                editViewModel.onEvent(TestimonialEvent.OnSubmit)
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(spacing.medium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue, contentColor = Color.White
            ),
            enabled = areInputsValid && areMediaValid
        ) {
            Text(text = "Submit", style = MaterialTheme.typography.labelLarge)
        }

        if (showCustomDialogWithResult) {

            when (events) {

                is TestimonialSubmitState.Error -> {
                    Log.d("validate", "Error -> ${events.error}")
                }

                is TestimonialSubmitState.Loading -> LoadingAnimation()

                is TestimonialSubmitState.Success -> {
                    OnSuccessfulSubmit(onDismiss = {
                        showCustomDialogWithResult = !showCustomDialogWithResult
                    }, onNavigateTstHome = onNavigateTstHome, onPositiveClick = {
                        onNavigateTstHome()
                    })
                }

                is TestimonialSubmitState.NetworkError -> ErrorScreenLayout(onTryAgain = {
                    editViewModel.onEvent(
                        TestimonialEvent.OnSubmit
                    )
                })
            }
        }

    }


}


@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateTstScreen(title: String, onNavigateTstCreate: () -> Unit, onNavigateTstHome: () -> Unit) {

    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            }) {
                Icon(
                    Icons.Filled.NavigateBefore,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }, modifier = Modifier.shadow(elevation = cardElevation.medium))
    }, content = {
        TestimonialForm(paddingValues = it, onNavigateTstHome = onNavigateTstHome)
    }, containerColor = MaterialTheme.colorScheme.background)


    HandleBackPress {
        showCustomDialogWithResult = !showCustomDialogWithResult
    }

    if (showCustomDialogWithResult) {
        CustomDialogWithResultExample(
            onDismiss = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            onNegativeClick = {
                onNavigateTstCreate()
            },
            onPositiveClick = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            btnTitle = "Discard Testimonial",
            btnWarn = "Allow Permission to send you notifications when new art styles added.",
            btn1Title = "Discard Testimonials",
            btn2Title = "Cancel"
        )
    }


}