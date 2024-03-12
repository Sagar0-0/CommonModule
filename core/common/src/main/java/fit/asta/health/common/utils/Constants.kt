package fit.asta.health.common.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.os.Parcelable
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Constants {
    const val DATA_LIMIT = 20L
    const val REQUEST_CODE = 100
    const val NOTIFICATION_TAG = "notification_tag"
    const val ALARM_NOTIFICATION_TAG = "alarm_notification_tag"
    const val HourMinAmPmKey = "hourMinAmPmKey"
    const val deepLinkUrl: String = "https://www.asta.com"
    const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    const val CHANNEL_ID_OTHER = "MISSED_ALARM_SERVICE_CHANNEL"
    const val BREATHING_GRAPH_ROUTE = "graph_breathing_tool"
    const val MEDITATION_GRAPH_ROUTE = "graph_meditation_tool"
    const val SLEEP_GRAPH_ROUTE = "graph_sleep_tool"
    const val SCHEDULER_GRAPH_ROUTE = "graph_today_scheduler"
    const val WALKING_GRAPH_ROUTE = "graph_walking_tool"
    const val EXERCISE_GRAPH_ROUTE = "graph_exercise_tool"
    const val WORKOUT_GRAPH_ROUTE = "graph_exercise_tool?activity=workout"
    const val YOGA_GRAPH_ROUTE = "graph_exercise_tool?activity=yoga"
    const val HIIT_GRAPH_ROUTE = "graph_exercise_tool?activity=HIIT"
    const val DANCE_GRAPH_ROUTE = "graph_exercise_tool?activity=dance"
    const val WATER_GRAPH_ROUTE = "graph_water_tool"
    const val SUNLIGHT_GRAPH_ROUTE = "graph_sunlight_tool"
    const val PROFILE_GRAPH_ROUTE = "graph_profile_tool"
    const val CREATE_PROFILE_GRAPH_ROUTE = "graph_create_profile_tool"
    const val TAG_NAME = "tag_name"
    fun getDayAndTime(inputDateString: String = "2023-07-03T12:00"): DayAndTime {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dayFormat = DateTimeFormatter.ofPattern("MMMM dd")
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val currentDateTime = LocalDateTime.now()
        try {
            val inputDateTime: LocalDateTime =
                LocalDateTime.parse(inputDateString, inputFormat)
            val currentTime: LocalTime = inputDateTime.toLocalTime()
            val timeOfDay = when {
                currentTime.isBefore(LocalTime.NOON) -> "Morning"
                currentTime.isBefore(LocalTime.of(17, 0)) -> "Afternoon"
                else -> "Evening"
            }
            val outputFormattedDay: String =
                when (inputDateTime.dayOfMonth - currentDateTime.dayOfMonth) {
                    0 -> "Today"
                    1 -> "Tomorrow"
                    else -> dayFormat.format(inputDateTime)
                }
            Log.d(
                "today",
                "getDayAndTime: inputDateTime $inputDateTime,currentTime $currentTime,timeOfDay $outputFormattedDay"
            )
            return DayAndTime(
                day = outputFormattedDay, time = timeFormatter.format(inputDateTime),
                timeOfDay = timeOfDay
            )
        } catch (e: Exception) {
            println("ErrorMessage occurred: ${e.message}")
        }
        return DayAndTime("", "", "")
    }

    fun getHourMinAmPm(
        inputTimeString: String = "12:00 PM",
        inputDay: String = "Today"
    ): HourMinAmPm {
        val inputFormat = DateTimeFormatter.ofPattern("hh:mm a")
        val day: Int = when (inputDay) {
            "Today" -> 0
            "Tomorrow" -> 1
            else -> 2
        }
        try {
            val parsedTime = LocalTime.parse(inputTimeString, inputFormat)
            val amPm = !parsedTime.isBefore(LocalTime.NOON)
            return HourMinAmPm(parsedTime.hour, parsedTime.minute, amPm, day)
        } catch (e: Exception) {
            println("ErrorMessage occurred: ${e.message}")
        }
        return HourMinAmPm(0, 0, false, day)
    }

    fun goToTool(tag: String): String {
        return when (tag.lowercase(Locale.ROOT)) {

            "beverage" -> {
                WATER_GRAPH_ROUTE
            }

            "breathing" -> {
                BREATHING_GRAPH_ROUTE
            }

            "dance" -> {
                DANCE_GRAPH_ROUTE
            }

            "hiit" -> {
                HIIT_GRAPH_ROUTE
            }

            "meditation" -> {
                MEDITATION_GRAPH_ROUTE
            }

            "nap" -> {
                SLEEP_GRAPH_ROUTE
            }

            "sleep" -> {
                SLEEP_GRAPH_ROUTE
            }

            "steps" -> {
                WALKING_GRAPH_ROUTE
            }

            "stretches" -> {
                EXERCISE_GRAPH_ROUTE
            }

            "sunlight" -> {
                SUNLIGHT_GRAPH_ROUTE
            }

            "walking" -> {
                WALKING_GRAPH_ROUTE
            }

            "water" -> {
                WATER_GRAPH_ROUTE
            }

            "workout" -> {
                EXERCISE_GRAPH_ROUTE
            }

            "yoga" -> {
                YOGA_GRAPH_ROUTE
            }

            else -> {
                ""
            }
        }

//        return when (tag) {
//            "Breathing" -> {
//                BREATHING_GRAPH_ROUTE
//            }
////        "Diet" -> {}
////        "Face Wash" -> {}
////        "Intermittent" -> {}
////        "Medicines" -> {
////            WATER_GRAPH_ROUTE
////        }
//            "Meditation" -> {
//                MEDITATION_GRAPH_ROUTE
//            }
////        "Power Nap" -> {}Graph.ExerciseTool.route + "?activity=dance"
//            "Sleep" -> {
//                SLEEP_GRAPH_ROUTE
//            }
////        "Sleep Therapy" -> {}
//            "Stretches" -> {
//                ("$EXERCISE_GRAPH_ROUTE?activity=yoga")
//            }
//
//            "SunLight" -> {
//                SUNLIGHT_GRAPH_ROUTE
//            }
//
//            "Walking" -> {
//                WALKING_GRAPH_ROUTE
//            }
//
//            "Water" -> {
//                WATER_GRAPH_ROUTE
//            }
//
//            "Workout" -> {
//                ("$EXERCISE_GRAPH_ROUTE?activity=workout")
//            }
//
//            "Yoga" -> {
//                ("$EXERCISE_GRAPH_ROUTE?activity=yoga")
//            }
//
//            else -> {
//                ""
//            }
//        }
    }

    fun getDataForSchedule(toolName: String): List<String> {
        return when (toolName.lowercase(Locale.ROOT)) {
            "water" -> {
                listOf(/*desc = */ "water, a substance composed of the chemical elements hydrogen and oxygen and existing in gaseous, liquid, and solid states. It is one of the most plentiful and essential of compounds. A tasteless and odourless liquid at room temperature, it has the important ability to dissolve many other substances.",/* label = */
                    "Water"
                )
            }

            "breathing" -> {
                listOf(/*desc = */ "Breathing is usually an unconscious process. However, there are some optimal ways to breathe",
                    /* label = */ "Breathing"
                )
            }

            "dance" -> {
                listOf(
                    "There are many forms of dance, from ballroom to barn dancing and disco to Morris dancing. Dance has always been a part of human culture, rituals and celebrations. Today, most dancing is about recreation and self-expression, although it can also be done as a competitive activity.",
                    "Dance"
                )
            }

            "hiit" -> {
                listOf("High Intensity Workout", "Workout")
            }

            "meditation" -> {
                listOf(
                    "Meditation can improve well-being and quality of life.",
                    "Meditation"
                )
            }

            "sleep" -> {
                listOf(
                    "Sleep impacts every aspect of our lives. Here are some of the main points you might want to take into consideration in order to get a good night’s sleep.",
                    "Sleep"
                )
            }

            "nap" -> {
                listOf("Nap boosts Activity", "Nap")
            }

            "steps" -> {
                listOf("Steps", "Steps")
            }

            "stretches" -> {
                listOf("WarmUp", "Stretches")
            }

            "sunlight" -> {
                listOf(/*desc = */ "If you think sunbathing is a sheer waste of time, then you are sadly mistaken. FYI, it has more benefits than you could ever imagine. According to WHO , getting anywhere from 5 to 15 minutes of sunlight on your arms, hands, and face 2-3 times a week is enough to enjoy the vitamin D-boosting benefits of the sun.",
                    /* label = */ "SunLight"
                )
            }

            "walking" -> {
                listOf(
                    "Walking is free to do and easy to fit into your daily routine. All you need to start walking is a sturdy pair of walking shoes.",
                    "Walking"
                )
            }

            "workout" -> {
                listOf(
                    "Regular exercise is one of the best things you can do for your health. In fact, you’ll begin to see and feel the benefits consistent physical activity can have on your body and well-being quickly.",
                    "Workout"
                )
            }

            "yoga" -> {
                listOf(
                    "Yoga is a practice that connects the body, breath, and mind. It uses physical postures, breathing exercises, and meditation to improve overall health. Yoga was developed as a spiritual practice thousands of years ago. Today, most Westerners do yoga for exercise or to reduce stress.",
                    "Yoga"
                )
            }

            else -> {
                emptyList()
            }
        }
    }

    object ToolTag {
        const val SUNLIGHT = "sunlight"
        const val BEVERAGE = "beverage"
        const val BREATHING = "breathing"
        const val DANCE = "dance"
        const val HIIT = "hiit"
        const val MEDITATION = "meditation"
        const val NAP = "nap"
        const val SLEEP = "sleep"
        const val STEPS = "steps"
        const val STRETCHES = "stretches"
        const val WALKING = "walking"
        const val WATER = "water"
        const val WORKOUT = "workout"
        const val YOGA = "yoga"
    }
}


