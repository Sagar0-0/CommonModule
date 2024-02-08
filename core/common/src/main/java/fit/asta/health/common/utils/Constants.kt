package fit.asta.health.common.utils

import android.os.Parcelable
import android.util.Log
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

object Constants {
    const val DATA_LIMIT = 20L
    const val REQUEST_CODE = 100
    const val NOTIFICATION_TAG = "notification_tag"
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
        return when (tag) {
            "Medicines" -> {
                WATER_GRAPH_ROUTE
            }

            "Water" -> {
                WATER_GRAPH_ROUTE
            }

            "Drink water" -> {
                WATER_GRAPH_ROUTE
            }

            "Workout" -> {
                EXERCISE_GRAPH_ROUTE
            }

            "Stretches" -> {
                EXERCISE_GRAPH_ROUTE
            }

            "Hiit" -> {
                HIIT_GRAPH_ROUTE
            }

            "Dance" -> {
                DANCE_GRAPH_ROUTE
            }

            "Meditation" -> {
                MEDITATION_GRAPH_ROUTE
            }

            "Sleep" -> {
                SLEEP_GRAPH_ROUTE
            }

            "Breathing" -> {
                BREATHING_GRAPH_ROUTE
            }

            "SunLight" -> {
                SUNLIGHT_GRAPH_ROUTE
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