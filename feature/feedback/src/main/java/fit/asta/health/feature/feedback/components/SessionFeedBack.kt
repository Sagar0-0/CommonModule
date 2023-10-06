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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.UploadFiles
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.components.generic.LoadingAnimation

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
                AppTexts.TitleMedium(text = feedbackQuesState.resId.toStringFromResId())
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
                        .background(color = AppTheme.colors.secondaryContainer)
                ) {
                    val enabled = remember {
                        mutableStateOf(true)
                    }

                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    WelcomeCard()
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

                    qns.forEachIndexed { idx, qn ->
                        FeedbackQuesItem(
                            qn = qn,
                            updatedAns = { an ->
                                ansList.value[idx] = an
                            },
                            isValid = { valid ->
                                enabled.value = enabled.value && valid
                            }
                        )
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    }


                    SubmitButton(
                        enabled = enabled.value,
                        onDisable = {
                            enabled.value = false
                        }
                    ) {
                        Log.e("ANS", "SessionFeedback: ${ansList.value.toList()}")
                        onSubmit(ansList.value.toList())
                    }

                    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
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
        UploadFiles(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2),
            updatedUriList = {
                val medias = it.map { uri ->
                    Media(
                        name = DocumentFile.fromSingleUri(context, uri)?.name ?: "",
                        url = "",
                        localUri = uri
                    )
                }
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



