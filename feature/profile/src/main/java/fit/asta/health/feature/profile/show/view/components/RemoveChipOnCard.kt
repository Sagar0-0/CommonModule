package fit.asta.health.feature.profile.show.view.components

import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.chip.AppAssistChip
import fit.asta.health.feature.profile.profile.ui.rememberAssistChipColors


@Composable
fun DisabledChipForList(
    textOnChip: String,
) {
    val colors = rememberAssistChipColors(disabledContainerColor = AppTheme.colors.primaryContainer)
    AppAssistChip(textToShow = textOnChip, enabled = false, colors = colors) { /*TODO*/ }
}

