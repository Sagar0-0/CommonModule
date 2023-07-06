package fit.asta.health.scheduler.util

import android.app.KeyguardManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.scheduler.model.net.scheduler.Wk
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker

class Constants {
    companion object {
        const val sun = 111
        const val mon = 222
        const val tue = 333
        const val wed = 444
        const val thu = 555
        const val fri = 666
        const val sat = 777

        const val BUNDLE_ALARM_OBJECT = "bundle_alarm_object"
        const val ARG_ALARM_OBJET = "arg_alarm_object"

        const val BUNDLE_VARIANT_INTERVAL_OBJECT = "bundle_variant_interval_object"
        const val ARG_VARIANT_INTERVAL_ALARM_OBJECT = "arg_variant_interval_alarm_object"
        const val ARG_VARIANT_INTERVAL_OBJECT = "arg_variant_interval_object"


        const val BUNDLE_PRE_NOTIFICATION_OBJECT = "bundle_pre_notification_object"
        const val ARG_PRE_NOTIFICATION_OBJET = "arg_post_notification_object"

        const val BUNDLE_POST_NOTIFICATION_OBJECT = "bundle_post_notification_object"
        const val ARG_POST_NOTIFICATION_OBJET = "arg_post_notification_object"

        const val USER_ID = "6309a9379af54f142c65fbff"

        fun changeStatusBarColor(color: Int, window: Window, context: Context) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = context.resources.getColor(color, context.theme)
            } else {
                window.statusBarColor = context.resources.getColor(color)
            }
        }

        fun setShowWhenLocked(window: Window, context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                (context as AppCompatActivity).setShowWhenLocked(true)
                context.setTurnScreenOn(true)
                val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                keyguardManager.requestDismissKeyguard(context, null)
            } else {
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                )
            }
        }

        val settings = UltimateRingtonePicker.Settings(
            systemRingtonePicker = UltimateRingtonePicker.SystemRingtonePicker(
                customSection = UltimateRingtonePicker.SystemRingtonePicker.CustomSection(),
                defaultSection = UltimateRingtonePicker.SystemRingtonePicker.DefaultSection(),
                ringtoneTypes = listOf(
                    RingtoneManager.TYPE_ALARM,
                    RingtoneManager.TYPE_RINGTONE,
                    RingtoneManager.TYPE_NOTIFICATION
                )
            ),
            deviceRingtonePicker = UltimateRingtonePicker.DeviceRingtonePicker(
                deviceRingtoneTypes = listOf(
                    UltimateRingtonePicker.RingtoneCategoryType.All,
                    UltimateRingtonePicker.RingtoneCategoryType.Artist,
                    UltimateRingtonePicker.RingtoneCategoryType.Album,
                    UltimateRingtonePicker.RingtoneCategoryType.Folder
                )
            )
        )

        fun getRecurringDaysText(alarmWeek: Wk): String {
            if (!alarmWeek.recurring) {
                return "Once"
            }
            var days = ""
            if (alarmWeek.monday) {
                days += "Mon "
            }
            if (alarmWeek.tuesday) {
                days += "Tue "
            }
            if (alarmWeek.wednesday) {
                days += "Wed "
            }
            if (alarmWeek.thursday) {
                days += "Thu "
            }
            if (alarmWeek.friday) {
                days += "Fri "
            }
            if (alarmWeek.saturday) {
                days += "Sat "
            }
            if (alarmWeek.sunday) {
                days += "Sun "
            }
            return days
        }

        const val ASTA_BASE_URL = "https://asta.fit/"
    }
}