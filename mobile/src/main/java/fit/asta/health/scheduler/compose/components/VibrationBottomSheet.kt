package fit.asta.health.scheduler.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.util.VibrationPattern

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
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
            Text(
                text = text,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { onSave(value) }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppButtons.AppOutlinedButton(
                onClick = { value = VibrationPattern.Short },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (value == VibrationPattern.Short) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onPrimary
                )
            ) {
                AppTexts.TitleMedium(text = "Short")
            }
            AppButtons.AppOutlinedButton(
                onClick = { value = VibrationPattern.Long },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (value == VibrationPattern.Long) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onPrimary
                )
            ) {
                AppTexts.TitleMedium(text = "Long")
            }
            AppButtons.AppOutlinedButton(
                onClick = { value = VibrationPattern.Intermittent },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (value == VibrationPattern.Intermittent) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onPrimary
                )
            ) {
                AppTexts.TitleMedium(text = "Intermittent")
            }
        }
    }

}