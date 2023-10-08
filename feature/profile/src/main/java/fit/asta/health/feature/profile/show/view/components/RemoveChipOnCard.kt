package fit.asta.health.feature.profile.show.view.components

import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.chip.AppAssistChip
import fit.asta.health.feature.profile.create.view.components.rememberAssistChipColors


@Composable
fun DisabledChipForList(
    textOnChip: String,
) {
    val colors = rememberAssistChipColors(disabledContainerColor = AppTheme.colors.primaryContainer)
    AppAssistChip(onClick = { /*TODO*/ }, textToShow = textOnChip, colors = colors, enabled = false)
}

