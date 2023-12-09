package fit.asta.health.feature.testimonials.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import fit.asta.health.feature.testimonials.screens.TestimonialCreateScreenControl
import fit.asta.health.feature.testimonials.screens.TestimonialHomeScreenControl
import fit.asta.health.feature.testimonials.viewmodel.TestimonialListViewModel
import fit.asta.health.feature.testimonials.viewmodel.TestimonialViewModel

const val TESTIMONIALS_GRAPH_ROUTE = "graph_testimonials_tool"

fun NavController.navigateToTestimonials(navOptions: NavOptions? = null) {
    this.navigate(TESTIMONIALS_GRAPH_ROUTE, navOptions)
}


/**
 * This is the navigation Host function for the Music Feature
 *
 * @param navController This is the navController for the Tracking Screens
 */
fun NavGraphBuilder.testimonialNavGraph(
    navController: NavHostController,
    onBack: () -> Unit
) {

    navigation(
        route = TESTIMONIALS_GRAPH_ROUTE,
        startDestination = TestimonialNavRoutes.Home.route
    ) {
        composable(route = TestimonialNavRoutes.Home.route) {
            val testimonialListViewModel: TestimonialListViewModel = hiltViewModel()
            val testimonials = testimonialListViewModel.testimonialPager.collectAsLazyPagingItems()

            TestimonialHomeScreenControl(
                testimonials = testimonials,
                navigateToCreate = {
                    navController.navigate(route = TestimonialNavRoutes.Create.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = TestimonialNavRoutes.Create.route) {

            val viewModel: TestimonialViewModel = hiltViewModel()

            val userTestimonialApiState = viewModel.userTestimonialApiState
                .collectAsStateWithLifecycle().value
            val testimonialData = viewModel.testimonialData.collectAsStateWithLifecycle().value

            val testimonialSubmitApi = viewModel.testimonialSubmitApiState
                .collectAsStateWithLifecycle().value

            TestimonialCreateScreenControl(
                userTestimonialState = userTestimonialApiState,
                userTestimonialData = testimonialData,
                testimonialSubmitApiState = testimonialSubmitApi,
                onBack = onBack,
                setEvent = viewModel::onEvent
            )
        }
    }
}