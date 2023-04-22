package fit.asta.health.scheduler.util

import android.app.KeyguardManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.model.net.scheduler.Wk
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

        fun extractDaysOfAlarm(selectedDayString: String): Wk {
            var isRecurring = false
            var isMonday = false
            var isTuesday = false
            var isWednesday = false
            var isThursday = false
            var isFriday = false
            var isSaturday = false
            var isSunday = false

            if (selectedDayString.lowercase().contains("mon")) {
                isRecurring = true
                isMonday = true
            }

            if (selectedDayString.lowercase().contains("tue")) {
                isRecurring = true
                isTuesday = true
            }

            if (selectedDayString.lowercase().contains("wed")) {
                isRecurring = true
                isWednesday = true
            }

            if (selectedDayString.lowercase().contains("thu")) {
                isRecurring = true
                isThursday = true
            }

            if (selectedDayString.lowercase().contains("fri")) {
                isRecurring = true
                isFriday = true
            }

            if (selectedDayString.lowercase().contains("sat")) {
                isRecurring = true
                isSaturday = true
            }

            if (selectedDayString.lowercase().contains("sun")) {
                isRecurring = true
                isSunday = true
            }

            return Wk(
                monday = isMonday,
                tuesday = isTuesday,
                wednesday = isWednesday,
                thursday = isThursday,
                friday = isFriday,
                saturday = isSaturday,
                sunday = isSunday,
                recurring = isRecurring
            )
        }

        fun getSlots(context: Context, alarm: AlarmEntity): ArrayList<Stat> {
            val slots = ArrayList<Stat>()
            try {
                val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = Date()
                val format = "yyyy-MM-dd HH:mm"
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                val dateObj1: Date = if (alarm.time.midDay) {
                    sdf.parse(
                        dateFormat.format(date) + " " + (alarm.time.hours.toInt() + 12) + ":" + alarm.time.minutes
                    )!!
                } else {
                    sdf.parse(
                        dateFormat.format(date) + " " + alarm.time.hours + ":" + alarm.time.minutes
                    )!!
                }

                val dateObj2: Date = sdf.parse(
                    dateFormat.format(date) + " " + "23:59"
                )!!
                Toast.makeText(context, "$dateObj1 $dateObj2", Toast.LENGTH_LONG).show()
                var dif = dateObj1.time
                while (dif < dateObj2.time) {
                    val slot = Date(dif)
                    val sformat = "HH:mm:a"
                    val dateFormat = SimpleDateFormat(sformat, Locale.getDefault())
                    val timeParts = dateFormat.format(slot).toString().split(":")
                    Log.d("TAGTAGTAGTAG", "getSlots: $timeParts")
                    slots.add(
                        Stat(
                            id = (1..999999999).random(),
                            name = alarm.info.name.toString(),
                            hours = (if (timeParts[0].toInt() >= 12) (timeParts[0].toInt() - 12) else timeParts[0].toInt()).toString(),
                            minutes = timeParts[1],
                            midDay = timeParts[2].lowercase() != "am"
                        )
                    )
                    dif += if (alarm.interval.repeatableInterval.unit == "Hour") {
                        (alarm.interval.repeatableInterval.time * 60 * 60000).toLong()
                    } else {
                        (alarm.interval.repeatableInterval.time * 60000).toLong()
                    }
                    if (slots.size > 2) {
                        break
                    }
                }
                Log.d("TAGTAGTAG", "getSlots: $slots")
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "${e.printStackTrace()}", Toast.LENGTH_LONG).show()
            }
            slots.removeAt(0)
            return slots
        }


        const val ASTA_BASE_URL = "https://asta.fit/"
    }
}