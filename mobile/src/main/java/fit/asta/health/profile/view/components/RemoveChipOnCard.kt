package fit.asta.health.profile.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fit.asta.health.common.ui.components.generic.AppChips
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts

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
fun AddChipOnCard(
    textOnChip: String,
    onClick: () -> Unit,
) {
    val colors =
        rememberAssistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    AppChips.AppAssistChip(onClick = onClick, label = {
        AppTexts.LabelSmall(
            text = textOnChip, color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, trailingIcon = {
        AppDefaultIcon(
            imageVector = Icons.Rounded.AddCircle,
            contentDescription = "Add Items",
            tint = MaterialTheme.colorScheme.primary
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

@Composable
fun rememberAssistChipColors(
    containerColor: Color? = null,
    disabledContainerColor: Color? = null,
): ChipColors {
    return AssistChipDefaults.assistChipColors(
        containerColor = containerColor ?: MaterialTheme.colorScheme.primaryContainer,
        disabledContainerColor = disabledContainerColor
            ?: MaterialTheme.colorScheme.primaryContainer
    )
}