package fit.asta.health.feedback.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.boxSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.feedback.data.remote.modal.An
import fit.asta.health.feedback.data.remote.modal.Qn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun feedbackTextFieldItem(qn: Qn): MutableState<An> {
    val ans = remember { mutableStateOf(An(null, false, null, null, 0, 0)) }
    val text = rememberSaveable { mutableStateOf("") }
    val opts = remember {
        mutableStateOf(qn.opts)
    }
    val maxChar = 300
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing.medium),
        shape = RoundedCornerShape(spacing.small),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(spacing.extraSmall)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.medium)
        ) {

            Text(
                text = qn.qn,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            when (qn.type) {
                2 -> {
                    opts.value = listOf(rating().value.toString())
                    Spacer(modifier = Modifier.height(spacing.medium))
                }

                3,5 -> {
                    opts.value = qn.opts?.let { listOf(mcqCard(it).value) }
                    Spacer(modifier = Modifier.height(spacing.medium))
                }
            }

            OutlinedTextField(
                value = text.value,
                onValueChange = {
                    if (it.length <= maxChar) text.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxSize.medium),
                placeholder = {
                    Text(
                        text = "Write your answer here...",
                        lineHeight = 19.6.sp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    /*focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background*/
                ),
                shape = MaterialTheme.shapes.medium
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