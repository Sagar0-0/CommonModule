package fit.asta.health

import android.content.Context
import fit.asta.health.navigation.home_old.categories.data.CategoryData
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.old_course.listing.data.CourseIndexData
import fit.asta.health.old_course.session.data.Exercise

interface ActivityLauncher {
    fun launchSingleSelectActivity(context: Context)
    fun launchMultiSelectActivity(context: Context, uid: String)
    fun launchDurationActivity(context: Context)
    fun launchSchedulerActivity(context: Context, reminder: Reminder? = null)
    fun launchExerciseDetailsActivity(context: Context, courseId: String, sessionId: String)
    fun launchVideoPlayerActivity(context: Context, courseId: String, sessionId: String)
    fun launchVideoPlayerActivity(context: Context, video: Exercise)
    fun launchVideoPlayerActivity(context: Context, videoList: ArrayList<Exercise>)
    fun launchCourseListingActivity(context: Context, data: CategoryData)
    fun launchCourseDetailsActivity(context: Context, data: CourseIndexData)
    fun launchSubscriptionActivity(context: Context)
    fun launchTestimonialsActivity(context: Context)
    fun launchTagsActivity(context: Context, selectedTagId: String?)

}