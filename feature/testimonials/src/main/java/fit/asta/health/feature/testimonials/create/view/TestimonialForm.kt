package fit.asta.health.feature.testimonials.create.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.OnSuccessfulSubmit
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.other.HandleBackPress
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
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
                onBack = { showCustomDialogWithResult = !showCustomDialogWithResult }
            )
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

    val event = testimonialViewModel.saveTestimonialState.collectAsState()
    val events = event.value

    // Parent Composable Function
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = AppTheme.spacing.level2,
                end = AppTheme.spacing.level2,
                bottom = AppTheme.spacing.level2
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // This function draws the testimonial radio buttons Card
        TestimonialsRadioButtonCard(
            cardTitle = "Testimonial Type",
            radioButtonList = radioButtonList,
            selectedOption = selectedOption
        ) { testimonialViewModel.onEvent(OnTypeChange(it)) }


        // Text Field to enter the title of the Testimonial
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title.value,
            label = "Title",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            onValueChange = { testimonialViewModel.onEvent(TestimonialEvent.OnTitleChange(it)) },
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(minSize = 4, maxSize = 32)
            )
        )

        // If text and Image testimonial is chosen then we show this text Field
        if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[1]) {
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = testimonial.value,
                label = "Testimonial",
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                onValueChange = {
                    testimonialViewModel.onEvent(TestimonialEvent.OnTestimonialChange(it))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                minLines = 4,
                maxLines = 4
            )
        }

        // Organisation Text Field
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = organization.value,
            label = "Organisation",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(minSize = 4, maxSize = 32)
            ),
            onValueChange = { testimonialViewModel.onEvent(TestimonialEvent.OnOrgChange(it)) }
        )

        // Role Text Field
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = role.value,
            label = "Role",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(minSize = 4, maxSize = 32)
            ),
            onValueChange = { testimonialViewModel.onEvent(TestimonialEvent.OnRoleChange(it)) },
        )

        // Checking which testimonial Type is selected by the user
        when (selectedOption) {

            // Text Testimonial
            radioButtonList[0] -> {
                // No extra UI needed
            }

            // Image Testimonial
            radioButtonList[1] -> {
                ImageLayout(getViewModel = testimonialViewModel)
            }

            // Video Testimonial
            radioButtonList[2] -> {
                TstGetVideo()
            }

            else -> {}
        }

        // Submit Button
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