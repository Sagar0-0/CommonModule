package fit.asta.health.feature.profile.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.chip.AppAssistChip
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField

@Composable
fun ItemSelectionLayout(
    userProfileState: UserProfileState,
    healthProperties: List<HealthProperties>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        Spacer(modifier = Modifier)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            AppDivider(modifier = Modifier.width(80.dp))
        }
        SearchBar(
            searchQuery = userProfileState.bottomSheetSearchQuery,
            onSearchQueryChange = {
                userProfileState.bottomSheetSearchQuery = it
            },
        )
        ChipRow(userProfileState, healthProperties)
        Spacer(modifier = Modifier)
    }
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
    userProfileState: UserProfileState,
    healthProperties: List<HealthProperties>
) {

    val filteredList = healthProperties.filter {
        it.name.contains(userProfileState.bottomSheetSearchQuery, ignoreCase = true)
    }

    FlowRow(horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
        filteredList.forEach { healthProperties ->
            val isSelected =
                userProfileState.healthBottomSheetTypes[userProfileState.currentHealthBottomSheetTypeIndex].list.contains(
                    healthProperties
                )
            AddChipOnCard(
                textOnChip = healthProperties.name,
                isSelected = isSelected,
                onClick = {
                    if (isSelected) {
                        userProfileState.healthBottomSheetTypes[userProfileState.currentHealthBottomSheetTypeIndex].remove(
                            healthProperties
                        )
                    } else {
                        userProfileState.healthBottomSheetTypes[userProfileState.currentHealthBottomSheetTypeIndex].add(
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