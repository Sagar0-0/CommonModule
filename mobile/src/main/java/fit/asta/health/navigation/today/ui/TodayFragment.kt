package fit.asta.health.navigation.today.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListenerImpl
import fit.asta.health.navigation.today.viewmodel.TodayPlanObserver
import fit.asta.health.navigation.today.viewmodel.TodayPlanViewModel
import javax.inject.Inject


class TodayFragment : Fragment() {

    @Inject
    lateinit var todayPlanView: TodayPlanView
    private val todayPlanViewModel: TodayPlanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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