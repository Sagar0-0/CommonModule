package fit.asta.health

import android.content.Context
import fit.asta.health.course.details.CourseDetailsActivity
import fit.asta.health.course.listing.data.CourseIndexData
import fit.asta.health.course.listing.ui.CourseListingActivity
import fit.asta.health.course.session.data.Exercise
import fit.asta.health.course.session.ui.SessionActivity
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.player.VideoPlayerActivity
import fit.asta.health.schedule.tags.ui.TagsActivity
import fit.asta.health.schedule.ui.ScheduleActivity
import fit.asta.health.subscription.SubscriptionActivity
import fit.asta.health.testimonials.ui.TestimonialsActivity

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

    override fun launchTestimonialsActivity(context: Context) {
        TestimonialsActivity.launch(context)
    }

    override fun launchTagsActivity(context: Context, selectedTagId: String?) {
        TagsActivity.launch(context, selectedTagId)
    }
}