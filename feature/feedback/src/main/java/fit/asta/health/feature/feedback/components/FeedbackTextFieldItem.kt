package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import fit.asta.health.data.feedback.remote.modal.Answer
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestionType
import fit.asta.health.data.feedback.remote.modal.Question
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun FeedbackTextFieldItem(
    question: Question,
    answer: Answer,
    updatedAnswer: (Answer) -> Unit,
    isAnswerValid: (Boolean) -> Unit,
) {
    val maxCharsAllowed = question.ansType.max

    AppElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // This is the Question of this Card
            Row {
                TitleTexts.Level3(text = question.questionText)
                if (question.isMandatory) TitleTexts.Level3(
                    modifier = Modifier.padding(start = AppTheme.spacing.level1),
                    text = "*",
                    color = Color.Red
                )
            }

            // This is either the Rating Stars or the Radio Buttons
            when (question.type) {
                FeedbackQuestionType.Rating.type -> {
                    Rating(
                        if (answer.options[0].toIntOrNull() == null) {
                            updatedAnswer(answer.copy(options = listOf("0")))
                            0
                        } else {
                            answer.options[0].toInt()
                        }
                    ) {
                        updatedAnswer(
                            answer.copy(options = listOf(it.toString()))
                        )
                    }
                }

                FeedbackQuestionType.McqCard.type, FeedbackQuestionType.McqCard2.type -> {
                    McqCard(
                        selectedAnswer = answer.options[0],
                        optionsList = question.options,
                        updatedAns = { newAns ->
                            updatedAnswer(
                                answer.copy(options = listOf(newAns))
                            )
                        }
                    )
                }
            }

            if (question.ansType.isDetailed) {
                // This is the Outlined Text Field for the user to give their Feedbacks
                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "AppOutlinedTextField" },
                    value = answer.detailedAnswer,
                    appTextFieldType = AppTextFieldValidator(
                        AppTextFieldType.Custom(
                            question.ansType.min, maxCharsAllowed
                        )
                    ),
                    isValidText = isAnswerValid,
                    minLines = 4,
                    onValueChange = { newText ->
                        updatedAnswer(
                            answer.copy(detailedAnswer = newText)
                        )
                    }
                )
            }
        }
    }
}