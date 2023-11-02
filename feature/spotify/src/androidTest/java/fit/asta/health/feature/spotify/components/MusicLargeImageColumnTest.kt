package fit.asta.health.feature.spotify.components

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.spotify.util.SpotifyTestHelper
import fit.asta.health.feature.spotify.utils.SpotifyUIHelper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MusicLargeImageColumnTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun base_ui_test() {

        val artists = SpotifyTestHelper.albumGenerator().artists

        composeTestRule.setContent {
            AppTheme {
                MusicLargeImageColumn(
                    imageUri = "",
                    headerText = "Header Text",
                    secondaryTexts = artists
                ) {}
            }
        }

        composeTestRule.onNode(hasClickAction()).assertExists()
        composeTestRule.onNodeWithContentDescription("Track Image").assertExists()
        composeTestRule.onNodeWithText("Header Text").assertExists()
        composeTestRule.onNodeWithText(
            SpotifyUIHelper.extractTextFromListOfStrings(artists) { it.name }
        )
    }

    @Test
    fun click_check() {

        val artists = SpotifyTestHelper.albumGenerator().artists
        var flag = false

        composeTestRule.setContent {
            AppTheme {
                MusicLargeImageColumn(
                    imageUri = "",
                    headerText = "Header Text",
                    secondaryTexts = artists
                ) { flag = true }
            }
        }

        composeTestRule.onNode(hasClickAction()).performClick()
        assert(flag)
    }
}