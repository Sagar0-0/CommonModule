@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.feature.create.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.components.generic.AppChips
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppDivider
import fit.asta.health.designsystem.components.generic.AppTextField
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.profile.feature.create.vm.ComposeIndex
import fit.asta.health.profile.feature.create.vm.ProfileEvent
import fit.asta.health.profile.feature.show.vm.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ItemSelectionLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardList: List<HealthProperties>,
    cardList2: SnapshotStateList<HealthProperties>?,
    cardIndex: Int,
    composeIndex: ComposeIndex,
    searchQuery: MutableState<String>,
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AstaThemeX.spacingX.medium)
        ) {
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                AppDivider(lineWidth = 80.dp)
            }
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            SearchBar(onSearchQueryChange = { searchQuery.value = it }, searchQuery)
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.small))
            ChipRow(cardList, cardList2, viewModel, cardIndex, composeIndex, searchQuery.value)
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
        }
    }
}

@Composable
fun SearchBar(
    onSearchQueryChange: (String) -> Unit,
    searchQuery: MutableState<String>,
) {
    val focusManager = LocalFocusManager.current
    AppTextField(
        value = searchQuery.value,
        onValueChange = { onSearchQueryChange(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        placeholder = { AppTexts.LabelSmall(text = "Search") },
        leadingIcon = {
            AppDefaultIcon(imageVector = Icons.Rounded.Search, contentDescription = "Search Icon")
        },
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun ChipRow(
    cardList: List<HealthProperties>,
    cardList2: SnapshotStateList<HealthProperties>?,
    viewModel: ProfileViewModel,
    cardIndex: Int,
    composeIndex: ComposeIndex,
    searchQuery: String,
) {

    val filteredList = cardList.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    FlowRow(horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small)) {
        filteredList.forEach { healthProperties ->
            val isSelected = cardList2?.contains(healthProperties) == true
            AddChipOnCard(textOnChip = healthProperties.name, isSelected = isSelected, onClick = {
                if (isSelected) {
                    viewModel.onEvent(
                        ProfileEvent.SetSelectedRemoveItemOption(
                            item = healthProperties, index = cardIndex, composeIndex = composeIndex
                        )
                    )
                } else {
                    viewModel.onEvent(
                        ProfileEvent.SetSelectedAddItemOption(
                            item = healthProperties, index = cardIndex, composeIndex = composeIndex
                        )
                    )
                }
            })
        }
    }
}

@Composable
fun AddChipOnCard(
    textOnChip: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    val colors =
        rememberAssistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    AppChips.AppAssistChip(onClick = onClick, label = {
        AppTexts.LabelSmall(
            text = textOnChip, color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, trailingIcon = {
        AppDefaultIcon(
            imageVector = if (isSelected) Icons.Rounded.RemoveCircle else Icons.Rounded.AddCircle,
            contentDescription = if (isSelected) "Remove Items" else "Add Items",
            tint = if (isSelected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
    }, colors = colors
    )
}


@Composable
fun rememberAssistChipColors(
    containerColor: Color? = null,
    disabledContainerColor: Color? = null,
): ChipColors {
    return AssistChipDefaults.assistChipColors(
        containerColor = containerColor ?: MaterialTheme.colorScheme.primaryContainer,
        disabledContainerColor = disabledContainerColor
            ?: MaterialTheme.colorScheme.primaryContainer
    )
}