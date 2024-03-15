package fit.asta.health.feature.feedback.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.data.feedback.remote.modal.Answer
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestions
import fit.asta.health.data.feedback.remote.modal.Media
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton

@Composable
fun FeedbackQuesScreen(
    feedbackQues: FeedbackQuestions,
    onSubmit: (answers: List<Answer>) -> Unit
) {
    val questions = remember {
        feedbackQues.questions
    }
    val validAnswersList = remember { questions.map { !it.isMandatory }.toMutableStateList() }
    var isSubmitButtonEnabled = validAnswersList.all { it }
    val answersList = remember { questions.map { Answer() }.toMutableStateList() }

    // Parent Composable which overlaps the Whole Screen
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2)
            .semantics { contentDescription = "LazyColumn" },
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Your Feedback will help us Card Composable
        item {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            WelcomeCard()
        }

        // Feedback Question Items and all the Feedback Composable are shown here
        itemsIndexed(questions) { idx, qn ->
            FeedbackQuesItem(
                question = qn,
                answer = answersList[idx],
                isAnswerValid = validAnswersList[idx],
                updatedAnswer = { newAns ->
                    answersList[idx] = newAns
                },
                onAnswerValidityChange = { newValidity ->
                    validAnswersList[idx] = newValidity
                }
            )
        }

        // Submit Button
        item {
            val context = LocalContext.current
            AppFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "SubmitButton" },
                textToShow = "Submit",
                enabled = isSubmitButtonEnabled
            ) {
                Log.e("ANS", "SessionFeedback: ${answersList.toList()}")
                feedbackQues.questions.forEachIndexed { idx, ques ->
                    val medias = answersList[idx].mediaUri.map { uri ->
                        Media(
                            name = DocumentFile.fromSingleUri(context, uri)?.name ?: "",
                            url = "",
                            localUri = uri
                        )
                    }
                    answersList[idx] = answersList[idx].copy(
                        media = medias,
                        qid = ques.questionNo,
                        type = ques.type
                    )
                }
                onSubmit(answersList.toList())
            }
        }
    }
}