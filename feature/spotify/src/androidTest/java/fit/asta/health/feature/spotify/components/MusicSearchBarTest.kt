package fit.asta.health.feature.spotify.components

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.hasInsertTextAtCursorAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.text.input.ImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MusicSearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun music_search_bar_base_ui_when_empty() {
        composeTestRule.setContent {
            AppTheme {
                SearchBar(
                    userInput = "",
                    onUserInputChange = {},
                    onFilterButtonClick = {},
                    onUserDone = {}
                )
            }
        }

        composeTestRule.onNodeWithText("").assertExists()
        composeTestRule.onNodeWithContentDescription("Clear Button").assertDoesNotExist()
    }

    @Test
    fun music_search_bar_base_ui_when_filled() {
        composeTestRule.setContent {
            AppTheme {
                SearchBar(
                    userInput = "user Input",
                    onUserInputChange = {},
                    onFilterButtonClick = {},
                    onUserDone = {}
                )
            }
        }

        composeTestRule.onNodeWithText("user Input").assertExists()
        composeTestRule.onNodeWithContentDescription("Clear Button").assertExists()
    }

    @Test
    fun music_search_bar_base_ui_when_click_checks() {

        var input = "user Input"
        var filter = false
        var onDone = false
        composeTestRule.setContent {
            AppTheme {
                SearchBar(
                    userInput = input,
                    onUserInputChange = {
                        input = it
                    },
                    onFilterButtonClick = {
                        filter = true
                    },
                    onUserDone = {
                        onDone = true
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("user Input").assertExists()

        composeTestRule.onNode(
            hasClickAction() and hasContentDescription("Filter Button")
        ).performClick()
        assert(filter)

        composeTestRule.onNode(hasInsertTextAtCursorAction()).performTextReplacement("input changed")
        assert(input == "input changed")

        composeTestRule.onNode(
            hasImeAction(ImeAction.Search)
        ).performClick()
        assert(onDone)
    }
}