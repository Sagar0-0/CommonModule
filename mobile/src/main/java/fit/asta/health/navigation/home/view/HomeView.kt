package fit.asta.health.navigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fit.asta.health.navigation.home.model.domain.ToolsHome

interface HomeView {

    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?

    fun changeState(state: State)

    sealed class State {
        class ShowHomeData(val toolsHome: ToolsHome) : State()
        object Empty : State()
        class Error(val error: Throwable) : State()
    }
}