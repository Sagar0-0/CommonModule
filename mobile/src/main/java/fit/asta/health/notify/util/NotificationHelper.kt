package fit.asta.health.notify.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import fit.asta.health.MainActivity
import fit.asta.health.R
import fit.asta.health.notify.receiver.SnoozeReceiver
import fit.asta.health.common.utils.getUriFromResourceId


private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0
private const val FLAGS = 0

/**
 * Creates a notification channel.
 * @param name, title to display.
 * @param desc, description to display.
 * @param importance, importance of the notification channel.
 * @param uriSound, sound to play.
 * @param showBadge, message to display.
 * @param isVibrate, set vibration of the notifications.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun Context.createNotificationChannel(
    name: String,
    desc: String?,
    importance: Int,
    uriSound: Uri?,
    showBadge: Boolean,
    isVibrate: Boolean
) {
    val channelId = "${this.packageName}-$name"
    val channel = NotificationChannel(channelId, name, importance).apply {

        description = desc

        setSound(
            uriSound,
            if (uriSound == null) null else AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
        )

        enableVibration(isVibrate)
        vibrationPattern = vibrationPattern()

        enableLights(true)
        lightColor = Color.GREEN

        lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

        setBypassDnd(true)
        setShowBadge(showBadge)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            setAllowBubbles(true)
    }

    this.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
}

/**
 * Creates notification builder.
 *
 * @param channelName, To channelize the notification.
 * @param title, title to display.
 * @param msg, message to display.
 * @param onGoing, Not to closed by the user.
 */
private fun Context.createNotificationBuilder(
    channelName: String,
    title: String,
    msg: String?,
    onGoing: Boolean
): NotificationCompat.Builder {

    /*val mp = MediaPlayer.create(this, getUriFromResourceId(R.raw.alarm))
    mp.start()

    val r = RingtoneManager.getRingtone(this, getUriFromResourceId(R.raw.alarm))
    r.play()
    */

    val channelId = "${this.packageName}-$channelName"
    return NotificationCompat.Builder(this, channelId).apply {

        legacyNotificationSettings(this)
        setSmallIcon(R.drawable.ic_notifications)
        setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_toolbar_logo))
        setAutoCancel(true)
        setLocalOnly(false) //To show on projector/tv/other devices
        setWhen(System.currentTimeMillis() + 2000)

        setContentTitle(title)
        setContentText(msg)
        setOngoing(onGoing)
    }
}

private fun Context.legacyNotificationSettings(builder: NotificationCompat.Builder) {

    builder.setDefaults(NotificationCompat.DEFAULT_ALL)
    builder.priority = NotificationCompat.PRIORITY_MAX
    builder.setSound(getUriFromResourceId(R.raw.alarm))
    builder.setVibrate(vibrationPattern())
    builder.setLights(Color.GREEN, 2000, 2000)
    builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
    builder.setNumber(1)
}

private fun vibrationPattern() = longArrayOf(0, 100, 200, 300, 400, 500, 700, 900, 1000)

/**
 * shows the text notification.
 *
 * @param channelName, To channelize the notification.
 * @param title, title to display.
 * @param msg, message to display.
 * @param onGoing, Not to closed by the user.
 */
fun Context.showTextNotification(
    channelName: String,
    title: String,
    msg: String,
    onGoing: Boolean = false
) {

    val builder = createNotificationBuilder(
        channelName,
        title,
        msg,
        onGoing
    )

    notify(builder)
}

/**
 * shows the big text notification.
 *
 * @param channelName, To channelize the notification.
 * @param title, title to display.
 * @param msg, message to display.
 * @param summary, summary to display.
 * @param onGoing, Not to closed by the user.
 */
fun Context.showBigTextNotification(
    channelName: String,
    title: String,
    msg: String,
    summary: String?,
    onGoing: Boolean = false
) {

    val builder = createNotificationBuilder(
        channelName,
        title,
        null,
        onGoing
    )

    builder.apply {

        val bigText = NotificationCompat.BigTextStyle()
        bigText.bigText(msg)
        bigText.setSummaryText(summary)
        setStyle(bigText)
    }

    notify(builder)
}

