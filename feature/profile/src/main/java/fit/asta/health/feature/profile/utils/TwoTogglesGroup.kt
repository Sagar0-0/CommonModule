package fit.asta.health.feature.profile.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.BooleanIntTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun TwoTogglesGroup(
    selectionTypeText: String?,
    selectedOption: BooleanInt,
    onStateChange: (BooleanInt) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        if (!selectionTypeText.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2)
            ) {
                TitleTexts.Level3(
                    text = selectionTypeText, color = AppTheme.colors.onTertiaryContainer
                )
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BooleanIntTypes.entries.forEach { option ->
                Row(
                    verticalAlignment = CenterVertically, modifier = Modifier.weight(1f)
                ) {
                    AppRadioButton(
                        selected = selectedOption == option.value
                    ) { onStateChange(option.value) }
                    CaptionTexts.Level4(
                        text = option.title,
                        color = AppTheme.colors.onPrimaryContainer
                    )
                }
            }
        }
    }
}

