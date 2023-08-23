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
import fit.asta.health.designsystem.components.EndScreenPopup
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.feedback.components.SessionFeedback
import fit.asta.health.feature.feedback.vm.FeedbackViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val FEEDBACK_GRAPH_ROUTE = "graph_feedback"

fun NavController.navigateToFeedback(fid: String, navOptions: NavOptions? = null) {
    this.navigate("${fit.asta.health.feature.feedback.FEEDBACK_GRAPH_ROUTE}/$fid", navOptions)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.feedbackRoute(navController: NavController) {
    composable("${fit.asta.health.feature.feedback.FEEDBACK_GRAPH_ROUTE}/{fid}") {
        val fid = it.arguments?.getString("fid") ?: ""

        val context = LocalContext.current
        val feedbackViewModel: FeedbackViewModel = hiltViewModel()

        LaunchedEffect(Unit) { feedbackViewModel.loadFeedbackQuestions(fid) }

        val quesState by feedbackViewModel.feedbackQuestions.collectAsStateWithLifecycle()
        val postResultState by feedbackViewModel.feedbackPostState.collectAsStateWithLifecycle()

        when (postResultState) {
            UiState.Idle -> {
                SessionFeedback(
                    feedbackQuesState = quesState,
                    onBack = navController::navigateUp,
                    onSubmit = feedbackViewModel::postUserFeedback
                )
            }

            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            is UiState.Success -> {
                EndScreenPopup(
                    title = "Thank you!",
                    desc = "Your feedback has been submitted",
                    onContinueClick = navController::navigateUp
                )
            }

            is UiState.Error -> {
                val error = (postResultState as UiState.Error).resId.toStringFromResId()
                LaunchedEffect(postResultState) {
                    Toast.makeText(
                        context,
                        "ERROR MSG: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}