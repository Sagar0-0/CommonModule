package fit.asta.health.feature.spotify.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.spotify.screens.AlbumDetailScreen
import fit.asta.health.feature.spotify.util.SpotifyTestHelper.albumGenerator
import fit.asta.health.feature.spotify.util.SpotifyTestHelper.multipleAlbumGenerator
import fit.asta.health.feature.spotify.utils.SpotifyUIHelper
import fit.asta.health.resources.strings.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shows_Album_Details_When_Already_Saved() {

        // Album Data to be passed to the Composable
        val album = albumGenerator()
        val albumList = multipleAlbumGenerator()

        // generating the Artists Names
        val artistNames = SpotifyUIHelper.extractTextFromListOfStrings(album.artists) { it.name }

        composeTestRule.setContent {
            AppTheme {
                AlbumDetailScreen(
                    albumLocalResponse = UiState.Success(albumList),
                    albumNetworkResponse = UiState.Success(album),
                    setEvent = {}
                )
            }
        }

        // checking if we are getting the Album image
        composeTestRule.onNodeWithContentDescription("Track Image").assertExists()

        // Checking if we are getting the Album Name
        composeTestRule.onNodeWithText(album.name).assertExists()

        // Checking if we are getting the Artists Names
        composeTestRule.onNodeWithText(artistNames).assertExists()

        // Checking if the Button contains the Delete Text or not
        composeTestRule.onNodeWithText("Delete From Favourites").assertExists()

        // Checking if the Spotify Play Button is there or not
        composeTestRule.onNodeWithText("Play using Spotify").assertExists()
    }

    @Test
    fun shows_Album_Details_When_Not_Saved() {

        // Album Data to be passed to the Composable
        val album = albumGenerator()
        val albumList = listOf<Album>()

        // generating the Artists Names
        val artistNames = SpotifyUIHelper.extractTextFromListOfStrings(album.artists) { it.name }

        composeTestRule.setContent {
            AppTheme {
                AlbumDetailScreen(
                    albumLocalResponse = UiState.Success(albumList),
                    albumNetworkResponse = UiState.Success(album),
                    setEvent = {}
                )
            }
        }

        // checking if we are getting the Album image
        composeTestRule.onNodeWithContentDescription("Track Image").assertExists()

        // Checking if we are getting the Album Name
        composeTestRule.onNodeWithText(album.name).assertExists()

        // Checking if we are getting the Artists Names
        composeTestRule.onNodeWithText(artistNames).assertExists()

        // Checking if the Button contains the Delete Text or not
        composeTestRule.onNodeWithText("Add To Favourites").assertExists()

        // Checking if the Spotify Play Button is there or not
        composeTestRule.onNodeWithText("Play using Spotify").assertExists()
    }


    @Test
    fun shows_Error_When_Local_State_Failure() {

        // Album Data to be passed to the Composable
        val album = albumGenerator()
        val error = UiState.ErrorMessage(R.string.server_error)

        composeTestRule.setContent {
            AppTheme {
                AlbumDetailScreen(
                    albumLocalResponse = error,
                    albumNetworkResponse = UiState.Success(album),
                    setEvent = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Server error").assertExists()
    }


    @Test
    fun shows_Error_When_Network_State_Failure() {

        // Album Data to be passed to the Composable
        val albumList = multipleAlbumGenerator()
        val error = UiState.ErrorMessage(R.string.server_error)

        composeTestRule.setContent {
            AppTheme {
                AlbumDetailScreen(
                    albumLocalResponse = UiState.Success(albumList),
                    albumNetworkResponse = error,
                    setEvent = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Server error").assertExists()
    }


    @Test
    fun shows_Loading_When_Network_State_Loading() {

        // Album Data to be passed to the Composable
        val albumList = multipleAlbumGenerator()
        val loading = UiState.Loading

        composeTestRule.setContent {
            AppTheme {
                AlbumDetailScreen(
                    albumLocalResponse = UiState.Success(albumList),
                    albumNetworkResponse = loading,
                    setEvent = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("Album Details Tag").assertExists()
    }


    @Test
    fun shows_Loading_When_Local_State_Loading() {

        // Album Data to be passed to the Composable
        val album = albumGenerator()
        val loading = UiState.Loading

        composeTestRule.setContent {
            AppTheme {
                AlbumDetailScreen(
                    albumLocalResponse = loading,
                    albumNetworkResponse = UiState.Success(album),
                    setEvent = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("Album Details Tag").assertExists()
    }
}