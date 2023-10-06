package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.molecular.textfield.AstaValidatedTextField
import fit.asta.health.resources.strings.R

@Composable
fun feedbackTextFieldItem(qn: Qn): MutableState<An> {
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
    val text = rememberSaveable { mutableStateOf("") }
    val opts = remember {
        mutableStateOf(qn.opts)
    }
    val maxChar = 300
    AppCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level3),
        shape = RoundedCornerShape(AppTheme.spacing.level2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.spacing.level3)
        ) {

            AppTexts.TitleMedium(
                text = qn.qn
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            when (qn.type) {
                2 -> {
                    opts.value = listOf(rating().value.toString())
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
                }

                3, 5 -> {
                    opts.value = qn.opts?.let { listOf(mcqCard(it).value) }
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
                }
            }

            AstaValidatedTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= maxChar) text.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.boxSize.level7),
                placeholder = R.string.write_your_answer_here,
            )
            AppTexts.TitleMedium(
                text = "${text.value.length} / $maxChar",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }

    ans.value = An(
        dtlAns = text.value,
        isDet = qn.isDet,
        media = null,
        opts = opts.value,
        qid = qn.qno,
        type = qn.type
    )
    return ans
}