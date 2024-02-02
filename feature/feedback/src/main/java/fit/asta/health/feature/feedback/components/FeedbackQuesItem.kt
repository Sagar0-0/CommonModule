package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.feedback.remote.modal.Answer
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestionTypes
import fit.asta.health.data.feedback.remote.modal.Question
import fit.asta.health.designsystem.molecular.UploadFiles


const val UPLOAD_LIMIT = 5

@Composable
fun FeedbackQuesItem(
    question: Question,
    answer: Answer,
    isAnswerValid: Boolean,
    updatedAnswer: (Answer) -> Unit,
    onAnswerValidityChange: (Boolean) -> Unit
) {
    if (question.type == FeedbackQuestionTypes.UploadFile.type) {
        UploadFiles(
            modifier = Modifier.fillMaxWidth(),
            uriList = answer.mediaUri.toList(),
            isValid = isAnswerValid,
            errorMessage = if (answer.mediaUri.isEmpty()) "You should upload least 1 file" else "You cannot upload more than $UPLOAD_LIMIT files",
            onItemAdded = {
                val newSet = answer.mediaUri.plus(it)
                updatedAnswer(answer.copy(mediaUri = newSet))
                if (question.isMandatory && newSet.isEmpty())
                    onAnswerValidityChange(false)
                else
                    onAnswerValidityChange(newSet.size <= UPLOAD_LIMIT)
            },
            onItemDeleted = { uri ->
                val newSet = answer.mediaUri.minus(uri)
                updatedAnswer(answer.copy(mediaUri = newSet))
                if (question.isMandatory && newSet.isEmpty())
                    onAnswerValidityChange(false)
                else
                    onAnswerValidityChange(newSet.size <= UPLOAD_LIMIT)
            }
        )
    } else {
        FeedbackTextFieldItem(
            question = question,
            answer = answer,
            updatedAnswer = updatedAnswer,
            isAnswerValid = onAnswerValidityChange
        )
    }
}