package fit.asta.health.course.listing.adapter.listeners

import android.content.Context
import fit.asta.health.ActivityLauncher
import fit.asta.health.course.listing.viewmodel.CourseListingViewModel
import fit.asta.health.notify.reminder.data.Reminder
import org.koin.core.KoinComponent
import org.koin.core.inject

class OnCourseClickListenerImpl(
    private val context: Context,
    private val viewModel: CourseListingViewModel
) :
    OnCourseClickListener, KoinComponent {

    private val launcher: ActivityLauncher by inject()

    override fun onCourseClick(position: Int) {
        launcher.launchCourseDetailsActivity(context, viewModel.getCourseDetails(position))
    }

    override fun onPlayClick(position: Int) {
        //Subscription flow
        launcher.launchSubscriptionActivity(context)
        //val course = viewModel.getCourseDetails(position)
        //launcher.launchVideoPlayerActivity(context, course.uid)
    }

    override fun onShareClick(position: Int) {

    }

    override fun onAlarmClick(position: Int) {

        val course = viewModel.getCourseDetails(position)
        val reminder = Reminder(
            id = 0,
            type = Reminder.EXERCISE,
            title = course.title,
            desc = course.subTitle
        )

        launcher.launchSchedulerActivity(context, reminder)
    }

    override fun onFavoriteClick(position: Int) {

    }
}