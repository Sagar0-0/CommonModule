package fit.asta.health.feature.spotify.components

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MusicProfileOptionListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun music_profile_option_list_base_ui() {
        composeTestRule.setContent {
            AppTheme {
                MusicProfileOptionList(
                    selectedItem = 2,
                    onSelectionChange = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Liked").assertExists()
        composeTestRule.onNodeWithText("Track").assertExists()
        composeTestRule.onNodeWithText("Playlist").assertExists()
        composeTestRule.onNodeWithText("Artist").assertExists()
        composeTestRule.onNodeWithText("Album").assertExists()
        composeTestRule.onNodeWithText("Show").assertExists()
        composeTestRule.onNodeWithText("Episode").assertExists()

        composeTestRule.onNodeWithText("Playlist").assertIsEnabled()
    }

    @Test
    fun music_profile_option_list_click_check() {

        var selected = 2

        composeTestRule.setContent {
            AppTheme {
                MusicProfileOptionList(
                    selectedItem = selected,
                    onSelectionChange = { selected = 3 }
                )
            }
        }

        composeTestRule.onNodeWithText("Artist").assertIsEnabled()
    }
}