package fit.asta.health.feature.scheduler.util

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.common.utils.Time24hr
import kotlinx.parcelize.Parcelize
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class Constants {
    companion object {
        const val sun = 111
        const val mon = 222
        const val tue = 333
        const val wed = 444
        const val thu = 555
        const val fri = 666
        const val sat = 777

        const val ARG_ALARM_OBJET = "arg_alarm_object"
        const val ARG_PRE_NOTIFICATION_OBJET = "arg_post_notification_object"
        const val ARG_POST_NOTIFICATION_OBJET = "arg_post_notification_object"
        const val ARG_VARIANT_INTERVAL_ALARM_OBJECT = "arg_variant_interval_alarm_object"
        const val ARG_VARIANT_INTERVAL_OBJECT = "arg_variant_interval_object"
        const val BUNDLE_ALARM_OBJECT = "bundle_alarm_object"
        const val BUNDLE_ALARM_OBJECT_NOTIFICATION = "bundle_alarm_object_notification"
        const val BUNDLE_VARIANT_INTERVAL_OBJECT = "bundle_variant_interval_object"
        const val BUNDLE_VARIANT_INTERVAL_OBJECT_NOTIFICATION =
            "bundle_variant_interval_object_notification"
        const val BUNDLE_PRE_NOTIFICATION_OBJECT = "bundle_pre_notification_object"
        const val BUNDLE_POST_NOTIFICATION_OBJECT = "bundle_post_notification_object"

//        fun changeStatusBarColor(color: Int, window: Window, context: Context) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.statusBarColor = context.resources.getColor(color, context.theme)
//        }

        fun setShowWhenLocked(window: Window, context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                (context as AppCompatActivity).setShowWhenLocked(true)
                context.setTurnScreenOn(true)
                val keyguardManager =
                    context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                keyguardManager.requestDismissKeyguard(context, null)
            } else {
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                )
            }
        }

        fun getTimeDifference(targetTime24hr: Time24hr): Long {
            val currentTime = LocalTime.now()
            val targetTime = LocalTime.of(targetTime24hr.hour, targetTime24hr.min)

            // If the target time is before the current time, add 24 hours to the target time
            // to get the correct time difference for today
            if (targetTime.isBefore(currentTime)) {
                targetTime.plusHours(24)
            }

            return currentTime.until(targetTime, ChronoUnit.MINUTES)
        }

        fun getVibrationPattern(value: VibrationPattern): LongArray {
            return when (value) {
                VibrationPattern.Short -> longArrayOf(0, 500, 250, 500, 250, 500)
                VibrationPattern.Long -> longArrayOf(0, 1000, 500, 1000)
                VibrationPattern.Intermittent -> longArrayOf(0, 500, 250, 500, 250, 500, 2500)
            }
        }

        fun getVibrationPattern(value: String): LongArray {
            return when (value) {
                "Short" -> longArrayOf(0, 500, 250, 500, 250, 500)
                "Long" -> longArrayOf(0, 1000, 500, 1000)
                "Intermittent" -> longArrayOf(0, 500, 250, 500, 250, 500, 2500)
                else -> longArrayOf(0, 500, 250, 500, 250, 500, 2500)
            }
        }
    }
}

@Parcelize
sealed class VibrationPattern : Parcelable {
    object Short : VibrationPattern(), Parcelable
    object Long : VibrationPattern(), Parcelable
    object Intermittent : VibrationPattern(), Parcelable
}