data class DayAndTime(val day: String, val time: String, val timeOfDay: String)

@Parcelize
data class HourMinAmPm(val hour: Int, val min: Int, val amPm: Boolean, val day: Int) : Parcelable

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

data class AMPMHoursMin(
    val hours: Int = 1,
    val minutes: Int = 1,
    val dayTime: DayTime = DayTime.PM
) {
    enum class DayTime {
        AM,
        PM
    }
}

data class Time24hr(val hour: Int, val min: Int)

fun Time24hr.convert24hrTo12hr(): AMPMHoursMin {
    var hour = this.hour
    val dayTime = if (hour < 12) AMPMHoursMin.DayTime.AM else AMPMHoursMin.DayTime.PM
    hour %= 12
    if (hour == 0) {
        hour = 12
    }
    return AMPMHoursMin(hour, this.min, dayTime)
}

fun AMPMHoursMin.convert12hrTo24hr(): Time24hr {
    var hour = this.hours
    if (this.dayTime == AMPMHoursMin.DayTime.PM && hour != 12) {
        hour += 12
    } else if (this.dayTime == AMPMHoursMin.DayTime.AM && hour == 12) {
        hour = 0
    }
    return Time24hr(hour, this.minutes)
}

fun scrollToIndex(size: Int, size1: Int, size2: Int): Int {
    return when (LocalTime.now().hour) {
        in 6..10 -> {
            4
        }

        in 11..14 -> {
            4 + size
        }

        else -> {
            4 + size + size1
        }
    }
}

