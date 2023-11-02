package fit.asta.health.feature.spotify.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.common.utils.UiState
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.spotify.screens.FavouriteScreen
import fit.asta.health.feature.spotify.util.SpotifyTestHelper.multipleAlbumGenerator
import fit.asta.health.feature.spotify.util.SpotifyTestHelper.multipleTrackGenerator
import fit.asta.health.feature.spotify.utils.SpotifyUIHelper.extractTextFromListOfStrings
import fit.asta.health.resources.strings.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FavouriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shows_tracks_when_track_is_Success() {

        val trackList = multipleTrackGenerator(count = 1)
        val trackArtistsName = extractTextFromListOfStrings(trackList[0].artists) { it.name }

        composeTestRule.setContent {
            AppTheme {
                FavouriteScreen(
                    tracksData = UiState.Success(trackList),
                    albumData = UiState.Loading,
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Track Image").assertExists()
        composeTestRule.onNodeWithText(trackList[0].name).assertExists()
        composeTestRule.onNodeWithText(trackArtistsName).assertExists()

        composeTestRule.onNodeWithTag("Favourite Screen").assertExists()
    }


    @Test
    fun shows_albums_when_track_is_Success() {

        val albumList = multipleAlbumGenerator(count = 1)
        val albumArtistsName = extractTextFromListOfStrings(albumList[0].artists) { it.name }

        composeTestRule.setContent {
            AppTheme {
                FavouriteScreen(
                    tracksData = UiState.Loading,
                    albumData = UiState.Success(albumList),
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Track Image").assertExists()
        composeTestRule.onNodeWithText(albumList[0].name).assertExists()
        composeTestRule.onNodeWithText(albumArtistsName).assertExists()

        composeTestRule.onNodeWithTag("Favourite Screen").assertExists()
    }


    @Test
    fun shows_error_when_track_state_Error() {

        val albumList = multipleAlbumGenerator(count = 1)
        val error = UiState.ErrorMessage(R.string.server_error)

        composeTestRule.setContent {
            AppTheme {
                FavouriteScreen(
                    tracksData = error,
                    albumData = UiState.Success(albumList),
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Server Error").assertExists()
    }

    @Test
    fun shows_error_when_album_state_Error() {

        val trackList = multipleTrackGenerator(count = 1)
        val error = UiState.ErrorMessage(R.string.server_error)

        composeTestRule.setContent {
            AppTheme {
                FavouriteScreen(
                    tracksData = UiState.Success(trackList),
                    albumData = error,
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Server Error").assertExists()
    }
}