package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppTexts

@Composable
fun SubmitButton(
    text: String = "Submit",
    enabled: Boolean,
    onDisable: () -> Unit,
    onClick: (() -> Unit)? = null,
) {
    onClick?.let {
        AppButtons.AppTextButton(
            enabled = enabled,
            onClick = {
                onDisable()
                it()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level3),
            shape = RoundedCornerShape(AppTheme.spacing.level2),
        ) {
            AppTexts.TitleMedium(
                text = text,
                color = AppTheme.colors.onBackground,
            )
        }
    }
}