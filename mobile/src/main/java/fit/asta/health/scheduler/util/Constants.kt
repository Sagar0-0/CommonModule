package fit.asta.health.scheduler.util

import android.app.KeyguardManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.Time24hr
import xyz.aprildown.ultimateringtonepicker.UltimateRingtonePicker
import java.time.LocalTime
import java.time.temporal.ChronoUnit

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
        const val BUNDLE_ALARM_OBJECT_NOTIFICATION = "bundle_alarm_object_notification"
        const val ARG_ALARM_OBJET = "arg_alarm_object"

        const val BUNDLE_VARIANT_INTERVAL_OBJECT = "bundle_variant_interval_object"
        const val BUNDLE_VARIANT_INTERVAL_OBJECT_NOTIFICATION =
            "bundle_variant_interval_object_notification"
        const val ARG_VARIANT_INTERVAL_ALARM_OBJECT = "arg_variant_interval_alarm_object"
        const val ARG_VARIANT_INTERVAL_OBJECT = "arg_variant_interval_object"


        const val BUNDLE_PRE_NOTIFICATION_OBJECT = "bundle_pre_notification_object"
        const val ARG_PRE_NOTIFICATION_OBJET = "arg_post_notification_object"

        const val BUNDLE_POST_NOTIFICATION_OBJECT = "bundle_post_notification_object"
        const val ARG_POST_NOTIFICATION_OBJET = "arg_post_notification_object"

        const val SPOTIFY_SONG_KEY_URI = "bundle_spotify_song"
        const val SPOTIFY_SONG_KEY_TYPE = "bundle_spotify_song_type"

        const val SPOTIFY_AUTH_REQUEST_CODE = 1337
        const val SPOTIFY_REDIRECT_URI = "fit.asta.health://callback"
        const val SPOTIFY_CLIENT_ID = "8f5ba8ca7b2a479aa6f766c931a6e8c4"
        const val SPOTIFY_SCOPES =
            "user-follow-read,user-read-recently-played,user-read-playback-position,user-top-read,playlist-read-private,app-remote-control,streaming,user-read-email,user-read-private,user-library-read"

        // Spotify Web API
        const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1/"

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
                val keyguardManager =
                    context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
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

        fun getTimeDifference(targetTime24hr: Time24hr): Long {
            val currentTime = LocalTime.now()
            val targetTime = LocalTime.of(targetTime24hr.hour, targetTime24hr.min)

            // If the target time is before the current time, add 24 hours to the target time
            // to get the correct time difference for today
            if (targetTime.isBefore(currentTime)) {
                targetTime.plusHours(24)
            }

            return currentTime.until(targetTime, ChronoUnit.MINUTES)
        }

    }
}