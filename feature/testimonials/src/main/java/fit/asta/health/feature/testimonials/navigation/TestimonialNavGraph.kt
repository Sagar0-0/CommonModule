package fit.asta.health.feature.testimonials.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import fit.asta.health.feature.testimonials.create.view.CreateTestimonialLayout
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import fit.asta.health.feature.testimonials.list.vm.TestimonialListViewModel
import fit.asta.health.feature.testimonials.navigation.TestimonialNavRoutes.Create
import fit.asta.health.feature.testimonials.navigation.TestimonialNavRoutes.Home
import fit.asta.health.feature.testimonials.screens.TestimonialHomeScreenControl
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
            val testimonialListViewModel: TestimonialListViewModel = hiltViewModel()
            val testimonials = testimonialListViewModel.testimonialPager.collectAsLazyPagingItems()

            TestimonialHomeScreenControl(
                testimonials = testimonials,
                navigateToCreate = {
                    navController.navigate(route = Create.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Create.route) {
            val testimonialViewModel: TestimonialViewModel = hiltViewModel()
            LaunchedEffect(key1 = Unit, block = { testimonialViewModel.loadUserTestimonialData() })

            CreateTestimonialLayout(
                testimonialViewModel = testimonialViewModel,
                onBack = {
                    navController.popBackStack()
                }
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