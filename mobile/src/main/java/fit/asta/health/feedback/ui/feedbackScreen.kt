package fit.asta.health.feedback.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import fit.asta.health.common.ui.components.*
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.feedback.viewmodel.FeedbackViewModel
import fit.asta.health.main.Graph
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.feedbackScreen(navController: NavController) {
    composable(Graph.Feedback.route + "/{fid}") {
        val fid = it.arguments?.getString("fid")!!

        val context = LocalContext.current
        val feedbackViewModel: FeedbackViewModel = hiltViewModel()

        LaunchedEffect(Unit) { feedbackViewModel.loadFeedbackQuestions(fid) }

        val quesState = feedbackViewModel.feedbackQuestions.collectAsStateWithLifecycle()
        val postResultState = feedbackViewModel.feedbackPostState.collectAsStateWithLifecycle()

        when (postResultState.value) {
            ResponseState.Idle -> {
                SessionFeedback(
                    feedbackQuesState = quesState.value,
                    onBack = navController::navigateUp,
                    onSubmit = feedbackViewModel::postUserFeedback
                )
            }

            ResponseState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            is ResponseState.Success -> {
                EndScreenPopup(
                    title = "Thank you!",
                    desc = "Your feedback has been submitted",
                    onContinueClick = navController::navigateUp
                )
            }

            is ResponseState.Error -> {
                Toast.makeText(
                    context,
                    "ERROR MSG: " + (postResultState.value as ResponseState.Error).error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
}