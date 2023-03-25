package fit.asta.health.tools.walking.notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import fit.asta.health.R
import fit.asta.health.tools.walking.view.WalkingActivity


class CreateNotification(
    var context : Context,
    var title : String,
    var msg : String
) {

    private val channelId = "steps_channel"
    private val channelName= "steps"
    private val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    fun showNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(context, WalkingActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setSmallIcon(R.drawable.runing)
        notificationBuilder.addAction(R.drawable.baseline_run_circle_24, "Open Notification", pendingIntent)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(msg)
        notificationBuilder.setAutoCancel(true)
        notificationManager.notify(100, notificationBuilder.build())
    }
}