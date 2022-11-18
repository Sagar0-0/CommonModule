package fit.asta.health

import android.content.Context
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.old_course.details.CourseDetailsActivity
import fit.asta.health.old_course.listing.data.CategoryData
import fit.asta.health.old_course.listing.data.CourseIndexData
import fit.asta.health.old_course.listing.ui.CourseListingActivity
import fit.asta.health.old_course.session.data.Exercise
import fit.asta.health.old_course.session.ui.SessionActivity
import fit.asta.health.old_scheduler.tags.ui.TagsActivity
import fit.asta.health.old_scheduler.ui.ScheduleActivity
import fit.asta.health.old_subscription.SubscriptionActivity
import fit.asta.health.player.video.VideoPlayerActivity

class ActivityLauncherImpl : ActivityLauncher {

    override fun launchSingleSelectActivity(context: Context) {
        TODO("Not yet implemented")
    }

    override fun launchMultiSelectActivity(context: Context, uid: String) {

    }

    override fun launchDurationActivity(context: Context) {
        TODO("Not yet implemented")
    }

    override fun launchSchedulerActivity(context: Context, reminder: Reminder?) {
        ScheduleActivity.launch(context, reminder)
    }

    override fun launchExerciseDetailsActivity(
        context: Context,
        courseId: String,
        sessionId: String
    ) {
        SessionActivity.launch(context, courseId, sessionId)
    }

    override fun launchVideoPlayerActivity(context: Context, courseId: String, sessionId: String) {
        VideoPlayerActivity.launch(context, courseId, sessionId)
    }

    override fun launchVideoPlayerActivity(context: Context, video: Exercise) {
        VideoPlayerActivity.launch(context, arrayListOf(video))
    }

    override fun launchVideoPlayerActivity(context: Context, videoList: ArrayList<Exercise>) {
        VideoPlayerActivity.launch(context, videoList)
    }

    override fun launchCourseListingActivity(context: Context, data: CategoryData) {
        CourseListingActivity.launch(context, data)
    }

    override fun launchCourseDetailsActivity(context: Context, data: CourseIndexData) {
        CourseDetailsActivity.launch(context, data)
    }

    override fun launchSubscriptionActivity(context: Context) {
        SubscriptionActivity.launch(context)
    }

    override fun launchTagsActivity(context: Context, selectedTagId: String?) {
        TagsActivity.launch(context, selectedTagId)
    }
}