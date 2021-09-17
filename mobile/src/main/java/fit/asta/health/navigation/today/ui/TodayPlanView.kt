package fit.asta.health.navigation.today.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.data.TodayPlanItemData
import java.util.*

interface TodayPlanView {

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View?
    fun setAdapterClickListener(listener: OnPlanClickListener)

    fun changeState(state: State)

    sealed class State {
        class LoadPlan(val list: List<TodayPlanItemData>) : State()
        object Empty: State()
        class Error(val message: String): State()
    }
}