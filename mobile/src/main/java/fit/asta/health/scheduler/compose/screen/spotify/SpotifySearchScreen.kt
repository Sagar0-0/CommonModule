package fit.asta.health.scheduler.compose.screen.spotify

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.scheduler.compose.components.SearchBarUI
import fit.asta.health.scheduler.compose.components.SpotifyMusicItem
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall

@Composable
fun SpotifySearchScreen(
    searchResult: SpotifyNetworkCall<SpotifySearchModel>,
    playSong: (String) -> Unit,
    setSearchQuery: (String, String) -> Unit
) {

    // Context and Activity of the Function
    val context = LocalContext.current

    // This is the Input of the User
    val userSearchInput = remember { mutableStateOf("") }

    // Root Composable function
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        // This function Draws the Search Bar to the Screen
        SearchBarUI(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            userInput = userSearchInput.value,
            onUserInputChange = {
                userSearchInput.value = it
            }
        ) {
            sendRequest(
                context = context,
                userSearchInput = userSearchInput.value,
                setSearchQuery = setSearchQuery
            )
        }


        when (searchResult) {

            // Initialized State
            is SpotifyNetworkCall.Initialized -> {}

            // Loading State
            is SpotifyNetworkCall.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // Success State
            is SpotifyNetworkCall.Success -> {

                // Handling the Tracks UI here
                searchResult.data?.tracks?.trackList.let { trackList ->

                    // Tracks
                    Text(
                        text = "Tracks",

                        modifier = Modifier
                            .padding(16.dp),

                        // Text and Font Properties
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.W800,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Showing the Tracks List UI inside a Lazy Row
                    LazyColumn(
                        modifier = Modifier
                            .height(LocalConfiguration.current.screenHeightDp.dp)
                            .padding(16.dp)
                            .width(LocalConfiguration.current.screenWidthDp.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(spacing.medium)
                    ) {
                        if (trackList != null) {

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
                                    onCardClick = { playSong(currentItem.uri) }
                                ) {

                                }
                            }
                        }
                    }
                }
            }

            // Failure State
            is SpotifyNetworkCall.Failure -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {

                            // Searching Again
                            sendRequest(
                                context = context,
                                userSearchInput = userSearchInput.value,
                                setSearchQuery = setSearchQuery
                            )
                        },
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 32.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = searchResult.message.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),

                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

/**
 * This Function asks the ViewModel to fetch the result of the Search
 */
private fun sendRequest(
    context: Context,
    userSearchInput: String,
    setSearchQuery: (String, String) -> Unit
) {

    // Making a string with the filters chosen by the User
    val type = "track"

    // Checking if both the user Input and the filtered type is not empty
    if (userSearchInput.isNotEmpty()) {

        // Setting the Query parameters in the ViewModel
        setSearchQuery(userSearchInput, type)
    } else {

        // No Search Query
        Toast.makeText(
            context,
            "Search Bar Empty!",
            Toast.LENGTH_SHORT
        ).show()
    }
}