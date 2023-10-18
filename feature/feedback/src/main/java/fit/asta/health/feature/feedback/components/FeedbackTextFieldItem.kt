package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun FeedbackTextFieldItem(
    qn: Qn,
    ans: An,
    updatedAns: (An) -> Unit,
    isValid: (Boolean) -> Unit,
) {
    val maxChar = qn.ansType.max

    AppElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // This is the Question of this Card
            TitleTexts.Level3(text = qn.qn)

            // This is either the Rating Stars or the Radio Buttons
            when (qn.type) {
                2 -> {
                    Rating(
                        if (ans.opts[0].toIntOrNull() == null) {
                            updatedAns(ans.copy(opts = listOf("0")))
                            0
                        } else {
                            ans.opts[0].toInt()
                        }
                    ) {
                        updatedAns(
                            ans.copy(opts = listOf(it.toString()))
                        )
                    }
                }

                3, 5 -> {
                    McqCard(
                        ans = ans.opts[0],
                        list = qn.opts,
                        updatedAns = { newAns ->
                            updatedAns(
                                ans.copy(opts = listOf(newAns))
                            )
                        }
                    )
                }
            }

            // This is the Outlined Text Field for the user to give their Feedbacks
            AppOutlinedTextField(modifier = Modifier.fillMaxWidth(),
                value = ans.dtlAns,
                appTextFieldType = AppTextFieldValidator(
                    AppTextFieldType.Custom(
                        qn.ansType.min, maxChar
                    )
                ),
                isValidText = isValid,
                minLines = 4,
                onValueChange = { newText ->
                    updatedAns(
                        ans.copy(dtlAns = newText)
                    )
                }
            )
        }
    }
}