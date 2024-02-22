package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton

@Composable
fun PageNavigationButtons(
    onNext: (() -> Unit)? = null,
    onPrevious: (() -> Unit)? = null,
    onSubmitClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        onPrevious?.let {
            AppFilledButton(
                textToShow = "Previous",
                onClick = it,
                modifier = Modifier.weight(1f),
            )
        }
        AppFilledButton(
            textToShow = if (onSubmitClick != null) "Submit" else "Next",
            onClick = { if (onSubmitClick != null) onSubmitClick.invoke() else onNext?.invoke() },
            modifier = Modifier.weight(1f),
        )
    }
}