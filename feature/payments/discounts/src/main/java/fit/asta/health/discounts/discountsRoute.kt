package fit.asta.health.discounts

//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavOptions
//import androidx.navigation.compose.composable
//import fit.asta.health.common.utils.popUpToTop
//import fit.asta.health.discounts.vm.DiscountsViewModel

//const val DISCOUNTS_GRAPH_ROUTE = "graph_discounts"
//fun NavController.navigateToDiscounts(navOptions: NavOptions? = null) {
//    if (navOptions == null) {
//        this.navigate(DISCOUNTS_GRAPH_ROUTE) {
//            popUpToTop(this@navigateToDiscounts)
//        }
//    } else {
//        this.navigate(DISCOUNTS_GRAPH_ROUTE, navOptions)
//    }
//}

//fun NavGraphBuilder.discountsRoute() {
//    composable(DISCOUNTS_GRAPH_ROUTE) {
//        val discountsViewModel: DiscountsViewModel = hiltViewModel()
//        LaunchedEffect(Unit) {
//            discountsViewModel.getData()
//        }
//
//        val state by discountsViewModel.state.collectAsStateWithLifecycle()
//
//    }
//}