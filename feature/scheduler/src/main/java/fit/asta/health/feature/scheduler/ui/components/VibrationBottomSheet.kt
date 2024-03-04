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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.button.AppIconButton
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
            AppIconButton(imageVector = Icons.Default.Close, onClick = onNavigateBack)
            TitleTexts.Level2(
                text = text,
                color = AppTheme.colors.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            AppIconButton(
                modifier = Modifier.testTag("done"),
                imageVector = Icons.Default.Check
            ) { onSave(value) }
        }
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            ButtonWithColor(color = if (value == VibrationPattern.Short) AppTheme.colors.primary
            else Color(0xFF8D9199), text = stringResource(id = StringR.string.shortPattern)) {
                value = VibrationPattern.Short
            }
            ButtonWithColor(color = if (value == VibrationPattern.Long) AppTheme.colors.primary
            else Color(0xFF8D9199), text = stringResource(id = StringR.string.longPattern)) {
                value = VibrationPattern.Long
            }
            ButtonWithColor(color = if (value == VibrationPattern.Intermittent) AppTheme.colors.primary
            else Color(0xFF8D9199), text = stringResource(StringR.string.intermittent)) {
                value = VibrationPattern.Intermittent
            }
//            AppOutlinedButton(
//                textToShow = stringResource(id = StringR.string.shortPattern),
////                colors = ButtonDefaults.outlinedButtonColors(
////                    containerColor = if (value == VibrationPattern.Short) AppTheme.colors.primary
////                    else AppTheme.colors.onPrimary
////                )
//            ) { value = VibrationPattern.Short }
//            AppOutlinedButton(
//                textToShow = stringResource(id = StringR.string.longPattern),
////                colors = ButtonDefaults.outlinedButtonColors(
////                    containerColor = if (value == VibrationPattern.Long) AppTheme.colors.primary
////                    else AppTheme.colors.onPrimary
////                )
//            ) { value = VibrationPattern.Long }
//            AppOutlinedButton(
//                textToShow = stringResource(StringR.string.intermittent),
//                colors = ButtonDefaults.outlinedButtonColors(
//                    containerColor = if (value == VibrationPattern.Intermittent) AppTheme.colors.primary
//                    else AppTheme.colors.onPrimary
//                )
//            ) { value = VibrationPattern.Intermittent }
        }
    }

}