/**
 * shows the big text notification.
 *
 * @param channelName, To channelize the notification.
 * @param title, title to display.
 * @param msg, message to display.
 * @param summary, summary to display.
 * @param onGoing, Not to closed by the user.
 */
@Suppress("UNUSED_PARAMETER")
fun Context.showInboxNotification(
    channelName: String,
    title: String,
    msg: String,
    summary: String?,
    onGoing: Boolean = false
) {

    val builder = createNotificationBuilder(
        channelName,
        title,
        null,
        onGoing
    )

    builder.apply {

        //Implement inbox style notification
        val iStyle = NotificationCompat.InboxStyle()
        iStyle.addLine("Message 1.")
        iStyle.addLine("Message 2.")
        iStyle.addLine("Message 3.")
        iStyle.addLine("Message 4.")
        iStyle.addLine("Message 5.")
        iStyle.setSummaryText("+2 more")
        setStyle(iStyle)
    }

    notify(builder)
}

/**
 * shows the image notification.
 *
 * @param channelName, To channelize the notification.
 * @param title, title to display.
 * @param msg, message to display.
 * @param uriImage, image to display.
 * @param onGoing, Not to closed by the user.
 */
fun Context.showImageNotification(
    channelName: String,
    title: String,
    msg: String,
    uriImage: Uri,
    onGoing: Boolean = false
) {

    val builder = createNotificationBuilder(
        channelName,
        title,
        msg,
        onGoing
    )

    notifyWithImage(this, builder, uriImage)
}

private fun notifyWithImage(context: Context, builder: NotificationCompat.Builder, uriImage: Uri) {

    Glide.with(context)
        .asBitmap()
        .load(uriImage)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(object : CustomTarget<Bitmap?>() {

            override fun onLoadCleared(placeholder: Drawable?) {

                context.notify(builder)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {

                val picStyleBig = NotificationCompat.BigPictureStyle()
                    .bigPicture(resource)
                builder.setStyle(picStyleBig)

                context.notify(builder)
            }
        })
}

/**
 * Builds and delivers the notification.
 *
 * @param channelName, To channelize the notification.
 * @param title, title to display.
 * @param msg, message to display.
 * @param uriImage, image to display.
 */
fun Context.sendNotification(
    channelName: String,
    title: String,
    msg: String,
    uriImage: Uri?,
    onGoing: Boolean = false
) {

    // Build the notification
    val builder = createNotificationBuilder(
        channelName,
        title,
        msg,
        onGoing
    )

    val intentNotify = Intent(this, MainActivity::class.java)
    val intentPending = PendingIntent.getActivity(
        this,
        NOTIFICATION_ID,
        intentNotify,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val intSnooze = Intent(this, SnoozeReceiver::class.java)
    val intSnoozePending = PendingIntent.getBroadcast(
        this,
        REQUEST_CODE, intSnooze,
        FLAGS
    )

    val strSnooze = this.getString(R.string.title_snooze)
    builder.apply {

        setContentIntent(intentPending)
        setAutoCancel(true)
        addAction(R.drawable.ic_notifications, strSnooze, intSnoozePending)
    }

    if (uriImage != null) {

        notifyWithImage(this, builder, uriImage)
    }
}

private fun Context.notify(builder: NotificationCompat.Builder) {

    getNotificationMgr().notify(NOTIFICATION_ID, builder.build())
    wakeUpScreen()
}

private fun Context.getNotificationMgr() =
    ContextCompat.getSystemService(this, NotificationManager::class.java) as NotificationManager

private fun Context.wakeUpScreen() {

    val tag = "${this.packageName}-${this.getString(R.string.app_name)}:wakeUp"
    val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager

    @Suppress("DEPRECATION")
    val bIsWake =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) pm.isInteractive else pm.isScreenOn

    if (bIsWake) {

        val wl = pm.newWakeLock(
            PowerManager.ACQUIRE_CAUSES_WAKEUP or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or PowerManager.ON_AFTER_RELEASE,
            tag
        )
        wl.acquire(3000)
        //wl.release()
    }
}

/**
 * Cancels all notifications.
 *
 */
fun Context.cancelNotifications() {

    getNotificationMgr().cancelAll()
}