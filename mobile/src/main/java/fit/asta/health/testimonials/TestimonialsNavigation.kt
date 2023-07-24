package fit.asta.health.testimonials

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import fit.asta.health.imageCropperV2.demo.ImageCropperScreen
import fit.asta.health.main.Graph
import fit.asta.health.main.sharedViewModel
import fit.asta.health.testimonials.view.LoadTestimonialForm
import fit.asta.health.testimonials.viewmodel.create.MediaType
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun NavGraphBuilder.testimonialsNavigation(
    navController: NavHostController
) {
    navigation(
        route = Graph.Testimonials.route,
        startDestination =  TestimonialsRoute.Home.route
    ) {
        composable(route = TestimonialsRoute.Home.route) {
            val getViewModel:TestimonialViewModel= it.sharedViewModel(navController)
            TestimonialsLayout(
                player = getViewModel.player(),
                onNavigateUp = {
                    navController.navigate(route = TestimonialsRoute.Create.route)
                }, onNavigateBack = {
                    navController.popBackStack()
                })
        }

        composable(route = TestimonialsRoute.Create.route) {
            val getViewModel:TestimonialViewModel= it.sharedViewModel(navController)
            LoadTestimonialForm(onNavigateTstCreate = { navController.popBackStack() },
                onNavigateTstHome = {
                    navController.navigate(route = TestimonialsRoute.Home.route)
                },
                onNavigateImgCropper = {
                    navController.navigate(route = TestimonialsRoute.BeforeImgCropper.route)
                },
                getViewModel = getViewModel,
                onNavigateAfterImgCropper = { navController.navigate(route = TestimonialsRoute.AfterImgCropper.route) })

        }


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


    }
}