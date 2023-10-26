package fit.asta.health.feature.settings.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppRadioButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.texts.TitleTexts


@Composable
internal fun SettingsPreferenceDialog(
    titleId: Int,
    imageVector: ImageVector,
    theme: String,
    entries: Array<String>,
    values: Array<String>,
    onValueChange: (String) -> Unit
) {
    val title = stringResource(id = titleId)
    val idx = values.indexOf(theme.ifEmpty { "system" })
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(idx) }

    SettingsCardItem(icon = imageVector, text = title) {
        showDialog = true
    }

    if (showDialog) {
        AppDialog(onDismissRequest = { showDialog = false }) {
            AppCard(
                shape = AppTheme.shape.level2,
                modifier = Modifier.fillMaxWidth()
            ) {
                entries.forEachIndexed { index, entry ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AppRadioButton(
                            selected = selectedIndex == index
                        ) {
                            showDialog = false
                            selectedIndex = index
                            onValueChange(values[selectedIndex])
                        }
                        TitleTexts.Level2(text = entry)
                    }
                }
            }
        }
    }
}