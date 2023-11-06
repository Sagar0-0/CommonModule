package fit.asta.health.feature.scheduler.ui.navigation

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.scheduler.ui.screen.alarmsetingscreen.AlarmSettingScreen
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import fit.asta.health.resources.strings.R as StringR

@RunWith(AndroidJUnit4::class)
class SchedulerTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    var status = context.resources.getString(StringR.string.status)
    var tag = context.resources.getString(StringR.string.tag)
    var lable = context.resources.getString(StringR.string.label)
    var lableText = context.resources.getString(StringR.string.enter_your_label)
    var describable = context.resources.getString(StringR.string.description)
    var describableText = context.resources.getString(StringR.string.enter_description)
    var interval = context.resources.getString(StringR.string.intervals_settings)
    var reminderMode = context.resources.getString(StringR.string.reminder_mode)
    var splash = context.resources.getString(StringR.string.splash)
    var vibration = context.resources.getString(StringR.string.vibration)
    var vibrationLong = context.resources.getString(StringR.string.longPattern)
    var sound = context.resources.getString(StringR.string.sound)
    var important = context.resources.getString(StringR.string.important)


    @Before
    fun init() {
        composeTestRule.setContent {
            AppTheme {
                AlarmSettingScreen()
            }
        }
    }

    @Test
    fun onSubmitSuccess() {
        // Start the app
        composeTestRule.onNodeWithText("M").performClick()
        composeTestRule.onNodeWithText("F").assertExists()
        composeTestRule.onNodeWithTag(status).performClick()
        composeTestRule.waitUntilTimeout(5000)
        composeTestRule.onNodeWithTag(tag).performClick()
        composeTestRule.onNodeWithTag(lable).performClick()
        composeTestRule.onNodeWithText(lableText).performTextInput("hello ashish how are you")
        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.onNodeWithTag(describable).performClick()
        composeTestRule.onNodeWithText(describableText).performTextInput("description for you")
        composeTestRule.onNodeWithTag("button").performClick()
        composeTestRule.onNodeWithTag(reminderMode).performClick()
        composeTestRule.onNodeWithTag(splash).performClick()
        composeTestRule.onNodeWithTag("close").performClick()
        composeTestRule.onNodeWithTag(vibration).performClick()
        composeTestRule.onNodeWithText("Select").performClick()
        composeTestRule.onNodeWithTag("done").performClick()
        composeTestRule.onNodeWithTag(important).performClick()


    }
}

@OptIn(DelicateCoroutinesApi::class)
fun ComposeContentTestRule.waitUntilTimeout(timeoutMillis: Long) {
    var expired = false
    GlobalScope.launch {
        delay(timeoutMillis)
        expired = true
    }

    this.waitUntil(
        condition = { expired },
        timeoutMillis = timeoutMillis + 1000
    )
}