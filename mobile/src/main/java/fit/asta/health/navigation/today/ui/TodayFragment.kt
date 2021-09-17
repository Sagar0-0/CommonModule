package fit.asta.health.navigation.today.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListenerImpl
import fit.asta.health.navigation.today.viewmodel.TodayPlanObserver
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import org.koin.android.ext.android.inject

class TodayFragment : Fragment() {

    private val todayPlanView: TodayPlanView by inject()
    private val todayPlanViewModel: TodayPlanViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return todayPlanView.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        todayPlanView.setAdapterClickListener(
            OnPlanClickListenerImpl(
                requireContext(),
                todayPlanViewModel
            )
        )
        todayPlanViewModel.observeTodayLiveData(
            viewLifecycleOwner,
            TodayPlanObserver(todayPlanView)
        )
        todayPlanViewModel.fetchPlan("")
    }
}