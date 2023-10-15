package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
    updatedAns: (An) -> Unit,
    isValid: (Boolean) -> Unit
) {

    val text = rememberSaveable { mutableStateOf("") }
    val opts = remember { mutableStateOf(qn.opts) }
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
                    Rating {
                        opts.value = listOf(it.toString())
                        updatedAns(
                            An(
                                dtlAns = text.value,
                                media = null,
                                opts = opts.value,
                                qid = qn.qno,
                                type = qn.type
                            )
                        )
                    }
                }

                3, 5 -> {
                    qn.opts?.let {
                        McqCard(
                            list = it,
                            updatedAns = { ans ->
                                opts.value = listOf(ans)
                                updatedAns(
                                    An(
                                        dtlAns = text.value,
                                        media = null,
                                        opts = opts.value,
                                        qid = qn.qno,
                                        type = qn.type
                                    )
                                )
                            }
                        )
                    }
                }
            }

            // This is the Outlined Text Field for the user to give their Feedbacks
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text.value,
                appTextFieldType = AppTextFieldValidator(
                    AppTextFieldType.Custom(
                        qn.ansType.min,
                        maxChar
                    )
                ),
                isValidText = isValid,
                minLines = 4
            ) {
                text.value = it
                updatedAns(
                    An(
                        dtlAns = text.value,
                        media = null,
                        opts = opts.value,
                        qid = qn.qno,
                        type = qn.type
                    )
                )
            }
        }
    }
}