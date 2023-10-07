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
import fit.asta.health.designsystem.molecular.EndScreenPopup
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.feature.feedback.components.SessionFeedback
import fit.asta.health.feature.feedback.vm.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val FEEDBACK_GRAPH_ROUTE = "graph_feedback"

fun NavController.navigateToFeedback(feature: String, navOptions: NavOptions? = null) {
    this.navigate("$FEEDBACK_GRAPH_ROUTE/$feature", navOptions)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.feedbackRoute(onBack: () -> Unit) {
    composable("$FEEDBACK_GRAPH_ROUTE/{feature}") {
        val feature = it.arguments?.getString("feature") ?: ""

        val context = LocalContext.current
        val feedbackViewModel: FeedbackViewModel = hiltViewModel()

        LaunchedEffect(Unit) { feedbackViewModel.loadFeedbackQuestions(feature) }

        val quesState by feedbackViewModel.feedbackQuestions.collectAsStateWithLifecycle()
        val postResultState by feedbackViewModel.feedbackPostState.collectAsStateWithLifecycle()

        when (postResultState) {
            UiState.Idle -> {
                SessionFeedback(
                    feedbackQuesState = quesState,
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

            is UiState.ErrorMessage -> {
                val errorMessage =
                    (postResultState as UiState.ErrorMessage).resId.toStringFromResId()
                LaunchedEffect(postResultState) {
                    Toast.makeText(
                        context,
                        "ERROR MSG: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> {}
        }
    }
}