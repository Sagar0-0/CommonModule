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
 * limitations under the License
 */
package fit.asta.health.scheduler.ref

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.View
import androidx.annotation.VisibleForTesting
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import java.util.Calendar
import java.util.Locale

/**
 * Static utility methods for Alarms.
 */
object AlarmUtils {
    @JvmStatic
    fun getFormattedTime(context: Context, time: Calendar): String {
        val skeleton = if (DateFormat.is24HourFormat(context)) "EHm" else "Ehma"
        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton)
        return DateFormat.format(pattern, time) as String
    }

    @JvmStatic
    fun getFormattedTime(context: Context, timeInMillis: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = timeInMillis
        return getFormattedTime(context, c)
    }

    @JvmStatic
    fun getAlarmText(context: Context, instance: AlarmInstance, includeLabel: Boolean): String {
        val alarmTimeStr: String = getFormattedTime(context, instance.alarmTime)
        return if (instance.mLabel!!.isEmpty() || !includeLabel) {
            alarmTimeStr
        } else {
            alarmTimeStr + " - " + instance.mLabel
        }
    }

    /**
     * format "Alarm set for 2 days, 7 hours, and 53 minutes from now."
     */
    @VisibleForTesting
    fun formatElapsedTimeUntilAlarm(context: Context, delta: Long): String {
        // If the alarm will ring within 60 seconds, just report "less than a minute."
        var variableDelta = delta
//        val formats = context.resources.getStringArray(R.array.alarm_set)
//        if (variableDelta < DateUtils.MINUTE_IN_MILLIS) {
//            return formats[0]
//        }

        // Otherwise, format the remaining time until the alarm rings.

        // Round delta upwards to the nearest whole minute. (e.g. 7m 58s -> 8m)
        val remainder = variableDelta % DateUtils.MINUTE_IN_MILLIS
        variableDelta += if (remainder == 0L) 0 else DateUtils.MINUTE_IN_MILLIS - remainder
        var hours = variableDelta.toInt() / (1000 * 60 * 60)
        val minutes = variableDelta.toInt() / (1000 * 60) % 60
        val days = hours / 24
        hours %= 24


        val showDays = days > 0
        val showHours = hours > 0
        val showMinutes = minutes > 0

        // Compute the index of the most appropriate time format based on the time delta.
        val index = ((if (showDays) 1 else 0)
                or (if (showHours) 2 else 0)
                or (if (showMinutes) 4 else 0))

        return " String.format(formats[index], daySeq, hourSeq, minSeq)"
    }

    @JvmStatic
    fun popAlarmSetToast(context: Context, alarmTime: Long) {

    }

    @JvmStatic
    fun popAlarmSetSnackbar(snackbarAnchor: View, alarmTime: Long) {

    }
}