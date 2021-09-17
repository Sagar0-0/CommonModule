package fit.asta.health.navigation.home

import androidx.lifecycle.Observer
import fit.asta.health.navigation.home.ui.HomeView


class HomeViewObserver(private val homeView: HomeView) : Observer<HomeAction> {

    override fun onChanged(action: HomeAction) {

        when (action) {
            is HomeAction.LoadBanners -> {
                val state = HomeView.State.ShowBanners(action.listBanners)
                homeView.changeState(state)
            }
            is HomeAction.LoadCategories -> {
                val state = HomeView.State.ShowCategories(action.listCategories)
                homeView.changeState(state)
            }
            is HomeAction.LoadTestimonials -> {
                val state = HomeView.State.ShowTestimonials(action.listTestimonials)
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