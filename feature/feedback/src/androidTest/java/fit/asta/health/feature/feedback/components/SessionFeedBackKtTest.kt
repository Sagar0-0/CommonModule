package fit.asta.health.feature.feedback.components

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestions
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
    fun feedbackImagePresent() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf()
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("feedback image").assertExists()
    }

    @Test
    fun uploadFilesIconExists() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf(
                Question(type = 1)
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Upload Icon Button").assertExists()
    }

    @Test
    fun ratingCardExists() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf(
                Question(type = 2)
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Rating").assertExists()
    }

    @Test
    fun mcqCardExists() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf(
                Question(type = 3)
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("McqCard").assertExists()
    }

    @Test
    fun mcqCard2Exists() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf(
                Question(type = 5)
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("McqCard").assertExists()
    }

    @Test
    fun appTextFieldExists() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf(
                Question(type = 4)
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("AppOutlinedTextField").assertExists()
    }

    @Test
    fun submitButtonEnabled() {
        val feedbackQuestions = FeedbackQuestions(
            questions = listOf(
                Question(type = 1),
                Question(type = 2),
                Question(type = 3)
            )
        )
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                SessionFeedback(
                    feedbackQuesState = UiState.Success(feedbackQuestions),
                    onBack = {},
                    onSubmit = {}
                )
            }
        }
        composeTestRule
            .onNodeWithContentDescription("LazyColumn")
            .performScrollToIndex(2)
        composeTestRule
            .onNodeWithContentDescription("SubmitButton")
            .assertIsEnabled()
    }
}