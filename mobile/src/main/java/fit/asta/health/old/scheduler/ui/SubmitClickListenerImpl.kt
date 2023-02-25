package fit.asta.health.old.scheduler.ui

import android.view.View
import fit.asta.health.old.scheduler.viewmodel.ScheduleViewModel

class SubmitClickListenerImpl(val viewModel: ScheduleViewModel) : View.OnClickListener {

    override fun onClick(view: View) {

        viewModel.submitScheduledPlan()
    }
}
