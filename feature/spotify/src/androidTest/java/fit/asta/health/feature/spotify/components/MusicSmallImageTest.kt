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
class MusicSmallImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun music_small_image_card_base_ui_when_secondary_text_empty() {
        composeTestRule.setContent {
            AppTheme {
                MusicSmallImageRow(
                    imageUri = "",
                    name = "Test Track",
                    secondaryText = ""
                ) {}
            }
        }

        composeTestRule.onNode(hasClickAction()).assertExists()
        composeTestRule.onNodeWithContentDescription("Playlist Image").assertExists()
        composeTestRule.onNodeWithText("Test Track").assertExists()
        composeTestRule.onNodeWithText("").assertDoesNotExist()
    }

    @Test
    fun music_small_image_card_base_ui_when_secondary_text_not_empty() {
        composeTestRule.setContent {
            AppTheme {
                MusicSmallImageRow(
                    imageUri = "",
                    name = "Test Track",
                    secondaryText = "secondary text"
                ) {}
            }
        }

        composeTestRule.onNode(hasClickAction()).assertExists()
        composeTestRule.onNodeWithContentDescription("Playlist Image").assertExists()
        composeTestRule.onNodeWithText("Test Track").assertExists()
        composeTestRule.onNodeWithText("secondary text").assertExists()
    }

    @Test
    fun music_small_image_card_base_ui_click_check() {

        var flag = false
        composeTestRule.setContent {
            AppTheme {
                MusicSmallImageRow(
                    imageUri = "",
                    name = "Test Track",
                    secondaryText = "secondary text"
                ) { flag = true }
            }
        }

        composeTestRule.onNode(hasClickAction()).performClick()
        assert(flag)
    }
}