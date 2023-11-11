package fit.asta.health.feature.feedback

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.EndScreenPopup
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.feature.feedback.components.SessionFeedback
import fit.asta.health.feature.feedback.vm.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val FEEDBACK_GRAPH_ROUTE = "graph_feedback"

fun NavController.navigateToFeedback(featureId: String, navOptions: NavOptions? = null) {
    this.navigate("$FEEDBACK_GRAPH_ROUTE/$featureId", navOptions)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.feedbackRoute(onBack: () -> Unit) {
    composable("$FEEDBACK_GRAPH_ROUTE/{feature}") {
        val featureId = it.arguments?.getString("feature") ?: ""

        val context = LocalContext.current
        val feedbackViewModel: FeedbackViewModel = hiltViewModel()

        LaunchedEffect(Unit) { feedbackViewModel.loadFeedbackQuestions(featureId) }

        val feedbackQuesState by feedbackViewModel.feedbackQuestions.collectAsStateWithLifecycle()
        val feedbackPostResultState by feedbackViewModel.feedbackPostState.collectAsStateWithLifecycle()

        when (feedbackPostResultState) {
            UiState.Idle -> {
                SessionFeedback(
                    feedbackQuesState = feedbackQuesState,
                    onBack = onBack,
                    onSubmit = feedbackViewModel::postUserFeedback
                )
            }

            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppCircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                EndScreenPopup(
                    title = "Thank you!",
                    desc = "Your feedback has been submitted",
                    onContinueClick = onBack
                )
            }

            is UiState.NoInternet -> {
                AppInternetErrorDialog {
                    feedbackViewModel.resetPostResultState()
                }
            }

            is UiState.ErrorRetry -> {
                AppErrorScreen(text = (feedbackPostResultState as UiState.ErrorRetry).resId.toStringFromResId()) {
                    feedbackViewModel.resetPostResultState()
                }
            }

            is UiState.ErrorMessage -> {
                val errorMessage =
                    (feedbackPostResultState as UiState.ErrorMessage).resId.toStringFromResId()
                LaunchedEffect(feedbackPostResultState) {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}