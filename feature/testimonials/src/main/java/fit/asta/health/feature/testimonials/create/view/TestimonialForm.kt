package fit.asta.health.feature.testimonials.create.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.UiString
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.AppTextFieldValidate
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.OnSuccessfulSubmit
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.ValidateTxtLength
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.other.HandleBackPress
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnTypeChange
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateTestimonialScreen(
    testimonialViewModel: TestimonialViewModel,
    onBack: () -> Unit
) {
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = R.string.testimonial_title_edit.toStringFromResId(),
                onBack = {
                    showCustomDialogWithResult = !showCustomDialogWithResult
                })
        }
    ) {
        TestimonialForm(
            paddingValues = it,
            onNavigateTstHome = onBack,
            testimonialViewModel = testimonialViewModel,
        )
    }
    HandleBackPress {
        showCustomDialogWithResult = !showCustomDialogWithResult
    }
    if (showCustomDialogWithResult) {
        ShowCustomConfirmationDialog(
            onDismiss = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            onNegativeClick = onBack,
            onPositiveClick = {
                showCustomDialogWithResult = !showCustomDialogWithResult
            },
            dialogData = DialogData(
                dialogTitle = "Discard Testimonial",
                dialogDesc = "Allow Permission to send you notifications when new art styles added.",
                negTitle = "Discard Testimonials",
                posTitle = "Cancel"
            )
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialForm(
    paddingValues: PaddingValues,
    onNavigateTstHome: () -> Unit,
    testimonialViewModel: TestimonialViewModel,
) {

    val type by testimonialViewModel.type.collectAsStateWithLifecycle()
    val title by testimonialViewModel.title.collectAsStateWithLifecycle()
    val testimonial by testimonialViewModel.testimonial.collectAsStateWithLifecycle()
    val organization by testimonialViewModel.org.collectAsStateWithLifecycle()
    val role by testimonialViewModel.role.collectAsStateWithLifecycle()
    val areInputsValid by testimonialViewModel.areInputsValid.collectAsStateWithLifecycle()
    val areMediaValid by testimonialViewModel.areMediaValid.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }

    val radioButtonList = listOf(
        TestimonialType.TEXT,
        TestimonialType.IMAGE,
        TestimonialType.VIDEO
    )

    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    val selectedOption = radioButtonList.find {
        it == type
    } ?: radioButtonList[0]

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val event = testimonialViewModel.saveTestimonialState.collectAsState()
    val events = event.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = AppTheme.spacing.level2,
                end = AppTheme.spacing.level2
            )
    ) {
        Column {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            TestimonialsRadioButton(selectionTypeText = "Testimonial Type",
                radioButtonList = radioButtonList,
                selectedOption = selectedOption,
                onOptionSelected = {
                    testimonialViewModel.onEvent(OnTypeChange(it))
                })

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {
                AppTextFieldValidate(
                    value = title.value,
                    label = "Title",
                    isError = title.error !is UiString.Empty,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }),
                    onValueChange = { testimonialViewModel.onEvent(TestimonialEvent.OnTitleChange(it)) },
                    errorMessage = title.error,
                )

                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

                if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[1]) {
                    AppTextFieldValidate(
                        value = testimonial.value,
                        label = "Testimonial",
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.clearFocus()
                        }),
                        onValueChange = {
                            testimonialViewModel.onEvent(
                                TestimonialEvent.OnTestimonialChange(
                                    it
                                )
                            )
                        },
                        errorMessage = testimonial.error,
                        isError = testimonial.error !is UiString.Empty,
                        modifier = Modifier.height(AppTheme.boxSize.level8),
                        imeAction = if (testimonial.value.length > ValidateTxtLength.defLength) {
                            ImeAction.Next
                        } else {
                            ImeAction.Default
                        },
                        showLenErrorMsg = true
                    )
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                }

                AppTextFieldValidate(
                    value = organization.value,
                    onValueChange = { testimonialViewModel.onEvent(TestimonialEvent.OnOrgChange(it)) },
                    label = "Organisation",
                    isError = organization.error !is UiString.Empty,
                    errorMessage = organization.error,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
                )

                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

                AppTextFieldValidate(
                    value = role.value,
                    onValueChange = { testimonialViewModel.onEvent(TestimonialEvent.OnRoleChange(it)) },
                    label = "Role",
                    isError = role.error !is UiString.Empty,
                    errorMessage = role.error,
                    imeAction = ImeAction.Done,
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

                if (selectedOption == radioButtonList[1]) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    ImageLayout(
                        getViewModel = testimonialViewModel,
                    )
                } else if (selectedOption == radioButtonList[2]) {
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    TstGetVideo()
                }

                AppFilledButton(
                    textToShow = "Submit",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = areInputsValid && areMediaValid
                ) {
                    showCustomDialogWithResult = !showCustomDialogWithResult
                    testimonialViewModel.onEvent(TestimonialEvent.OnSubmitTestimonial)
                }

                if (showCustomDialogWithResult) {
                    when (events) {

                        is UiState.Loading -> AppDotTypingAnimation()
                        is UiState.Success -> {
                            OnSuccessfulSubmit(
                                onDismiss = {
                                    showCustomDialogWithResult = !showCustomDialogWithResult
                                },
                                onNavigateTstHome = onNavigateTstHome,
                                onPositiveClick = onNavigateTstHome
                            )
                        }

                        is UiState.NoInternet -> AppInternetErrorDialog {
                            testimonialViewModel.onEvent(TestimonialEvent.OnSubmitTestimonial)
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}