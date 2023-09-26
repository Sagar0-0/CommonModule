package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.component.AstaValidatedTextField
import fit.asta.health.designsystemx.AstaThemeX
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
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AstaThemeX.spacingX.medium),
        shape = RoundedCornerShape(AstaThemeX.spacingX.small),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(AstaThemeX.spacingX.extraSmall)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AstaThemeX.spacingX.medium)
        ) {

            Text(
                text = qn.qn,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            when (qn.type) {
                2 -> {
                    opts.value = listOf(rating().value.toString())
                    Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
                }

                3, 5 -> {
                    opts.value = qn.opts?.let { listOf(mcqCard(it).value) }
                    Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
                }
            }

            AstaValidatedTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= maxChar) text.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AstaThemeX.boxSizeX.medium),
                placeholder = R.string.write_your_answer_here,
            )
            Text(
                text = "${text.value.length} / $maxChar",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall,
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