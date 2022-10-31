package fit.asta.health.navigation.today.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.navigation.today.adapter.TodayPlanAdapter
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.data.TodayPlanItemData


class TodayPlanViewImpl : TodayPlanView {

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.today_fragment, container, false)
        setupRecyclerView()
        return rootView
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = TodayPlanAdapter()
            val rcvTodayPlan = it.findViewById<RecyclerView>(R.id.rcvTodayPlan)
            rcvTodayPlan.layoutManager = LinearLayoutManager(it.context)
            rcvTodayPlan.adapter = adapter
        }
    }

    override fun setAdapterClickListener(listener: OnPlanClickListener) {
        rootView?.let {
            val rcvTodayPlan = it.findViewById<RecyclerView>(R.id.rcvTodayPlan)
            (rcvTodayPlan.adapter as TodayPlanAdapter).setAdapterClickListener(listener)
        }
    }

    override fun changeState(state: TodayPlanView.State) {
        when (state) {
            is TodayPlanView.State.LoadPlan -> setAdapter(state.list)
            TodayPlanView.State.Empty -> showEmpty()
            else -> {}
        }
    }

    private fun showEmpty() {

    }

    private fun setAdapter(list: List<TodayPlanItemData>) {
        rootView?.let {
            val rcvTodayPlan = it.findViewById<RecyclerView>(R.id.rcvTodayPlan)
            (rcvTodayPlan.adapter as TodayPlanAdapter).updateList(list)
        }
    }
}