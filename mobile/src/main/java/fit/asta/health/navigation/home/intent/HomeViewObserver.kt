package fit.asta.health.navigation.home.intent

import androidx.lifecycle.Observer
import fit.asta.health.navigation.home.view.HomeView


class HomeViewObserver(private val homeView: HomeView) : Observer<HomeAction> {

    override fun onChanged(action: HomeAction) {

        when (action) {
            is HomeAction.LoadHomeData -> {
                val state = HomeView.State.ShowHomeData(action.toolsHome)
                homeView.changeState(state)
            }
            is HomeAction.Empty -> {
                val state = HomeView.State.Empty
                homeView.changeState(state)
            }
            is HomeAction.Error -> {
                val state = HomeView.State.Error(action.error)
                homeView.changeState(state)
            }
        }
    }
}