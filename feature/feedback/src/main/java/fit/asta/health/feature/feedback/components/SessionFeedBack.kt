package fit.asta.health.feature.feedback.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.Media
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppRetryCard
import fit.asta.health.designsystem.molecular.UploadFiles
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun

        SessionFeedback(
    feedbackQuesState: UiState<FeedbackQuesDTO>,
    onBack: () -> Unit,
    onSubmit: (ans: List<An>) -> Unit
) {
    val context = LocalContext.current
    AppScaffold(
        topBar = {
            AppTopBar(title = "Feedback", onBack = onBack)
        }
    ) {
        when (feedbackQuesState) {
            is UiState.ErrorRetry -> {
                AppRetryCard(text = feedbackQuesState.resId.toStringFromResId()) {
                    onBack()
                }
            }

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

            is UiState.ErrorMessage -> {
                TitleTexts.Level2(text = feedbackQuesState.resId.toStringFromResId())
                LaunchedEffect(feedbackQuesState) {
                    Toast.makeText(
                        context,
                        feedbackQuesState.resId.toStringFromResId(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                onBack()
            }

            is UiState.Success -> {
                Box(modifier = Modifier.padding(it)) {
                    FeedbackSuccessScreen(
                        feedbackQuesState = feedbackQuesState.data,
                        onSubmit = onSubmit
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun FeedbackQuesItem(qn: Qn, updatedAns: (An) -> Unit, isValid: (Boolean) -> Unit) {
    val context = LocalContext.current
    if (qn.type == 1) {

        // TODO :- Need to remove this function from the design system
        UploadFiles(
            modifier = Modifier.fillMaxWidth(),
            updatedUriList = {
                val medias = it.map { uri ->
                    Media(
                        name = DocumentFile.fromSingleUri(context, uri)?.name ?: "",
                        url = "",
                        localUri = uri
                    )
                }
                isValid(!(qn.isMandatory && medias.isEmpty()))
                updatedAns(
                    An(
                        dtlAns = null,
                        media = medias,
                        opts = qn.opts,
                        qid = qn.qno,
                        type = qn.type
                    )
                )
            }
        )
    } else {
        FeedbackTextFieldItem(
            qn = qn,
            updatedAns = updatedAns,
            isValid = isValid
        )
    }
}


@Composable
private fun FeedbackSuccessScreen(
    feedbackQuesState: FeedbackQuesDTO,
    onSubmit: (ans: List<An>) -> Unit
) {
    val qns = feedbackQuesState.qns
    val list = remember { qns.map { false }.toMutableStateList() }
    var isEnabled = list.none { !it }
    val ansList = remember { qns.map { An() }.toMutableStateList() }

    // Parent Composable which overlaps the Whole Screen
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Your Feedback will help us Card Composable
        item {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            WelcomeCard()
        }

        // Feedback Question Items and all the Feedback Composable are shown here
        itemsIndexed(qns) { idx, qn ->
            FeedbackQuesItem(
                qn = qn,
                updatedAns = { an ->
                    ansList[idx] = an
                },
                isValid = { valid ->
                    Log.d("FEE", "SessionFeedback: ${list.toList()}")
                    list[idx] = valid
                }
            )
        }

        // Submit Button
        item {
            SubmitButton(
                enabled = isEnabled,
                onDisable = {
                    isEnabled = false
                }
            ) {
                Log.e("ANS", "SessionFeedback: ${ansList.toList()}")
                onSubmit(ansList.toList())
            }
        }
    }
}