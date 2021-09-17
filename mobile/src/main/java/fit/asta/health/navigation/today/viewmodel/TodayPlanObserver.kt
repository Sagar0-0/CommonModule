package fit.asta.health.navigation.today.viewmodel

import androidx.lifecycle.Observer
import fit.asta.health.navigation.today.ui.TodayPlanView

class TodayPlanObserver(private val todayPlanView: TodayPlanView) :
    Observer<TodayPlanAction> {
    override fun onChanged(action: TodayPlanAction) {
        when (action) {
            is TodayPlanAction.LoadPlan -> {
                val state = TodayPlanView.State.LoadPlan(action.list)
                todayPlanView.changeState(state)
            }
            TodayPlanAction.Empty -> {
                val state = TodayPlanView.State.Empty
                todayPlanView.changeState(state)
            }

            is TodayPlanAction.Error -> {
                val state = TodayPlanView.State.Error(action.message)
                todayPlanView.changeState(state)
            }
        }
    }
}