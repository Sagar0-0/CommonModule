package fit.asta.health.feature.feedback.components

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.Qn
import fit.asta.health.designsystem.AppTheme
import org.junit.Rule
import org.junit.jupiter.api.Test

class SessionFeedBackTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onSubmitSuccess() {
        val feedbackQuesDTO = FeedbackQuesDTO(
            qns = listOf(
                Qn(),
                Qn(),
                Qn()
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

        composeTestRule.onNodeWithText("Submit").assertIsEnabled()
    }
}