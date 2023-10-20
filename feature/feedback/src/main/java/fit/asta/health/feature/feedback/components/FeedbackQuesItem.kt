package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.feedback.remote.modal.An
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.molecular.UploadFiles


const val UPLOAD_LIMIT = 5

@Composable
fun FeedbackQuesItem(
    qn: Qn,
    ans: An,
    isValid: Boolean,
    updatedAns: (An) -> Unit,
    onValidityChange: (Boolean) -> Unit
) {
    if (qn.type == 1) {
        UploadFiles(
            modifier = Modifier.fillMaxWidth(),
            uriList = ans.mediaUri.toList(),
            isValid = isValid,
            errorMessage = if(ans.mediaUri.isEmpty()) "You should upload least 1 file" else "You cannot upload more than $UPLOAD_LIMIT files",
            onItemAdded = {
                val newSet = ans.mediaUri.plus(it)
                updatedAns(ans.copy(mediaUri = newSet))
                if (qn.isMandatory && newSet.isEmpty())
                    onValidityChange(false)
                else
                    onValidityChange(newSet.size <= UPLOAD_LIMIT)
            },
            onItemDeleted = { uri ->
                val newSet = ans.mediaUri.minus(uri)
                updatedAns(ans.copy(mediaUri = newSet))
                if (qn.isMandatory && newSet.isEmpty())
                    onValidityChange(false)
                else
                    onValidityChange(newSet.size <= UPLOAD_LIMIT)
            }
        )
    } else {
        FeedbackTextFieldItem(
            qn = qn,
            ans = ans,
            updatedAns = updatedAns,
            isValid = onValidityChange
        )
    }
}