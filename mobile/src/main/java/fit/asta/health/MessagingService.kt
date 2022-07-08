package fit.asta.health

import android.net.Uri
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import fit.asta.health.notify.util.sendNotification

class MessagingService : FirebaseMessagingService() {

    companion object {

        private const val TAG = "MsgService"
    }

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

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {

        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    /**
     * Persist token to third-party servers.
     *
     * @param token The new token.
     */
    @Suppress("UNUSED_PARAMETER")
    private fun sendRegistrationToServer(token: String) {

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Log.d(TAG, "Refreshed token: $token")
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
}