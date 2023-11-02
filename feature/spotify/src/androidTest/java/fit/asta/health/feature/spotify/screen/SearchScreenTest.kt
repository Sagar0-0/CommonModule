package fit.asta.health.feature.spotify.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.common.utils.UiState
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.spotify.screens.SearchScreen
import fit.asta.health.feature.spotify.util.SpotifyTestHelper.generateSpotifySearchState
import fit.asta.health.resources.strings.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun search_state_success() {
        val spotifySearchData = generateSpotifySearchState()
        composeTestRule.setContent {
            AppTheme {
                SearchScreen(
                    spotifySearchState = UiState.Success(spotifySearchData),
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Tracks").assertExists()
    }


    @Test
    fun search_state_loading() {
        composeTestRule.setContent {
            AppTheme {
                SearchScreen(
                    spotifySearchState = UiState.Loading,
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("Search Screen").assertExists()
    }

    @Test
    fun search_state_failure() {
        composeTestRule.setContent {
            AppTheme {
                SearchScreen(
                    spotifySearchState = UiState.ErrorMessage(R.string.server_error),
                    setEvent = {},
                    navigator = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Server error").assertExists()
    }
}