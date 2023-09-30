package fit.asta.health.feature.feedback.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.components.uploadFiles
import fit.asta.health.designsystem.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionFeedback(
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
            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            is UiState.ErrorMessage -> {
                Text(text = feedbackQuesState.resId.toStringFromResId())
                LaunchedEffect(feedbackQuesState) {
                    Toast.makeText(
                        context,
                        "Unexpected error occurred.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                onBack()
            }

            is UiState.Success -> {
                val qns = feedbackQuesState.data.qns
                val ansList = remember {
                    mutableStateOf(
                        qns.map {
                            An(
                                null,
                                false,
                                null,
                                null,
                                0,
                                0
                            )
                        } as MutableList<An>
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {

                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                    WelcomeCard()
                    Spacer(modifier = Modifier.height(AppTheme.spacing.medium))

                    qns.forEachIndexed { idx, qn ->
                        ansList.value[idx] = feedbackQuesItem(qn).value
                        Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                    }

                    SubmitButton(text = "Submit") {
                        Log.e("ANS", "SessionFeedback: ${ansList.value.toList()}")
                        onSubmit(ansList.value.toList())
                    }

                    Spacer(modifier = Modifier.height(AppTheme.spacing.small))
                }
            }

            else -> {}
        }
    }
}

@Composable
fun feedbackQuesItem(qn: Qn): MutableState<An> {
    val context = LocalContext.current
    val ans = remember {
        mutableStateOf(
            An(
                null,
                false,
                null,
                null,
                0,
                0
            )
        )
    }
    if (qn.type == 1) {
        val uriList = uploadFiles(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.medium)
        )
        val medias = uriList.map {
            Media(
                name = DocumentFile.fromSingleUri(context, it)?.name ?: "",
                url = "",
                localUri = it
            )
        }
        ans.value = An(
            dtlAns = null,
            isDet = qn.isDet,
            media = medias,
            opts = qn.opts,
            qid = qn.qno,
            type = qn.type
        )
    } else {
        ans.value = feedbackTextFieldItem(qn).value
    }

    Log.d("ANS", "Ques No.${qn.qno}:ans = ${ans.value}")
    return ans
}