/*val Context.navigationBarHeight: Int
    get() {
*//*        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= 30) {
            windowManager
                .currentWindowMetrics
                .windowInsets
                .getInsets(WindowInsets.Type.navigationBars())
                .bottom

        } else {
            val currentDisplay = try {
                windowManager.defaultDisplay
            } catch (e: NoSuchMethodError) {
                windowManager.defaultDisplay
            }

            val appUsableSize = Point()
            val realScreenSize = Point()
            currentDisplay?.apply {
                getSize(appUsableSize)
                getRealSize(realScreenSize)
            }

            // navigation bar on the side
            if (appUsableSize.x < realScreenSize.x) {
                return realScreenSize.x - appUsableSize.x
            }

            // navigation bar at the bottom
            return if (appUsableSize.y < realScreenSize.y) {
                realScreenSize.y - appUsableSize.y
            } else 0
        }*//*
        return this.getNavigationBarHeight()
    }*/

fun Context.navigationBarHeight(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT >= 30) {
            windowManager
                .currentWindowMetrics
                .windowInsets
                .getInsets(WindowInsets.Type.navigationBars())
                .bottom
            val hasGesture: Boolean
            val margin =
                windowManager.currentWindowMetrics.windowInsets.getInsets(WindowInsets.Type.systemGestures())?.left ?: 0
            hasGesture = margin > 0

            if (hasGesture) return 12
        }

    }

    val resources: Resources = this.resources
    val resName = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        "navigation_bar_height"
    } else {
        "navigation_bar_height_landscape"
    }
    val id: Int = resources.getIdentifier(resName, "dimen", "android")
    return if (id > 0) {
        resources.getDimensionPixelSize(id).pxToDp(this).toInt()
    } else {
        0
    }
}

fun Int.pxToDp(context: Context): Float {
    return   this / context.resources.displayMetrics.density;
}

fun Context.isSpotifyInstalled(): Boolean {
    val packageName = "com.spotify.music"
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}