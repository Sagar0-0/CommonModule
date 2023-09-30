package fit.asta.health.feature.address.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.google.android.libraries.places.api.Places
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.address.remote.modal.MyAddress
import fit.asta.health.data.address.remote.modal.SearchResponse
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchBottomSheet(
    modifier: Modifier = Modifier,
    type: SearchSheetType,
    searchResponseState: UiState<SearchResponse>,
    onUiEvent: (SearchSheetUiEvent) -> Unit
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    val textFieldFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { textFieldFocus.requestFocus() }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val closeSheet = {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onUiEvent(SearchSheetUiEvent.Close)
                onUiEvent(SearchSheetUiEvent.ClearSearchResponse)
            }
        }
    }
    LaunchedEffect(Unit) {
        Places.initialize(context.applicationContext, context.getString(R.string.MAPS_API_KEY))
    }

    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        dragHandle = null,
        onDismissRequest = { closeSheet() },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                maxLines = 1,
                singleLine = true,
                readOnly = false,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(textFieldFocus)
                    .padding(AppTheme.spacing.medium),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (it.length > 2) onUiEvent(SearchSheetUiEvent.Search(it))
                },
                placeholder = {
                    Text(
                        text = R.string.search_for_area_street.toStringFromResId(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                },
                trailingIcon = {
                    if (searchQuery.length > 2) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Search Button",
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = AppTheme.customSize.extraMedium
                                )
                            ) {
                                searchQuery = ""
                            }
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.primary,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.primary
                )
            )

            when (searchResponseState) {
                is UiState.Success -> {
                    var results =
                        searchResponseState.data.results
                    if (results.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(AppTheme.spacing.small),
                            text = R.string.no_result_for.toStringFromResId() + "\"$searchQuery\"",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        LazyColumn {
                            items(results) {
                                AppButtons.AppTextButton(
                                    onClick = {
                                        searchQuery = ""
                                        val myAddressItem = MyAddress(
                                            lat = it.geometry.location.lat,
                                            lon = it.geometry.location.lng,
                                        )
                                        closeSheet()
                                        onUiEvent(SearchSheetUiEvent.OnResultClick(myAddressItem))
                                        results = listOf()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(AppTheme.spacing.extraSmall)
                                ) {
                                    AppDefServerImg(
                                        model = it.icon,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = AppTheme.spacing.medium)
                                    )
                                    AppTexts.BodyLarge(text = it.name)
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                UiState.Loading -> {
                    Box(
                        modifier = Modifier.padding(top = AppTheme.spacing.large),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                is UiState.ErrorMessage -> {
                    Text(text = searchResponseState.resId.toStringFromResId())
                }

                UiState.Idle -> {
                    Text(text = R.string.your_search_results_will_appear_here.toStringFromResId())
                }

                else -> {}
            }
        }
    }


}