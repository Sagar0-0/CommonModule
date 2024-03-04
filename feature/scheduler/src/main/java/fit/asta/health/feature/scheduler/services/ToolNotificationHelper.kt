package fit.asta.health.feature.scheduler.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.core.app.NotificationCompat
import coil.ImageLoader
import coil.request.ImageRequest
import fit.asta.health.common.utils.Constants
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.resources.drawables.R
import javax.inject.Inject

class ToolNotificationHelper @Inject constructor(
    val context: Context,
    val builder: NotificationCompat.Builder,
    val alarm: AlarmEntity,
    private val pendingIntentSnooze: PendingIntent,
    private val pendingIntentStop: PendingIntent,
    private val notificationManager: NotificationManager,
    private val isConnected: Boolean
) :
    ToolViewListener {


    companion object {
        const val ACTION_ADD_WATER = "ACTION_ADD_WATER"
        const val ACTION_ADD_JUICE = "ACTION_ADD_JUICE"
        lateinit var listener: ToolViewListener
    }

    private var remoteViews: RemoteViews
    private var expandedRemoteView: RemoteViews
    val loader = ImageLoader(context)

    init {
        remoteViews = getToolRemoteView()
        expandedRemoteView = expandedNotification()
        listener = this
        setExpandedNotification()
    }


    fun getToolRemoteView(): RemoteViews {
        return when (alarm.info.tag.lowercase()) {
            Constants.ToolTag.WATER -> {
                createWaterNotificationAlarm()
//                setDefault()
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
            "Water reminder"
        )
        remoteViews.setTextViewText(R.id.tv_content, alarm.info.tag)
        return remoteViews
    }

    private fun setExpandedNotification() {
        builder.setCustomBigContentView(expandedRemoteView)
        notificationManager.notify(alarm.hashCode(), builder.build())
    }

    private fun expandedWaterRemoteView(): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.water_tool_notification_large)
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
        updateImageView(R.id.iv_water_notification, alarm.info.url)
        remoteViews.setTextViewText(
            R.id.wtr_tv_title,
            "Water reminder"
        )
        remoteViews.setTextViewText(R.id.tv_content, alarm.info.tag)
        return remoteViews
    }

    private fun expandedNotification(): RemoteViews {
        return when (alarm.info.tag.lowercase()) {
            Constants.ToolTag.WATER -> {
                expandedWaterRemoteView()
//                setDefault()
            }

            else -> {
                setDefault()
            }
        }
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
        remoteViews.setTextViewText(id, text)
        expandedRemoteView.setTextViewText(id, text)
        builder.setCustomContentView(remoteViews)
        builder.setCustomHeadsUpContentView(remoteViews)
        builder.setCustomBigContentView(expandedRemoteView)
        notificationManager.notify(alarm.hashCode(), builder.build())
    }

    override fun updateImageView(id: Int, image: Int) {

//        remoteViews.setImageViewBitmap(id)
    }

    override fun updateImageView(id: Int, image: String) {
        try {
            val req = ImageRequest.Builder(context)
                .data(
                    if (isConnected) getImageUrl(url = image)
                    else R.drawable.weatherimage
                )// demo link
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    val bitmap1: Bitmap? = null
                    expandedRemoteView.setImageViewBitmap(id, bitmap)
                    notificationManager.notify(alarm.hashCode(), builder.build())
                }
                .build()
            loader.enqueue(req)

        } catch (_: Exception) {
            val req = ImageRequest.Builder(context)
                .data(R.drawable.weatherimage)
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    val bitmap1: Bitmap? = null
                    expandedRemoteView.setImageViewBitmap(id, bitmap)
                    notificationManager.notify(alarm.hashCode(), builder.build())
                    notificationManager.notify(alarm.hashCode(), builder.build())
                }
                .build()
            loader.enqueue(req)
        }
    }
}

interface ToolViewListener {
    fun updateWaterTool(text: String)

    fun updateTextView(@IdRes id: Int, text: String)

    fun updateImageView(@IdRes id: Int, @DrawableRes image: Int)
    fun updateImageView(@IdRes id: Int, image: String)

}







