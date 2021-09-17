package fit.asta.health.notify.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import fit.asta.health.R
import fit.asta.health.notify.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        context.sendNotification(
            context.getString(R.string.title_reminder),
            "Title",
            "Content...",
            null
        )
    }
}