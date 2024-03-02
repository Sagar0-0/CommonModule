package fit.asta.health.feature.testimonials.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.DialogData
import fit.asta.health.designsystem.molecular.ShowCustomConfirmationDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.other.HandleBackPress
import fit.asta.health.feature.testimonials.components.TestimonialCreateSuccessScreen
import fit.asta.health.feature.testimonials.events.TestimonialEvent
import fit.asta.health.resources.strings.R


@Composable
fun TestimonialCreateScreenControl(
    userTestimonialState: UiState<Testimonial>,
    userTestimonialData: Testimonial,
    testimonialSubmitApiState: UiState<SaveTestimonialResponse>,
    onBack: () -> Unit,
    setEvent: (TestimonialEvent) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        setEvent(TestimonialEvent.GetUserTestimonial)
    }

    // This variable is used to prompt when to show the Custom Dialog Bar
    var showCustomDialogWithResult by remember { mutableStateOf(false) }

    // Scaffold
    AppScaffold(
        topBar = {
            AppTopBar(
                title = R.string.testimonial_title_edit.toStringFromResId(),
                onBack = { showCustomDialogWithResult = !showCustomDialogWithResult }
            )
        }
    ) { paddingValues ->

        when (userTestimonialState) {

            // Idle State
            is UiState.Idle -> {
                setEvent(TestimonialEvent.GetUserTestimonial)
            }

            // Loading State
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
            }

            // Success State
            is UiState.Success -> {
                TestimonialCreateSuccessScreen(
                    paddingValues = paddingValues,
                    userTestimonialData = userTestimonialData,
                    testimonialSubmitApiState = testimonialSubmitApiState,
                    onBack = onBack,
                    setEvent = setEvent
                )
            }

            // Error Retry State
            is UiState.ErrorRetry -> {
                AppErrorScreen {
                    setEvent(TestimonialEvent.GetUserTestimonial)
                }
            }

            // No Internet State
            is UiState.NoInternet -> {
                AppInternetErrorDialog {
                    setEvent(TestimonialEvent.GetUserTestimonial)
                }
            }

            else -> {}
        }
    }

    // This function runs the below code when the back button is pressed
    HandleBackPress {
        showCustomDialogWithResult = !showCustomDialogWithResult
    }

    // This Dialog is shown when the user hits back to ask if he really wanna discard or continue
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
                negTitle = "Discard",
                posTitle = "Cancel"
            )
        )
    }
}