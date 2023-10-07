package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppTextButton

@Composable
fun SubmitButton(
    text: String = "Submit",
    enabled: Boolean,
    onDisable: () -> Unit,
    onClick: (() -> Unit)? = null,
) {
    onClick?.let {
        AppTextButton(
            textToShow = text,
            enabled = enabled,
            onClick = {
                onDisable()
                it()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level3)
        )
    }
}