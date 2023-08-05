package fit.asta.health.common.maps.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.maps.modal.AddressScreen
import fit.asta.health.common.maps.modal.AddressesResponse
import fit.asta.health.common.maps.modal.SearchResponse
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.customSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    searchResponseState: ResponseState<SearchResponse>,
    onResultClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClose: () -> Unit
) {
    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    val textFieldFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { textFieldFocus.requestFocus() }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val closeSheet = {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                onClose()
            }
        }
    }

    ModalBottomSheet(
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
                    .padding(top = spacing.small, start = spacing.small, end = spacing.small),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (it.length > 2) onSearch(searchQuery)
                },
                placeholder = {
                    Text(
                        text = "Search for area,street name..",
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
                                    radius = customSize.extraMedium
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


            Text(
                modifier = Modifier.padding(spacing.small),
                text = "SEARCH RESULTS",
                style = MaterialTheme.typography.titleLarge
            )

            when (searchResponseState) {
                is ResponseState.Success -> {
                    val results =
                        searchResponseState.data.results
                    if (results.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(spacing.small),
                            text = "No result for \"$searchQuery\"",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    } else {
                        LazyColumn {
                            items(results) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            searchQuery = ""
                                            val myAddressItem = AddressesResponse.MyAddress(
                                                selected = false,
                                                area = "",
                                                block = "",
                                                hn = "",
                                                id = "",
                                                lat = it.geometry.location.lat,
                                                loc = "",
                                                lon = it.geometry.location.lng,
                                                name = "",
                                                nearby = "",
                                                ph = "",
                                                pin = "",
                                                sub = "",
                                                uid = ""
                                            )
                                            val gson: Gson = GsonBuilder().create()
                                            val addJson = gson
                                                .toJson(myAddressItem)
                                                .replace("/", "|")

                                            closeSheet()
                                            onResultClick("${AddressScreen.Map.route}/$addJson")
                                        }
                                        .padding(spacing.medium),
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                is ResponseState.Loading -> {
                    Box(
                        modifier = Modifier.padding(top = spacing.large),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingAnimation()
                    }
                }

                is ResponseState.NoInternet -> {
                    AppErrorScreen()
                }

                is ResponseState.Error -> {
                    AppErrorScreen(desc = "Some internal error occurred! We are fixing it soon!")
                }

                else -> {}
            }

        }
    }

}