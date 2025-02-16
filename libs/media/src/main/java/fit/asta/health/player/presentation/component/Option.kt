package fit.asta.health.player.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.button.AppSwitch
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun <T> Option(
    name: String,
    values: List<T>,
    currentValue: T,
    formatter: (T) -> String = { it.toString() },
    onValueChanged: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TitleTexts.Level2(text = name)
        Row {
            CaptionTexts.Level3(text = formatter(currentValue))
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                values.forEach { value ->
                    DropdownMenuItem(
                        text = {
                            CaptionTexts.Level3(text = formatter(value))
                        },
                        onClick = {
                            onValueChanged(value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BooleanOption(
    name: String,
    value: Boolean,
    enabled: Boolean = true,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = value,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onValueChange
            )
            .padding(horizontal = 16.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTexts.Level2(
            text = name,
            modifier = Modifier.weight(1f)
        )
        AppSwitch(checked = value, enabled = enabled, onCheckedChange = onValueChange)
    }
}
