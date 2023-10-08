package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.scheduler.util.VibrationPattern
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VibrationBottomSheetLayout(
    text: String,
    onNavigateBack: () -> Unit,
    onSave: (VibrationPattern) -> Unit = {}
) {

    var value by remember { mutableStateOf<VibrationPattern>(VibrationPattern.Short) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppIconButton(onClick = onNavigateBack, imageVector = Icons.Default.Close)
            TitleTexts.Level2(
                text = text,
                color = AppTheme.colors.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            AppIconButton(imageVector = Icons.Default.Check, onClick = { onSave(value) })
        }
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppOutlinedButton(
                onClick = { value = VibrationPattern.Short },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (value == VibrationPattern.Short) AppTheme.colors.primary
                    else AppTheme.colors.onPrimary
                ),
                textToShow = stringResource(id = StringR.string.shortPattern)
            )
            AppOutlinedButton(
                onClick = { value = VibrationPattern.Long },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (value == VibrationPattern.Long) AppTheme.colors.primary
                    else AppTheme.colors.onPrimary
                ),
                textToShow = stringResource(id = StringR.string.longPattern)
            )
            AppOutlinedButton(
                onClick = { value = VibrationPattern.Intermittent },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (value == VibrationPattern.Intermittent) AppTheme.colors.primary
                    else AppTheme.colors.onPrimary
                ),
                textToShow = stringResource(StringR.string.intermittent)
            )
        }
    }

}