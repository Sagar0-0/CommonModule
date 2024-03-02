package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.sunlight.feature.event.OnHomeMenu


@Composable
fun SunlightTopBar(
    title: String,
    onBack: () -> Unit,
    onHelp: (OnHomeMenu) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    fit.asta.health.designsystem.molecular.background.AppTopBar(
        title = title, onBack = onBack, modifier = modifier,
        containerColor = AppTheme.colors.surface,
        titleContentColor = AppTheme.colors.onSurface,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                DropdownMenuItem(text = {
                    BodyTexts.Level2(text = "Edit Conditions")
                }, onClick = {
                    isExpanded = !isExpanded
                    onHelp.invoke(OnHomeMenu.OnSkinConditionEdit)
                },
                    leadingIcon = {
                        AppIcon(imageVector = Icons.Default.Create)
                    })
                DropdownMenuItem(text = {
                    BodyTexts.Level2(text = "Help & Suggestions")
                }, onClick = {
                    isExpanded = !isExpanded
                    onHelp.invoke(OnHomeMenu.OnHelpAndSuggestion)
                },
                    leadingIcon = {
                        AppIcon(imageVector = Icons.Default.Info)
                    })

            }
            AppIconButton(imageVector = Icons.Default.Menu, onClick = {
                isExpanded = !isExpanded
            })

        }
    }
}