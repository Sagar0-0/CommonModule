package fit.asta.health.feature.settings.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(idx) }

    SettingsCardItem(
        icon = imageVector,
        text = title
    ) {
        showDialog = true
    }

    if (showDialog) {
        AppDialog(onDismissRequest = { showDialog = false }) {
            AppCard(
                shape = AppTheme.shape.level2,
                modifier = Modifier.fillMaxWidth()
            ) {
                entries.forEachIndexed { index, entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedIndex = index
                                onValueChange(values[selectedIndex])
                                showDialog = false
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppRadioButton(
                            selected = selectedIndex == index
                        )
                        TitleTexts.Level2(text = entry)
                    }
                }
            }
        }
    }
}