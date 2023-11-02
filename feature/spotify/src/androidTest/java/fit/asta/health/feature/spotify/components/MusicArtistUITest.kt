package fit.asta.health.feature.spotify.components

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MusicArtistUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onClick_Working() {
        var count = 0
        composeTestRule.setContent {
            AppTheme {
                MusicArtistsUI(
                    imageUri = "",
                    artistName = "Anirban"
                ) { count++ }
            }
        }

        composeTestRule.onNode(hasClickAction()).performClick()
        composeTestRule.onNodeWithContentDescription("Artist Image").assertExists()
        composeTestRule.onNodeWithText("Anirban").assertExists()
        assert(count == 1)
    }
}