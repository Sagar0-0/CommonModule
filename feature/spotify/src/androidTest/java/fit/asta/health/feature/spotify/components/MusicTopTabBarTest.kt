package fit.asta.health.feature.spotify.components

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MusicTopTabBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun music_top_tab_bar_base_ui() {
        composeTestRule.setContent {
            AppTheme {
                MusicTopTabBar(
                    tabList = listOf("Asta Music", "Favourite", "Third Party"),
                    selectedItem = 1,
                    selectedColor = AppTheme.colors.primary,
                    unselectedColor = AppTheme.colors.secondary,
                    onNewTabClicked = {}
                ) {}
            }
        }

        composeTestRule.onNode(hasText("Asta Music"))

        composeTestRule.onNodeWithText("Asta Music").assertExists()
        composeTestRule.onNodeWithText("Favourite").assertExists()
        composeTestRule.onNodeWithText("Third Party").assertExists()
        composeTestRule.onNodeWithText("Favourite").assertIsEnabled()
    }

    @Test
    fun music_top_tab_bar_click_check() {

        var flag = false
        composeTestRule.setContent {
            AppTheme {
                MusicTopTabBar(
                    tabList = listOf("Asta Music", "Favourite", "Third Party"),
                    selectedItem = 1,
                    selectedColor = AppTheme.colors.primary,
                    unselectedColor = AppTheme.colors.secondary,
                    onNewTabClicked = {
                        flag = true
                    }
                ) {}
            }
        }

        composeTestRule
            .onNode(
                hasClickAction() and hasAnyChild(hasText("Asta Music")),
                useUnmergedTree = true
            ).performClick()

        assert(flag)
    }
}