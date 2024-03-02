package fit.asta.health.feature.scheduler.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.app.NotificationCompat
import fit.asta.health.common.utils.Constants
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.resources.drawables.R
import javax.inject.Inject

class ToolNotificationHelper @Inject constructor(
    val context: Context,
    val builder: NotificationCompat.Builder,
    val alarm: AlarmEntity,
    private val pendingIntentSnooze: PendingIntent,
    private val pendingIntentStop: PendingIntent,
    private val notificationManager: NotificationManager
) :
    ToolViewListener {


    companion object {
        const val ACTION_ADD_WATER = "ACTION_ADD_WATER"
        const val ACTION_ADD_JUICE = "ACTION_ADD_JUICE"
        lateinit var listener: ToolViewListener
    }

    private var remoteViews: RemoteViews

    init {
        remoteViews = getToolRemoteView()
        listener = this
    }


    fun getToolRemoteView(): RemoteViews {
        return when (alarm.info.tag.lowercase()) {
            Constants.ToolTag.WATER -> {
//                createWaterNotificationAlarm()
                setDefault()
            }

            else -> {
                setDefault()
            }
        }
    }

    private fun createWaterNotificationAlarm(
    ): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.water_tool_notification_small)
        remoteViews.setOnClickPendingIntent(R.id.wtr_btn_stop, pendingIntentStop)
        remoteViews.setOnClickPendingIntent(R.id.wtr_btn_snooze, pendingIntentSnooze)
        val alarmName: String = alarm.info.name
        val updateWater = Intent(context, ToolsBroadcastReceiver::class.java).apply {
            action = ACTION_ADD_WATER
            putExtra("notification_id", alarm.hashCode())
        }
        val pendingIntentUpdateWater = PendingIntent.getBroadcast(
            context,
            555,
            updateWater,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setOnClickPendingIntent(R.id.add_water, pendingIntentUpdateWater)
        val updateJuice = Intent(context, ToolsBroadcastReceiver::class.java).apply {
            action = ACTION_ADD_JUICE
            putExtra("notification_id", alarm.hashCode())
        }
        val pendingIntentUpdateJuice = PendingIntent.getBroadcast(
            context,
            666,
            updateJuice,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setOnClickPendingIntent(R.id.add_juice, pendingIntentUpdateJuice)
        remoteViews.setTextViewText(
            R.id.wtr_tv_title,
            "Hello iam water wefjbcl bvajdshvb sadiujvkbcasdl asiudvbjalsj sadiuvb "
        )
        remoteViews.setTextViewText(R.id.tv_content, alarm.info.tag)
        return remoteViews
    }

    private fun setDefault(
    ): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.notification_alarm_small)
        remoteViews.setOnClickPendingIntent(R.id.btnStop, pendingIntentStop)
        remoteViews.setOnClickPendingIntent(R.id.btn_snooze, pendingIntentSnooze)
        val alarmName: String = alarm.info.name
        remoteViews.setTextViewText(R.id.tv_title, alarmName)
        remoteViews.setTextViewText(R.id.tv_content, alarm.info.tag)
        return remoteViews
    }

    override fun updateWaterTool(text: String) {
        Log.d("hello", "updateWaterTool: $text")
        remoteViews.setTextViewText(R.id.wtr_tv_title, text)
        builder.setCustomContentView(remoteViews)
        builder.setCustomHeadsUpContentView(remoteViews)
        notificationManager.notify(alarm.hashCode(), builder.build())
    }

    override fun updateTextView(id: Int, text: String) {

    }

    override fun updateImageView(id: Int, image: Int) {

    }
}

interface ToolViewListener {
    fun updateWaterTool(text: String)

    fun updateTextView(@IdRes id: Int, text: String)

    fun updateImageView(@IdRes id: Int, @DrawableRes image: Int)

}







