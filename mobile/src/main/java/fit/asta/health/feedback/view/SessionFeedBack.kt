package fit.asta.health.feedback.view

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fit.asta.health.common.ui.CustomTopBar
import fit.asta.health.common.ui.components.UploadFiles
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getFileName
import fit.asta.health.feedback.model.network.An
import fit.asta.health.feedback.model.network.Media
import fit.asta.health.feedback.model.network.Qn
import fit.asta.health.feedback.view.components.SubmitButton
import fit.asta.health.feedback.view.components.WelcomeCard
import fit.asta.health.feedback.view.components.feedbackTextFieldItem
import fit.asta.health.feedback.viewmodel.FeedbackQuesState
import kotlin.math.log

@Composable
fun SessionFeedback(
    feedbackQuesState: FeedbackQuesState,
    onSubmit: (ans: List<An>) -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CustomTopBar(text = "Feedback") {
                (context as Activity).finish()
            }
        }
    ) {
        when (feedbackQuesState) {
            FeedbackQuesState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it)
                )
            }

            is FeedbackQuesState.Error -> {
                feedbackQuesState.error.message?.let { it1 -> Text(text = it1) }
                Log.e("QUES", "SessionFeedback: ${feedbackQuesState.error}")
                Toast.makeText(
                    context,
                    "Unexpected error occurred.",
                    Toast.LENGTH_SHORT
                ).show()
                (context as Activity).finish()
            }

            is FeedbackQuesState.Success -> {
                val qns = feedbackQuesState.feedback.data.qns
                val ansList = remember {
                    mutableStateOf(
                        qns.map { An(null,false,null,null,0,0) } as MutableList<An>
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(it)
                        .verticalScroll(rememberScrollState())
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {

                    Spacer(modifier = Modifier.height(spacing.medium))
                    WelcomeCard()
                    Spacer(modifier = Modifier.height(spacing.medium))

                    qns.forEachIndexed { idx, qn ->
                        ansList.value[idx] = feedbackQuesItem(qn).value
                        Spacer(modifier = Modifier.height(spacing.medium))
                    }

                    SubmitButton(text = "Submit") {
                        Log.e("ANS", "SessionFeedback: ${ansList.value.toList()}")
                        onSubmit(ansList.value.toList())
                    }

                    Spacer(modifier = Modifier.height(spacing.small))
                }
            }
        }
    }
}

@Composable
fun feedbackQuesItem(qn: Qn): MutableState<An> {
    val context = LocalContext.current
    val ans = remember { mutableStateOf(An(null, false, null, null, 0, 0)) }
    if (qn.type == 1) {
        val uriList = UploadFiles(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium)
        )
        val medias = uriList.map {
            Media(
                name = getFileName(context, it),
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



