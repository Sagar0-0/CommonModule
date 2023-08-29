package fit.asta.health.testimonials.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.feature.testimonials.TestimonialsDestination.Create
import fit.asta.health.feature.testimonials.TestimonialsDestination.Home
import fit.asta.health.feature.testimonials.create.view.LoadTestimonialCreateLayout
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import fit.asta.health.feature.testimonials.list.view.TestimonialsListLayout
import fit.asta.health.common.utils.sharedViewModel
import fit.asta.health.main.Graph
import fit.asta.health.testimonials.ui.create.view.LoadTestimonialCreateLayout
import fit.asta.health.testimonials.ui.create.vm.TestimonialViewModel
import fit.asta.health.testimonials.ui.list.view.TestimonialsListLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.testimonialsRoute(
    navController: NavHostController
) {
    navigation(
        route = Graph.Testimonials.route,
        startDestination = Home.route
    ) {
        composable(route = Home.route) {

            val getViewModel: TestimonialViewModel = it.sharedViewModel(navController)
            TestimonialsListLayout(
                player = getViewModel.player(),
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
//                onNavigateImgCropper = {
//                    navController.navigate(route = TestimonialsRoute.BeforeImgCropper.route)
//                },
                getViewModel = getViewModel,
                //onNavigateAfterImgCropper = { navController.navigate(route = TestimonialsRoute.AfterImgCropper.route) })
            )
        }

        /*
        composable(route = TestimonialsRoute.BeforeImgCropper.route) {
            val getViewModel:TestimonialViewModel= it.sharedViewModel(navController)
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
            val getViewModel:TestimonialViewModel= it.sharedViewModel(navController)
            ImageCropperScreen(onCloseImgCropper = { navController.popBackStack() },

                onConfirmSelection = { imageBitmap ->

                    getViewModel.onEvent(
                        TestimonialEvent.OnMediaSelect(
                            mediaType = MediaType.AfterImage, url = imageBitmap
                        )
                    )

                    navController.popBackStack()
                })
        }
        */
    }
}