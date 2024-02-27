@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.molecular

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme

@Composable
fun AppSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    shape: Shape = AppTheme.shape.level1,
    colors: SearchBarColors = SearchBarDefaults.colors(
        containerColor = AppTheme.colors.secondaryContainer,
        dividerColor = AppTheme.colors.tertiary,
        inputFieldColors = TextFieldDefaults.colors()
    ),
    tonalElevation: Dp = AppTheme.spacing.level0,
    windowInsets: WindowInsets = SearchBarDefaults.windowInsets,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable() (ColumnScope.() -> Unit)
) {

    SearchBar(
        query = query,
        onQueryChange = {
            onQueryChange(it)
        },
        onSearch = {
            onSearch(it)
        },
        active = active,
        onActiveChange = {
            onActiveChange(it)
        },
        enabled = enabled,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = shape,
        modifier = modifier,
        colors = colors,
        tonalElevation = tonalElevation,
        windowInsets = windowInsets,
        interactionSource = interactionSource,
        content = content
    )
}