package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.chip.AppAssistChip
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField

@Composable
fun ColumnScope.PropertiesSearchSheet(
    userProperties: List<UserProperties>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isItemSelected: (UserProperties) -> Boolean,
    onAdd: (UserProperties) -> Unit,
    onRemove: (UserProperties) -> Unit
) {
    SearchBar(
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
    )
    ChipRow(
        userProperties = userProperties,
        searchQuery = searchQuery,
        isItemSelected = isItemSelected,
        onAdd = onAdd,
        onRemove = onRemove
    )
}

@Composable
fun SearchBar(
    onSearchQueryChange: (String) -> Unit,
    searchQuery: String,
) {
    val focusManager = LocalFocusManager.current
    AppOutlinedTextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        label = "Search",
        leadingIcon = Icons.Rounded.Search,
        leadingIconDes = "Search Icon"
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipRow(
    userProperties: List<UserProperties>,
    searchQuery: String,
    isItemSelected: (UserProperties) -> Boolean,
    onAdd: (UserProperties) -> Unit,
    onRemove: (UserProperties) -> Unit
) {

    val filteredList = userProperties.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    FlowRow(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
        filteredList.forEach { healthProperties ->
            val isSelected = isItemSelected(healthProperties)
            AddChipOnCard(
                textOnChip = healthProperties.name,
                isSelected = isSelected,
                onClick = {
                    if (isSelected) {
                        onRemove(
                            healthProperties
                        )
                    } else {
                        onAdd(
                            healthProperties
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun AddChipOnCard(
    textOnChip: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    val colors = rememberAssistChipColors(containerColor = AppTheme.colors.primaryContainer)

    AppAssistChip(
        textToShow = textOnChip,
        trailingIcon = if (isSelected) Icons.Rounded.RemoveCircle else Icons.Rounded.AddCircle,
        iconTint = if (isSelected) AppTheme.colors.error else AppTheme.colors.primary,
        colors = colors,
        onClick = onClick
    )
}


@Composable
fun rememberAssistChipColors(
    containerColor: Color? = null,
    disabledContainerColor: Color? = null,
): ChipColors {
    return AssistChipDefaults.assistChipColors(
        containerColor = containerColor ?: AppTheme.colors.primaryContainer,
        disabledContainerColor = disabledContainerColor ?: AppTheme.colors.primaryContainer
    )
}