package fit.asta.health.auth.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.fcm.repo.TokenRepo
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repo: TokenRepo
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // TODO: Handle types of notifications
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            remoteMessage.data["title"]?.let { sendNotification(it) }
        }
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        repo.setNewTokenAvailable(token)
    }

    private fun sendNotification(messageBody: String) {
        //TODO: THIS IS USED WHEN APP IS IN FOREGROUND
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
