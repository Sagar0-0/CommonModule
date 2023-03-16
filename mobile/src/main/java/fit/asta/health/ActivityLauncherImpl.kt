package fit.asta.health

import android.content.Context
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.player.video.VideoPlayerActivity

class ActivityLauncherImpl : ActivityLauncher {

    override fun launchSchedulerActivity(context: Context, reminder: Reminder?) {
        //SchedulerActivity.launch(context, reminder)
    }

    override fun launchVideoPlayerActivity(context: Context, courseId: String, sessionId: String) {
        VideoPlayerActivity.launch(context, courseId, sessionId)
    }
}