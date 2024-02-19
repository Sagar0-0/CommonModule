package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun AppDropDown(
    modifier: Modifier = Modifier,
    title: String? = null,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    selectedTextColor: Color = AppTheme.colors.onTertiaryContainer,
    options: List<String>,
    titleTextColor: Color = AppTheme.colors.onTertiaryContainer,
    showAsterisk: Boolean = false,
    showTitle: Boolean = true,
    backgroundColor: Color = AppTheme.colors.tertiaryContainer,
    arrowColor: Color = AppTheme.colors.onTertiaryContainer,
    backgroundScale: Dp = 16.dp,
    selectedItemColor: Color = AppTheme.colors.onTertiaryContainer,
    unselectedItemColor: Color = Color.Gray,
    onSelectOption: ((Int, String) -> Unit)? = null
) {
    val spacing = AppTheme.spacing
    val shape = AppTheme.shape.level2
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource()
            ) { expanded.value = true },
        verticalArrangement = Arrangement.spacedBy(spacing.level2)
    ) {

        if (showTitle) {
            Row {
                BodyTexts.Level1(
                    text = title ?: "",
                    color = titleTextColor,
                )
                if (showAsterisk) {
                    BodyTexts.Level1(
                        text = "*",
                        color = Color.Red
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor, shape = shape)
                .padding(backgroundScale),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyTexts.Level1(
                text = selected.value.ifEmpty { title ?: "" },
                color = selectedTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )

            AppIcon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = arrowColor,
            )
        }

        AppDropDownContent(
            modifier = Modifier
                .padding(spacing.level2),
            expanded = expanded,
            selected = selected,
            items = options,
            selectedItemColor = selectedItemColor,
            unselectedItemColor = unselectedItemColor,
            onSelectOption = { index, option ->
                focusManager.clearFocus()
                selected.value = (option)
                onSelectOption?.invoke(index, option)
            }
        )
    }
}

@Composable
fun AppDropDownContent(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    selected: MutableState<String>,
    items: List<String>,
    onSelectOption: (Int, String) -> Unit,
    selectedItemColor: Color = AppTheme.colors.onTertiaryContainer,
    unselectedItemColor: Color = Color.Gray
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = (false) },
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                onClick = {
                    expanded.value = (false)
                    selected.value = item
                    onSelectOption(index, item)
                },
                contentPadding = PaddingValues(8.dp)
            ) {
                CaptionTexts.Level1(
                    text = item,
                    modifier = Modifier.fillMaxWidth(),
                    color = if (selected.value == item) selectedItemColor else unselectedItemColor,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}