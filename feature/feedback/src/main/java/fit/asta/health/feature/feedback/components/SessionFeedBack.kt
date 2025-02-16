package fit.asta.health.feature.feedback.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.feedback.remote.modal.Answer
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestions
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun SessionFeedback(
    feedbackQuesState: UiState<FeedbackQuestions>,
    onBack: () -> Unit,
    onSubmit: (answers: List<Answer>) -> Unit
) {
    val context = LocalContext.current
    AppScaffold(
        topBar = {
            AppTopBar(title = "Feedback", onBack = onBack)
        }
    ) {
        when (feedbackQuesState) {
            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    AppCircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                Box(modifier = Modifier.padding(it)) {
                    FeedbackQuesScreen(
                        feedbackQues = feedbackQuesState.data,
                        onSubmit = onSubmit
                    )
                }
            }

            is UiState.ErrorRetry -> {
                AppErrorScreen(text = feedbackQuesState.resId.toStringFromResId()) {
                    onBack()
                }
            }

            is UiState.ErrorMessage -> {
                TitleTexts.Level2(text = feedbackQuesState.resId.toStringFromResId())
                LaunchedEffect(feedbackQuesState) {
                    Toast.makeText(
                        context,
                        feedbackQuesState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()
                    onBack()
                }
            }

            else -> {}
        }
    }
}