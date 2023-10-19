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
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.Media
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton

@Composable
fun FeedbackQuesScreen(
    feedbackQuesState: FeedbackQuesDTO,
    onSubmit: (ans: List<An>) -> Unit
) {
    val qns = feedbackQuesState.qns
    val isValidList = remember { qns.map { it.isMandatory }.toMutableStateList() }
    var isEnabled = isValidList.none { !it }
    val ansList = remember { qns.map { An() }.toMutableStateList() }

    // Parent Composable which overlaps the Whole Screen
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Your Feedback will help us Card Composable
        item {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            WelcomeCard()
        }

        // Feedback Question Items and all the Feedback Composable are shown here
        itemsIndexed(qns) { idx, qn ->
            FeedbackQuesItem(
                qn = qn,
                ans = ansList[idx],
                isValid = isValidList[idx],
                updatedAns = { newAns ->
                    ansList[idx] = newAns
                },
                onValidityChange = { newValidity ->
                    isValidList[idx] = newValidity
                }
            )
        }

        // Submit Button
        item {
            val context = LocalContext.current
            AppFilledButton(
                textToShow = "Submit",
                modifier = Modifier.fillMaxWidth(),
                enabled = isEnabled,
                shape = AppTheme.shape.level1
            ) {
                isEnabled = false
                Log.e("ANS", "SessionFeedback: ${ansList.toList()}")
                ansList.forEachIndexed { idx, ans ->
                    val medias = ans.mediaUri.map { uri ->
                        Media(
                            name = DocumentFile.fromSingleUri(context, uri)?.name ?: "",
                            url = "",
                            localUri = uri
                        )
                    }
                    ansList[idx] = ansList[idx].copy(media = medias)
                }
                onSubmit(ansList.toList())
            }
        }
    }
}