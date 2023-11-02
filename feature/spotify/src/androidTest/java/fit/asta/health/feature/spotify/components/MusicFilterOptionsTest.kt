package fit.asta.health.feature.spotify.components

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MusicFilterOptionsTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun filter_options_base_ui_test() {
        composeTestRule.setContent {
            val filterList = remember { mutableStateMapOf("Album" to true, "Artist" to true) }
            AppTheme {
                MusicFilterOptions(
                    filterList = filterList
                ) { _, _ ->
                }
            }
        }

        composeTestRule.onNodeWithTag("Album").assertExists()
        composeTestRule.onNodeWithTag("Artist").assertExists()
        composeTestRule.onNodeWithText("Album").assertExists()
        composeTestRule.onNodeWithText("Artist").assertExists()
    }


    @Test
    fun check_box_clicked() {
        var flag = false
        composeTestRule.setContent {
            AppTheme {
                MusicFilterOptions(
                    filterList = mutableMapOf("A" to true)
                ) { _, _ ->
                    flag = true
                }
            }
        }
        composeTestRule.onNodeWithTag("A").performClick()
        assert(flag)
    }
}