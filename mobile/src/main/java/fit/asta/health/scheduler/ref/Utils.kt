/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fit.asta.health.scheduler.ref

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.widget.TextClock
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import fit.asta.health.scheduler.ref.alarms.AlarmService
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import java.util.Locale

object Utils {


    // Intent action to trigger an instance state change.
    val CHANGE_STATE_ACTION = "change_state"

    // Intent action to show the alarm and dismiss the instance
    val SHOW_AND_DISMISS_ALARM_ACTION = "show_and_dismiss_alarm"

    // Intent action for an AlarmManager alarm serving only to set the next alarm indicators
    val INDICATOR_ACTION = "indicator"

    // System intent action to notify AppWidget that we changed the alarm text.
    val ACTION_ALARM_CHANGED = "com.android.deskclock.ALARM_CHANGED"

    // Extra key to set the desired state change.
    val ALARM_STATE_EXTRA = "intent.extra.alarm.state"

    // Extra key to indicate the state change was launched from a notification.
    val FROM_NOTIFICATION_EXTRA = "intent.extra.from.notification"

    // Extra key to set the global broadcast id.
    val ALARM_GLOBAL_ID_EXTRA = "intent.extra.alarm.global.id"

    // Intent category tags used to dismiss, snooze or delete an alarm
    val ALARM_DISMISS_TAG = "DISMISS_TAG"
    val ALARM_SNOOZE_TAG = "SNOOZE_TAG"
    val ALARM_DELETE_TAG = "DELETE_TAG"

    // Intent category tag used when schedule state change intents in alarm manager.
    val ALARM_MANAGER_TAG = "ALARM_MANAGER"

    /**
     * Utility method to create a proper change state intent.
     *
     * @param context application context
     * @param tag used to make intent differ from other state change intents.
     * @param instance to change state to
     * @param state to change to.
     * @return intent that can be used to change an alarm instance state
     */
    fun createStateChangeIntent(
        context: Context?,
        tag: String?,
        instance: AlarmInstance,
        state: Int?
    ): Intent {
        // This intent is directed to AlarmService, though the actual handling of it occurs here
        // in AlarmStateManager. The reason is that evidence exists showing the jump between the
        // broadcast receiver (AlarmStateManager) and service (AlarmService) can be thwarted by
        // the Out Of Memory killer. If clock is killed during that jump, firing an alarm can
        // fail to occur. To be safer, the call begins in AlarmService, which has the power to
        // display the firing alarm if needed, so no jump is needed.
        val intent: Intent =
            AlarmInstance.createIntent(context, AlarmService::class.java, instance.mId)
        intent.action = CHANGE_STATE_ACTION
        intent.addCategory(tag)
        intent.putExtra(ALARM_GLOBAL_ID_EXTRA, -1)// DataModel.dataModel.globalIntentId)
        if (state != null) {
            intent.putExtra(ALARM_STATE_EXTRA, state.toInt())
        }
        return intent
    }

    /**
     * @return `true` if the device is [Build.VERSION_CODES.O] or later
     */
    val isOOrLater: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O


    fun updateNextAlarm(am: AlarmManager, info: AlarmClockInfo?, op: PendingIntent?) {
        if (info != null && op != null) {
            am.setAlarmClock(info, op)
        }
    }

    /***
     * Formats the time in the TextClock according to the Locale with a special
     * formatting treatment for the am/pm label.
     *
     * @param clock TextClock to format
     * @param includeSeconds whether or not to include seconds in the clock's time
     */
    fun setTimeFormat(clock: TextClock?, includeSeconds: Boolean) {
        // Get the best format for 12 hours mode according to the locale
        clock?.format12Hour = get12ModeFormat(amPmRatio = 0.4f, includeSeconds = includeSeconds)
        // Get the best format for 24 hours mode according to the locale
        clock?.format24Hour = get24ModeFormat(includeSeconds)
    }

    /**
     * @param amPmRatio a value between 0 and 1 that is the ratio of the relative size of the
     * am/pm string to the time string
     * @param includeSeconds whether or not to include seconds in the time string
     * @return format string for 12 hours mode time, not including seconds
     */
    fun get12ModeFormat(amPmRatio: Float, includeSeconds: Boolean): CharSequence {
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

    /**
     * @return a vector-drawable inflated from the given `resId`
     */
    fun getVectorDrawable(context: Context, @DrawableRes resId: Int): VectorDrawableCompat? {
        return VectorDrawableCompat.create(context.resources, resId, context.theme)
    }
}
