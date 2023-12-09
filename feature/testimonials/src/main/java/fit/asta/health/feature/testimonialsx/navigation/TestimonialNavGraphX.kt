package fit.asta.health.feature.testimonialsx.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.feature.testimonialsx.screens.TestimonialCreateScreenControl
import fit.asta.health.feature.testimonialsx.screens.TestimonialHomeScreenControl
import fit.asta.health.feature.testimonialsx.viewmodel.TestimonialViewModelX

const val TESTIMONIALS_GRAPH_ROUTE = "graph_testimonials_tool"

fun NavController.navigateToTestimonials(navOptions: NavOptions? = null) {
    this.navigate(TESTIMONIALS_GRAPH_ROUTE, navOptions)
}


/**
 * This is the navigation Host function for the Music Feature
 *
 * @param navController This is the navController for the Tracking Screens
 */
fun NavGraphBuilder.testimonialNavGraphX(
    navController: NavHostController,
    onBack: () -> Unit
) {

    navigation(
        route = TESTIMONIALS_GRAPH_ROUTE,
        startDestination = TestimonialNavRoutesX.Home.route
    ) {
        composable(route = TestimonialNavRoutesX.Home.route) {
            TestimonialHomeScreenControl(
                navigate = { navController.navigate(it) },
                onBack = onBack
            )
        }

        composable(route = TestimonialNavRoutesX.Create.route) {

            val viewModel: TestimonialViewModelX = hiltViewModel()

            val userTestimonialApiState = viewModel.userTestimonial
                .collectAsStateWithLifecycle().value
            val testimonialData = viewModel.testimonialData.collectAsStateWithLifecycle().value

            val testimonialSubmitApi = viewModel.testimonialSubmitApiState
                .collectAsStateWithLifecycle().value

            TestimonialCreateScreenControl(
                userTestimonialState = userTestimonialApiState,
                userTestimonialData = testimonialData,
                testimonialSubmitApiState = testimonialSubmitApi,
                onBack = onBack,
                navigate = { navController.navigate(it) },
                setEvent = viewModel::onEvent
            )
        }
    }
}