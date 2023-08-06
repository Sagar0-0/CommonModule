@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view.components

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
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppDivider
import fit.asta.health.common.ui.components.generic.AppTextField
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.view.components.AddChipOnCard
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ItemSelectionLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardList: List<HealthProperties>,
    cardIndex: Int,
    composeIndex: ComposeIndex,
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
        ) {
            Spacer(modifier = Modifier.height(spacing.medium))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                AppDivider(lineWidth = 80.dp)
            }
            Spacer(modifier = Modifier.height(spacing.medium))
            SearchBar(onSearchQueryChange = {})
            Spacer(modifier = Modifier.height(spacing.small))
            ChipRow(cardList, viewModel, cardIndex, composeIndex)
            Spacer(modifier = Modifier.height(spacing.medium))
        }
    }
}

@Composable
fun SearchBar(onSearchQueryChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val searchQuery = remember { mutableStateOf("") }
    AppTextField(
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it
            onSearchQueryChange(it)
        },
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
    viewModel: ProfileViewModel,
    cardIndex: Int,
    composeIndex: ComposeIndex,
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(spacing.small)) {
        cardList.forEach { healthProperties ->
            AddChipOnCard(textOnChip = healthProperties.name, onClick = {
                viewModel.onEvent(
                    ProfileEvent.SetSelectedAddItemOption(
                        item = healthProperties, index = cardIndex, composeIndex = composeIndex
                    )
                )
            })
        }
    }
}
