package fit.asta.health.testimonials


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.imageCropperV2.demo.ImageCropperScreen
import fit.asta.health.testimonials.view.LoadTestimonialForm
import fit.asta.health.testimonials.viewmodel.create.MediaType
import fit.asta.health.testimonials.viewmodel.create.TestimonialEvent
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialsNavigation(
    navController: NavHostController,
    getViewModel: TestimonialViewModel = hiltViewModel(),
) {

//    val context = LocalContext.current

    NavHost(navController, startDestination = TestimonialsRoute.Home.route) {

        composable(route = TestimonialsRoute.Home.route) {

            TestimonialsLayout(onNavigateUp = {
                navController.navigate(route = TestimonialsRoute.Create.route)
            }, onNavigateBack = {
                navController.popBackStack()
            })

        }

        composable(route = TestimonialsRoute.Create.route) {

            LoadTestimonialForm(
                onNavigateTstCreate = { navController.popBackStack() },
                onNavigateTstHome = {
                    navController.navigate(route = TestimonialsRoute.Home.route)
                },
//                onNavigateImgCropper = {
//                    navController.navigate(route = TestimonialsRoute.BeforeImgCropper.route)
//                },
                getViewModel = getViewModel,
//                onNavigateAfterImgCropper = { navController.navigate(route = TestimonialsRoute.AfterImgCropper.route) }
            )

        }


//        composable(route = TestimonialsRoute.BeforeImgCropper.route) {
//
//            ImageCropperScreen(onCloseImgCropper = { navController.popBackStack() },
//
//                onConfirmSelection = { imageBitmap ->
//
//                    getViewModel.onEvent(
//                        TestimonialEvent.OnMediaSelect(
//                            mediaType = MediaType.BeforeImage, url = imageBitmap
//                        )
//                    )
//
//                    navController.popBackStack()
//                })
//
//        }
//
//
//        composable(route = TestimonialsRoute.AfterImgCropper.route) {
//
//            ImageCropperScreen(onCloseImgCropper = { navController.popBackStack() },
//
//                onConfirmSelection = { imageBitmap ->
//
//                    getViewModel.onEvent(
//                        TestimonialEvent.OnMediaSelect(
//                            mediaType = MediaType.AfterImage, url = imageBitmap
//                        )
//                    )
//
//                    navController.popBackStack()
//                })
//
//        }


    }
}
