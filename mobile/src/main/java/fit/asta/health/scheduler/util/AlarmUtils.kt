package fit.asta.health.scheduler.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fit.asta.health.scheduler.AlarmBroadcastReceiver
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import java.util.*

class AlarmUtils {
    companion object {
        fun schedule(context: Context, alarmEntity: AlarmEntity) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmBroadcastReceiver::class.java)
            val bundle = Bundle()
            bundle.putParcelable(Constants.ARG_ALARM_OBJET, alarmEntity)
            intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)
            val alarmPendingIntent =
                PendingIntent.getBroadcast(context, alarmEntity.alarmId, intent, 0)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            if (alarmEntity.time.midDay) {
                calendar[Calendar.HOUR_OF_DAY] = alarmEntity.time.hours.toInt() + 12
            } else {
                calendar[Calendar.HOUR_OF_DAY] = alarmEntity.time.hours.toInt()
            }
            calendar[Calendar.MINUTE] = alarmEntity.time.minutes.toInt()
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            val toastText =
                "One Time Alarm scheduled at ${alarmEntity.time.hours}:${alarmEntity.time.minutes}"

            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
            alarmEntity.status = true
        }

        fun cancelAlarm(context: Context, alarmEntity: AlarmEntity) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmBroadcastReceiver::class.java)
            val alarmPendingIntent =
                PendingIntent.getBroadcast(context, alarmEntity.alarmId, intent, 0)
            alarmManager.cancel(alarmPendingIntent)
            alarmEntity.status = false
            @SuppressLint("DefaultLocale") val toastText =
                String.format(
                    "Alarm cancelled for %02d:%02d",
                    alarmEntity.time.hours,
                    alarmEntity.time.minutes
                )
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            Log.i("cancel", toastText)
        }
    }
}