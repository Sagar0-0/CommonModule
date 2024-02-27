package fit.asta.health.feature.scheduler.util

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import fit.asta.health.data.scheduler.db.entity.AlarmInstance
import fit.asta.health.feature.scheduler.services.AlarmBroadcastReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {
    const val PRE_ALARM_STATE = 1
    const val CURRENT_ALARM_STATE = 2
    const val SNOOZE_CURRENT_ALARM_STATE = 3
    const val PRE_END_ALARM_STATE = 4
    const val END_ALARM_STATE = 5
    const val SNOOZE_END_ALARM_STATE = 6
    const val MISSED_ALARM_STATE = 7

    val CHANGE_STATE_ACTION = "change_state"
    val SNOOZE_ACTION = "snooze_state"
    val DISMISS_ACTION = "dismiss_state"
    val SKIP_ALARM_ACTION = "skip_alarm_state"
    val ALARM_STATE_EXTRA = "intent.extra.alarm.state"
    fun createStateChangeIntent(
        context: Context?,
        instance: AlarmInstance,
        state: Int?
    ): Intent {

        val intent: Intent =
            AlarmInstance.createIntent(
                context,
                AlarmBroadcastReceiver::class.java,
                instance.mAlarmId
            )
        intent.action = CHANGE_STATE_ACTION
        state?.let { intent.putExtra(ALARM_STATE_EXTRA, state) }
        return intent
    }

    /**
     * @param amPmRatio a value between 0 and 1 that is the ratio of the relative size of the
     * am/pm string to the time string
     * @param includeSeconds whether or not to include seconds in the time string
     * @return format string for 12 hours mode time, not including seconds
     */
    fun get12ModeFormat(amPmRatio: Float = 0.4f, includeSeconds: Boolean): CharSequence {
        var pattern = DateFormat.getBestDateTimePattern(
            Locale.getDefault(),
            if (includeSeconds) "hmsa" else "hma"
        )
        if (amPmRatio <= 0) {
            pattern = pattern.replace("a".toRegex(), "").trim { it <= ' ' }
        }

        // Replace spaces with "Hair Space"
        pattern = pattern.replace(" ".toRegex(), "\u200A")
        // Build a spannable so that the am/pm will be formatted
        val amPmPos = pattern.indexOf('a')
        if (amPmPos == -1) {
            return pattern
        }

        val sp: Spannable = SpannableString(pattern)
        sp.setSpan(
            RelativeSizeSpan(amPmRatio), amPmPos, amPmPos + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sp.setSpan(
            StyleSpan(Typeface.NORMAL), amPmPos, amPmPos + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        sp.setSpan(
            TypefaceSpan("sans-serif"), amPmPos, amPmPos + 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return sp
    }

    fun get24ModeFormat(includeSeconds: Boolean): CharSequence {
        return DateFormat.getBestDateTimePattern(
            Locale.getDefault(),
            if (includeSeconds) "Hms" else "Hm"
        )
    }

}