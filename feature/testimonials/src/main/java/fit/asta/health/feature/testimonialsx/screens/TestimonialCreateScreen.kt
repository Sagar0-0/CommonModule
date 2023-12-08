package fit.asta.health.feature.testimonialsx.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent
import fit.asta.health.feature.testimonialsx.components.TestimonialCreateSuccessScreen
import fit.asta.health.resources.strings.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestimonialCreateScreenControl(
    userTestimonialState: UiState<Testimonial>,
    userTestimonialData: Testimonial,
    testimonialSubmitApiState: UiState<SaveTestimonialResponse>,
    onBack: () -> Unit,
    navigate: (String) -> Unit,
    setEvent: (TestimonialEvent) -> Unit
) {

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
                // TODO : -- Calling the View Model for user's testimonial data
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
                    navigate = navigate,
                    setEvent = setEvent
                )
            }

            // Error Retry State
            is UiState.ErrorRetry -> {
                AppErrorScreen {
                    // TODO : -- Calling the View Model for user's testimonial data
//                testimonialViewModel.loadUserTestimonialData()
                }
            }

            // No Internet State
            is UiState.NoInternet -> {
                AppInternetErrorDialog {
                    // TODO : -- Calling the View Model for user's testimonial data
//                testimonialViewModel.loadUserTestimonialData()
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