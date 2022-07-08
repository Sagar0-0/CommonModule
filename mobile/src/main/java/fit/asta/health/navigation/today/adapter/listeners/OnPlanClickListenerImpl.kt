package fit.asta.health.navigation.today.adapter.listeners

import android.content.Context
import fit.asta.health.ActivityLauncher
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class OnPlanClickListenerImpl(
    private val context: Context,
    private val viewModel: TodayPlanViewModel
) :
    OnPlanClickListener, KoinComponent {

    private val launcher: ActivityLauncher by inject()

    override fun onPlanClick(position: Int) {
        val plan = viewModel.getPlanDetails(position)
        launcher.launchExerciseDetailsActivity(context, plan.courseId, plan.sessionId)
    }

    override fun onPlayClick(position: Int) {
        val plan = viewModel.getPlanDetails(position)
        launcher.launchVideoPlayerActivity(context, plan.courseId, plan.sessionId)
        //launcher.launchSubscriptionActivity(context)
    }

    override fun onRescheduling(position: Int) {
        //val plan =viewModel.getPlanDetails(position)
        launcher.launchSchedulerActivity(context,null)
    }
}