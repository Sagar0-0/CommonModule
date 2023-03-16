package fit.asta.health

import android.content.Context
import fit.asta.health.notify.reminder.data.Reminder

interface ActivityLauncher {
    fun launchSchedulerActivity(context: Context, reminder: Reminder? = null)
    fun launchVideoPlayerActivity(context: Context, courseId: String, sessionId: String)
}