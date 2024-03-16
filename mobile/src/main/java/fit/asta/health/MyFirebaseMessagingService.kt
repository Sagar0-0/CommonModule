package fit.asta.health

import android.net.Uri
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.repo.TokenRepo
import fit.asta.health.notify.util.sendNotification
import fit.asta.health.resources.strings.R
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repo: TokenRepo

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title!!, it.body!!, it.icon, it.imageUrl)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        repo.setNewTokenAvailable(token)
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param title FCM message title received.
     * @param desc FCM message body received.
     * @param icon FCM message icon received.
     * @param imageUrl FCM message image url received.
     */
    @Suppress("UNUSED_PARAMETER")
    private fun sendNotification(title: String, desc: String, icon: String?, imageUrl: Uri?) {

        applicationContext.sendNotification(
            getString(R.string.title_reminder),
            title,
            desc,
            imageUrl
        )
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
