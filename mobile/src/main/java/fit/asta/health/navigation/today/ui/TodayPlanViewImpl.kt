package fit.asta.health.navigation.today.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.navigation.today.adapter.TodayPlanAdapter
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.data.TodayPlanItemData
import kotlinx.android.synthetic.main.fragment_today.view.*
import java.util.*


class TodayPlanViewImpl : TodayPlanView {

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_today, container, false)
        setupRecyclerView()
        return rootView
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = TodayPlanAdapter()
            it.rcvTodayPlan.layoutManager = LinearLayoutManager(it.context)
            it.rcvTodayPlan.adapter = adapter

        }
    }

    override fun setAdapterClickListener(listener: OnPlanClickListener) {
        rootView?.let {
            (it.rcvTodayPlan.adapter as TodayPlanAdapter).setAdapterClickListener(listener)
        }
    }

    override fun changeState(state: TodayPlanView.State) {
        when (state) {
            is TodayPlanView.State.LoadPlan -> setAdapter(state.list)
            TodayPlanView.State.Empty -> showEmpty()
            else -> { }
        }
    }

    private fun showEmpty() {

    }

    private fun setAdapter(list: List<TodayPlanItemData>) {
        rootView?.let {
            (it.rcvTodayPlan.adapter as TodayPlanAdapter).updateList(list)
        }
    }
}