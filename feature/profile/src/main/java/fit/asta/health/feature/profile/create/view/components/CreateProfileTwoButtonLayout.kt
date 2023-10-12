package fit.asta.health.feature.profile.create.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun CreateProfileTwoButtonLayout(
    eventPrevious: () -> Unit,
    eventNext: () -> Unit,
    titleButton2: String = "Next",
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        AppOutlinedButton(
            onClick = eventPrevious,
            modifier = Modifier.weight(1f),
            border = BorderStroke(width = 2.dp, color = AppTheme.colors.onSurface)
        ) {
            CaptionTexts.Level3(
                text = "Previous",
                color = AppTheme.colors.onSurface
            )
        }
        AppFilledButton(
            onClick = eventNext,
            modifier = Modifier.weight(1f),
            shape = CircleShape
        ) {
            CaptionTexts.Level3(
                text = titleButton2,
                color = AppTheme.colors.onPrimary
            )
        }
    }
}