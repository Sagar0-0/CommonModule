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
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestionTypes
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

    AppElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // This is the Question of this Card
            Row {
                TitleTexts.Level3(
                    modifier = Modifier.weight(1f),
                    text = question.questionText
                )
                if (question.isMandatory) TitleTexts.Level3(
                    modifier = Modifier.padding(
                        start = AppTheme.spacing.level1,
                        end = AppTheme.spacing.level2
                    ),
                    text = "*",
                    color = Color.Red
                )
            }

            // This is either the Rating Stars or the Radio Buttons
            when (question.type) {
                FeedbackQuestionTypes.Rating.type -> {
                    if (question.isMandatory && answer.options[0].toIntOrNull() == null) {
                        isAnswerValid(false)
                    } else {
                        isAnswerValid(true)
                    }
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

                FeedbackQuestionTypes.McqCard.type, FeedbackQuestionTypes.McqCard2.type -> {
                    if (question.isMandatory && answer.options[0].isEmpty()) {
                        isAnswerValid(false)
                    } else {
                        isAnswerValid(true)
                    }
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

            if (question.ansType.isDetailed || question.type == FeedbackQuestionTypes.TextField.type) {
                // This is the Outlined Text Field for the user to give their Feedbacks
                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { contentDescription = "AppOutlinedTextField" },
                    value = answer.detailedAnswer,
                    appTextFieldType = AppTextFieldValidator(
                        if (question.ansType.isDetailed) {
                            AppTextFieldType.Custom(
                                question.ansType.min, question.ansType.max
                            )
                        } else {
                            AppTextFieldType.Custom()
                        }
                    ),
                    isValidText = {
                        if (question.type != FeedbackQuestionTypes.TextField.type) {
                            if (question.isMandatory && (answer.options[0].isEmpty() || answer.options[0].toIntOrNull() == null)) {
                                isAnswerValid(false)
                            } else {
                                isAnswerValid(it)
                            }
                        } else {
                            isAnswerValid(it)
                        }
                    },
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