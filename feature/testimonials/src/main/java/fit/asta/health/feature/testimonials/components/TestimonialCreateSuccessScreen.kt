package fit.asta.health.feature.testimonials.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.OnSuccessfulSubmit
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.feature.testimonials.events.MediaType
import fit.asta.health.feature.testimonials.events.TestimonialEvent
import fit.asta.health.feature.testimonials.utils.checkInputValidity


@Composable
fun TestimonialCreateSuccessScreen(
    paddingValues: PaddingValues,
    userTestimonialData: Testimonial,
    testimonialSubmitApiState: UiState<SaveTestimonialResponse>,
    onBack: () -> Unit,
    setEvent: (TestimonialEvent) -> Unit
) {

    // This variable contains the list of available Testimonial Types
    val radioButtonList = listOf(
        TestimonialType.TEXT,
        TestimonialType.IMAGE,
        TestimonialType.VIDEO
    )

    // This variable states when to show the feedback popup when the submit button is clicked
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    // This contains the selected Testimonial type
    val selectedOption = radioButtonList.find {
        it.value == userTestimonialData.type
    } ?: radioButtonList[0]

    // FocusManager Object
    val focusManager = LocalFocusManager.current

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
        ) { setEvent(TestimonialEvent.OnTypeChange(it)) }


        // Text Field to enter the title of the Testimonial
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userTestimonialData.title,
            label = "Title",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            onValueChange = { setEvent(TestimonialEvent.OnTitleChange(it)) },
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(minSize = 4, maxSize = 32)
            )
        )

        // If text and Image testimonial is chosen then we show this text Field
        if (selectedOption == radioButtonList[0] || selectedOption == radioButtonList[1]) {
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userTestimonialData.testimonial,
                label = "Testimonial",
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                onValueChange = {
                    setEvent(TestimonialEvent.OnTestimonialChange(it))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                minLines = 4,
                maxLines = 4,
                appTextFieldType = AppTextFieldValidator(
                    AppTextFieldType.Custom(minSize = 4, maxSize = 256)
                )
            )
        }

        // Organisation Text Field
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userTestimonialData.user.org,
            label = "Organisation",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(minSize = 4, maxSize = 32)
            ),
            onValueChange = { setEvent(TestimonialEvent.OnOrgChange(it)) }
        )

        // Role Text Field
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userTestimonialData.user.role,
            label = "Role",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(minSize = 4, maxSize = 32)
            ),
            onValueChange = { setEvent(TestimonialEvent.OnRoleChange(it)) },
        )

        // TODO :- Image and Video Input are left
        when (selectedOption) {
            TestimonialType.TEXT -> {
                // Does Nothing
            }

            TestimonialType.IMAGE -> {
                TestimonialUploadImage(
                    media = userTestimonialData.media,
                    onImageSelected = { mediaType, uri ->
                        setEvent(TestimonialEvent.OnMediaSelect(mediaType, uri))
                    },
                    onImageDelete = { setEvent(TestimonialEvent.OnMediaClear(it)) }
                )
            }

            TestimonialType.VIDEO -> {
                TestimonialUploadVideo(
                    media = userTestimonialData.media,
                    onVideoSelected = {
                        setEvent(TestimonialEvent.OnMediaSelect(MediaType.Video, it))
                    },
                    onVideoDeleted = {
                        setEvent(TestimonialEvent.OnMediaClear(MediaType.Video))
                    }
                )
            }
        }


        // Submit Button
        AppFilledButton(
            modifier = Modifier.fillMaxWidth(),
            textToShow = "Submit",
            enabled = checkInputValidity(userTestimonialData)
        ) {
            showCustomDialogWithResult = !showCustomDialogWithResult
            setEvent(TestimonialEvent.OnSubmitTestimonial)
        }

        if (showCustomDialogWithResult) {
            when (testimonialSubmitApiState) {

                is UiState.Loading -> AppDotTypingAnimation()
                is UiState.Success -> {
                    OnSuccessfulSubmit(
                        onDismiss = {
                            showCustomDialogWithResult = !showCustomDialogWithResult
                            onBack()
                        },
                        onNavigateTstHome = onBack,
                        onPositiveClick = onBack
                    )
                }

                is UiState.NoInternet -> AppInternetErrorDialog {
                    setEvent(TestimonialEvent.OnSubmitTestimonial)
                }

                else -> {}
            }
        }
    }
}