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
class MusicPlayableSmallCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun playable_card_base_ui() {
        composeTestRule.setContent {
            AppTheme {
                MusicPlayableSmallCards(
                    imageUri = "",
                    name = "Card Name",
                    onClick = {}
                )
            }
        }

        composeTestRule.onNode(hasClickAction()).assertExists()
        composeTestRule.onNodeWithContentDescription("Track Image")
        composeTestRule.onNodeWithText("Card Name").assertExists()
    }


    @Test
    fun playable_card_click_check() {
        var flag = false
        composeTestRule.setContent {
            AppTheme {
                MusicPlayableSmallCards(
                    imageUri = "",
                    name = "Card Name",
                    onClick = { flag = true }
                )
            }
        }

        composeTestRule.onNode(hasClickAction()).performClick()

        assert(flag)
    }
}