package fit.asta.health.feature.scheduler.ui.screen.spotify

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.spotify.model.search.SpotifySearchModel
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.feature.scheduler.ui.components.SearchBarUI
import fit.asta.health.feature.scheduler.ui.components.SpotifyMusicItem
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.ToneUiState
import fit.asta.health.resources.strings.R as StringR

@Composable
fun SpotifySearchScreen(
    searchResult: UiState<SpotifySearchModel>,
    setEvent: (SpotifyUiEvent) -> Unit
) {

    // Context and Activity of the Function
    val context = LocalContext.current

    // This is the Input of the User
    val userSearchInput = remember { mutableStateOf("") }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
    ) {
        val toast = stringResource(StringR.string.search_bar_empty)
        // This function Draws the Search Bar to the Screen
        SearchBarUI(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level3),
            userInput = userSearchInput.value,
            onUserInputChange = {
                userSearchInput.value = it
            }
        ) {

            // Checking if both the user Input and the filtered type is not empty
            if (userSearchInput.value.isNotEmpty()) {

                // Setting the Query parameters in the ViewModel
                setEvent(SpotifyUiEvent.NetworkIO.SetSearchQueriesAndVariables(userSearchInput.value))
            } else {

                // No Search Query
                Toast.makeText(
                    context,
                    toast,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Handling the States of the Search Result
        when (searchResult) {

            // Idle State
            is UiState.Idle -> {}

            // Loading State
            is UiState.Loading -> {
                LoadingAnimation(modifier = Modifier.fillMaxSize())
            }

            // Success State
            is UiState.Success -> {

                // Handling the Tracks UI here
                searchResult.data.tracks.trackList.let { trackList ->

                    // Tracks
                    HeadingTexts.Level1(
                        text = stringResource(StringR.string.tracks),
                        modifier = Modifier.padding(AppTheme.spacing.level3)
                    )

                    // Showing the Tracks List UI inside a Lazy Row
                    LazyColumn(
                        modifier = Modifier
                            .height(LocalConfiguration.current.screenHeightDp.dp)
                            .padding(AppTheme.spacing.level3)
                            .width(LocalConfiguration.current.screenWidthDp.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
                    ) {
                        items(trackList.size) {
                            val currentItem = trackList[it]

                            val textToShow = currentItem.artists
                                .map { artist -> artist.name }
                                .toString()
                                .filterNot { char ->
                                    char == '[' || char == ']'
                                }.trim()

                            // This function draws the Track UI
                            SpotifyMusicItem(
                                imageUri = currentItem.album.images.firstOrNull()?.url,
                                name = currentItem.name,
                                secondaryText = textToShow,
                                onCardClick = {
                                    setEvent(
                                        SpotifyUiEvent.HelperEvent.PlaySpotifySong(
                                            currentItem.uri
                                        )
                                    )
                                }
                            ) {
                                setEvent(
                                    SpotifyUiEvent.HelperEvent.OnApplyClick(
                                        ToneUiState(
                                            name = currentItem.name,
                                            type = 1,
                                            uri = currentItem.uri
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // ErrorMessage State
            is UiState.ErrorMessage -> {
                AppErrorScreen(desc = searchResult.resId.toStringFromResId()) {
                    setEvent(SpotifyUiEvent.NetworkIO.SetSearchQueriesAndVariables(userSearchInput.value))
                }
            }

            else -> {}
        }
    }
}