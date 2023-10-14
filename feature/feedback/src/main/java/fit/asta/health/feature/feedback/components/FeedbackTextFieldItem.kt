package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun FeedbackTextFieldItem(qn: Qn, updatedAns: (An) -> Unit, isValid: (Boolean) -> Unit) {
    val text = rememberSaveable { mutableStateOf("") }
    val opts = remember {
        mutableStateOf(qn.opts)
    }
    val maxChar = qn.ansType.max
    AppCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level0),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.spacing.level1)
        ) {

            TitleTexts.Level2(
                text = qn.qn
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
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

                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
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
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                }
            }
        }

        AppTextField(
            minLines = 6,
            appTextFieldType = AppTextFieldValidator(
                AppTextFieldType.Custom(
                    qn.ansType.min,
                    maxChar
                )
            ),
            value = text.value,
            onValueChange = {
                if (it.length <= maxChar) text.value = it
                isValid(it.length in qn.ansType.min..maxChar)
                updatedAns(
                    An(
                        dtlAns = text.value,
                        media = null,
                        opts = opts.value,
                        qid = qn.qno,
                        type = qn.type
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.boxSize.level2),
        )
        TitleTexts.Level2(
            text = "${text.value.length} / $maxChar",
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

    }
}