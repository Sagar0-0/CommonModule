package fit.asta.health.schedule.ui

import android.view.View
import fit.asta.health.schedule.viewmodel.ScheduleViewModel

class SubmitClickListenerImpl(val viewModel: ScheduleViewModel) : View.OnClickListener {

    override fun onClick(view: View) {

        viewModel.submitScheduledPlan()
    }
}
