package fit.asta.health.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.modal.OnboardingData
import fit.asta.health.onboarding.ui.OnBoardingPager
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnBoardingScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val data = listOf(
        OnboardingData(
            title = "Title",
            desc = "desc"
        ),
        OnboardingData(
            title = "Title",
            desc = "desc"
        ),
        OnboardingData(
            title = "Title",
            desc = "desc"
        ),
        OnboardingData(
            title = "Title",
            desc = "desc"
        ),
    )

    @Before
    fun setup() {
        rule.setContent {
            OnBoardingPager(
                state = ResponseState.Success(
                    data
                ),
                onReload = {

                }) {
                rule.activity.finish()
            }
        }
    }

    @Test
    fun clickSkip_finishScreen() {
        rule.onNodeWithText("Skip").performClick()
        rule.onNodeWithText("Skip").assertDoesNotExist()
    }

    @Test
    fun visible_finish_at_end() {
        rule.onNodeWithText("Next").performClick()
        rule.onNodeWithText("Next").performClick()
        rule.onNodeWithText("Next").performClick()
        rule.onNodeWithText("Finish").assertExists()
    }
}