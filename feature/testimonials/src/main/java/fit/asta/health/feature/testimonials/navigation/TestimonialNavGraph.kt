package fit.asta.health.feature.testimonials.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.feature.testimonials.create.view.LoadTestimonialCreateLayout
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import fit.asta.health.feature.testimonials.list.view.TestimonialsListLayout
import fit.asta.health.feature.testimonials.navigation.TestimonialNavRoutes.Create
import fit.asta.health.feature.testimonials.navigation.TestimonialNavRoutes.Home
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val TESTIMONIALS_GRAPH_ROUTE = "graph_testimonials_tool"

fun NavController.navigateToTestimonials(navOptions: NavOptions? = null) {
    this.navigate(TESTIMONIALS_GRAPH_ROUTE, navOptions)
}

/**
 * This is the navigation Host function for the Music Feature
 *
 * @param navController This is the navController for the Tracking Screens
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.testimonialNavRoute(
    navController: NavHostController, onBack: () -> Unit
) {
    navigation(
        route = TESTIMONIALS_GRAPH_ROUTE,
        startDestination = Home.route
    ) {
        composable(route = Home.route) {
//            val getViewModel: TestimonialViewModel = it.sharedViewModel(navController)
            TestimonialsListLayout(
                onNavigateUp = {
                    navController.navigate(route = Create.route)
                }, onNavigateBack = {
                    navController.popBackStack()
                })
        }

        composable(route = Create.route) {

            val getViewModel: TestimonialViewModel = it.sharedViewModel(navController)
            LoadTestimonialCreateLayout(
                onNavigateTstCreate = { navController.popBackStack() },
                onNavigateTstHome = {
                    navController.navigate(route = Home.route)
                },
                getViewModel = getViewModel,
            )
        }
    }

    /*
    composable(route = TestimonialsRoute.BeforeImgCropper.route) {
        val getViewModel: TestimonialViewModel = it.sharedViewModel(navController)
        ImageCropperScreen(onCloseImgCropper = { navController.popBackStack() },

            onConfirmSelection = { imageBitmap ->

                getViewModel.onEvent(
                    TestimonialEvent.OnMediaSelect(
                        mediaType = MediaType.BeforeImage, url = imageBitmap
                    )
                )

                navController.popBackStack()
            })
    }

    composable(route = TestimonialsRoute.AfterImgCropper.route) {
        val getViewModel: TestimonialViewModel = it.sharedViewModel(navController)
        ImageCropperScreen(onCloseImgCropper = { navController.popBackStack() },

            onConfirmSelection = { imageBitmap ->

                getViewModel.onEvent(
                    TestimonialEvent.OnMediaSelect(
                        mediaType = MediaType.AfterImage, url = imageBitmap
                    )
                )

                navController.popBackStack()
            })
    }*/
}