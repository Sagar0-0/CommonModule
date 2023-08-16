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
import java.util.Locale

object Utils {


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
