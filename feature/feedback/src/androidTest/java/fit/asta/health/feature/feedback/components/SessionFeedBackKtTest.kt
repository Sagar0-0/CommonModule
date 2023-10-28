package fit.asta.health.feature.feedback.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.Question
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SessionFeedBackTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun onSubmitSuccess() {
        val feedbackQuesDTO = FeedbackQuesDTO(
            questions = listOf(
                Question(),
                Question(),
                Question()
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuesDTO),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("feedback image").assertExists()
    }
}