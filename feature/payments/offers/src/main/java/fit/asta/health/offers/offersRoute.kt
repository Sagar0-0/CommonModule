package fit.asta.health.offers

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import fit.asta.health.common.utils.popUpToTop
import fit.asta.health.offers.vm.OffersViewModel

const val OFFERS_GRAPH_ROUTE = "graph_Offers"
fun NavController.navigateToOffers(navOptions: NavOptions? = null) {
    if (navOptions == null) {
        this.navigate(OFFERS_GRAPH_ROUTE) {
            popUpToTop(this@navigateToOffers)
        }
    } else {
        this.navigate(OFFERS_GRAPH_ROUTE, navOptions)
    }
}

fun NavGraphBuilder.offersRoute() {
    composable(OFFERS_GRAPH_ROUTE) {
        val offersViewModel: OffersViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            offersViewModel.getData()
        }

        val state by offersViewModel.state.collectAsStateWithLifecycle()

    }
}