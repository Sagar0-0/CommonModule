package fit.asta.health.feature.scheduler.services


import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.utils.Constants.ALARM_NOTIFICATION_TAG
import fit.asta.health.common.utils.Constants.CHANNEL_ID
import fit.asta.health.common.utils.Constants.CHANNEL_ID_OTHER
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.data.scheduler.db.AlarmDao
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.ui.AlarmScreenActivity
import fit.asta.health.feature.scheduler.util.Constants
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.feature.scheduler.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import fit.asta.health.resources.drawables.R as DrawR


@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class AlarmService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var alarmDao: AlarmDao

    @Inject
    lateinit var stateManager: StateManager

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var prefManager: PrefManager

    private var alarmEntity: AlarmEntity? = null
    private lateinit var ringtone: Uri
    private lateinit var vibrator: Vibrator
    private lateinit var partialWakeLock: PowerManager.WakeLock
    private var mTimer: Timer? = null
    private var mTimer1: Timer? = null
    private var notificationState: Boolean = true
    private var isConnected: Boolean = false
    private lateinit var player: Player
    private lateinit var listener: Player.Listener

    @androidx.annotation.OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        // Get an instance of the ConnectivityManager class
        scope.launch {
            prefManager.userData
                .map {
                    it.notificationStatus
                }.collectLatest { notificationState = it }
        }
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Get the network capabilities of the active network
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        // Check the network capabilities to see if the device is connected to a network
        isConnected =
            if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                true
            } else networkCapabilities != null && networkCapabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        mTimer = Timer()
        mTimer?.schedule(object : TimerTask() {
            override fun run() {
                Log.d("TAG", "run:1 ")
                if (alarmEntity != null) {
                    notification(
                        message = "Alarm is skipped ",
                        alarmName = alarmEntity!!.info.name,
                        tag = alarmEntity!!.info.tag,
                        nId = alarmEntity!!.hashCode()
                    )
                    stopService(Intent(applicationContext, AlarmScreenActivity::class.java))
                    stateManager.missedAlarm(applicationContext, alarmEntity!!)
                    alarmEntity = null
                } else {
                    stopSelf()
                }
            }
        }, 3 * 60 * 1000)
        mTimer1 = Timer()
        mTimer1?.schedule(object : TimerTask() {
            override fun run() {
                Log.d("TAG", "run: 2")
                if (alarmEntity == null) {
                    stateManager.stopService(applicationContext)
                }
            }
        }, (3 * 60 * 1000).plus(5 * 1000))
        partialWakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmService::WakeLock").apply {
                acquire(3 * 60 * 1000L /*3 minutes*/)
            }
        }
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .setUsage(C.USAGE_ALARM)
            .build()
        listener = object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                player.apply {
                    setMediaItem(MediaItem.fromUri(ringtone))
                    prepare()
                    play()
                }
            }
        }
        player = ExoPlayer.Builder(this.applicationContext)
            .setMediaSourceFactory(
                ProgressiveMediaSource.Factory(DefaultDataSource.Factory(this.applicationContext))
            )
            .setAudioAttributes(audioAttributes, false)
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build().apply {
                playWhenReady = false
                repeatMode = Player.REPEAT_MODE_ONE
                addListener(listener)
            }
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION") getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(
            this.baseContext, RingtoneManager.TYPE_ALARM
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //         getting bundle from intent

        if (intent != null) {
            val id: Long = intent.getLongExtra("id", -1)
            if (alarmEntity != null) {
                if (alarmEntity!!.alarmId != id) {
                    notification(
                        message = "Alarm is missed ",
                        alarmName = alarmEntity!!.info.name,
                        tag = alarmEntity!!.info.tag,
                        nId = alarmEntity!!.hashCode()
                    )
                    stateManager.dismissAlarm(applicationContext, alarmEntity!!.alarmId)
                    stateManager.updateMissedAlarm(alarmEntity!!)
                }
            } else {
                scope.launch {
                    alarmDao.getAlarm(id)?.let { alarm ->
                        alarmEntity = alarm
                        stateManager.updateMissedAlarm(alarm, false)
                        try {
                            player.apply {
//                                if (isConnected) setMediaItem(MediaItem.fromUri(alarm.tone.uri))
//                                else setMediaItem(MediaItem.fromUri(ringtone))
                                setMediaItem(MediaItem.fromUri(ringtone))
                                prepare()
                            }
                        } catch (_: Exception) {

                        }
                        if (notificationState || alarm.important) startAlarmWithMode(alarm)
                        else {
                            Log.d("notificationAlarm", "onStartCommand: ")
                            notification(
                                message = "Alarm Notification ",
                                alarmName = alarm.info.name,
                                tag = alarm.info.tag,
                                nId = alarm.hashCode()
                            )
                            stateManager.updateMissedAndDismissAlarm(
                                alarm,
                                isMissed = false,
                                applicationContext
                            )

                            alarmEntity = null
                        }
                    }

                }
            }
        }
        return START_STICKY
    }

    private fun startAlarmWithMode(alarm: AlarmEntity) {
        player.play()
        if (alarm.mode == "Notification") notificationAlarm(alarm)
        else splashAlarm(alarm)
    }

    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun notificationAlarm(
        alarm: AlarmEntity
    ) {
        val picStyleBig = NotificationCompat.BigPictureStyle()
        val loader = ImageLoader(this)
        val stopIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            action = Utils.DISMISS_ACTION
            putExtra("id", alarm.alarmId)
        }
        val pendingIntentStop = PendingIntent.getBroadcast(
            this, 555, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val snoozeIntent = Intent(this, AlarmBroadcastReceiver::class.java).apply {
            action = Utils.SNOOZE_ACTION
            putExtra("id", alarm.alarmId)
        }
        val pendingIntentSnooze = PendingIntent.getBroadcast(
            this,
            666,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val alarmName: String = alarm.info.name
        val pendingIntent = getMainActivityPendingIntent(applicationContext, alarm.info.tag)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24)
            .setAutoCancel(false)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(0, "Snooze", pendingIntentSnooze)
            .addAction(0, "Stop", pendingIntentStop)
            .setContentTitle(alarmName)
//            .setAllowSystemGeneratedContextualActions(true)
//            .setCustomContentView(remoteViews)
            .setContentText(alarm.info.tag)
            .setDeleteIntent(if (alarm.important) pendingIntentSnooze else pendingIntentStop)
        val toolViewHelper =
            ToolNotificationHelper(
                this,
                builder,
                alarm,
                pendingIntentSnooze,
                pendingIntentStop,
                notificationManager,
                isConnected
            )
        val remoteViews = toolViewHelper.getToolRemoteView()
        builder.setCustomHeadsUpContentView(remoteViews)
            .setCustomContentView(remoteViews)
//            .setCustomBigContentView(null)
//        ToolsStateManager(toolViewHelper as ToolViewListener)
//        notificationManager.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONGOING_EVENT
        val notification = builder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONGOING_EVENT
        startForGroundService(
            notification = notification,
            id = alarm.hashCode(),
            vibrationPattern = Constants.getVibrationPattern(alarm.vibration.pattern)
        )

        try {
            val req = ImageRequest.Builder(this)
                .data(
                    if (isConnected) getImageUrl(url = alarm.info.url)
                    else DrawR.drawable.weatherimage
                )// demo link
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    val bitmap1: Bitmap? = null
                    picStyleBig.bigPicture(bitmap).bigLargeIcon(bitmap1)
                    builder.setStyle(picStyleBig).setLargeIcon(bitmap)
                    notificationManager.notify(alarm.hashCode(), notification)
                }
                .build()
            loader.enqueue(req)

        } catch (_: Exception) {
            val req = ImageRequest.Builder(this)
                .data(DrawR.drawable.weatherimage)
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    val bitmap1: Bitmap? = null
                    picStyleBig.bigPicture(bitmap).bigLargeIcon(bitmap1)
                    builder.setStyle(picStyleBig).setLargeIcon(bitmap)
                    notificationManager.notify(alarm.hashCode(), notification)
                }
                .build()
            loader.enqueue(req)
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun splashAlarm(
        alarm: AlarmEntity
    ) {
        val splashIntent = Intent(this, AlarmScreenActivity::class.java).apply {
            putExtra("id", alarm.alarmId)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, splashIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmName: String = alarm.info.name
        val bigTextStyle = NotificationCompat.BigTextStyle().bigText(alarm.info.description)
        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(alarmName)
                .setContentText(alarm.info.tag)
                .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX) // set base on important in alarm entity
                .setFullScreenIntent(pendingIntent, true).setStyle(bigTextStyle).setWhen(0)
        startForGroundService(
            notification = builder.build(),
            id = alarm.hashCode(),
            vibrationPattern = Constants.getVibrationPattern(alarm.vibration.pattern)
        )
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private fun startForGroundService(
        notification: Notification?, id: Int, vibrationPattern: LongArray
    ) {
        notification?.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONGOING_EVENT
        vibrator.vibrate(
            VibrationEffect.createWaveform(
                vibrationPattern, 0
            )
        )
        Log.d("alarmtest", "startForGroundService")

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            startForeground(id, notification)
        } else {
            if (notification != null) {
                startForeground(
                    id, notification,
                    FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                )
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        alarmEntity = null
        mTimer?.cancel()
        mTimer1?.cancel()
        vibrator.cancel()
        partialWakeLock.release()
        player.removeListener(listener)
        player.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun notification(message: String, alarmName: String, tag: String, nId: Int) {
        val bigTextStyle = NotificationCompat.BigTextStyle().bigText(message)
        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID_OTHER)
                .setContentTitle(alarmName)
                .setContentText(tag)
                .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setStyle(bigTextStyle)
                .setAutoCancel(true)
        notificationManager.notify(nId.plus(1), builder.build())
    }
}

fun getMainActivityPendingIntent(context: Context, tag: String): PendingIntent {
    val intent = Intent(context, Class.forName("fit.asta.health.MainActivity"))
    intent.putExtra(ALARM_NOTIFICATION_TAG, tag)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    return PendingIntent.getActivity(
        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}