package fit.asta.health.feature.profile.show.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.components.generic.AppChips
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.feature.profile.create.view.components.rememberAssistChipColors

@Composable
fun RemoveChipOnCard(
    textOnChip: String,
    onClick: () -> Unit = {},
) {
    val colors =
        rememberAssistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    AppChips.AppAssistChip(onClick = onClick, label = {
        AppTexts.LabelSmall(
            text = textOnChip, color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, trailingIcon = {
        AppDefaultIcon(
            imageVector = Icons.Rounded.RemoveCircle,
            contentDescription = "Remove",
            tint = MaterialTheme.colorScheme.error
        )
    }, colors = colors
    )
}

@Composable
fun DisabledChipForList(
    textOnChip: String,
) {
    val colors =
        rememberAssistChipColors(disabledContainerColor = MaterialTheme.colorScheme.primaryContainer)
    AppChips.AppAssistChip(
        onClick = {},
        label = { AppTexts.LabelSmall(text = textOnChip) },
        colors = colors,
        enabled = false
    )
}

