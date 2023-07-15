package fit.asta.health.testimonials

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    var myString by remember { mutableStateOf<String?>("") }

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
                onNavigateImgCropper = {
                    navController.navigate(route = TestimonialsRoute.ImgCropper.route)
                },
                beforeImage = myString
            )

            Log.d("tag", "$myString")

        }


        composable(route = TestimonialsRoute.ImgCropper.route) {

            ImageCropperScreen(onCloseImgCropper = { navController.popBackStack() },

                onConfirmSelection = { imageBitmap ->

                    myString = imageBitmap

                    getViewModel.onEvent(
                        TestimonialEvent.OnMediaSelect(
                            mediaType = MediaType.BeforeImage,
                            url = convertPathToUri(path = imageBitmap)
                        )
                    )

                    navController.popBackStack()
                    Log.d("demo", "CONVERT -> ${convertPathToUri(path = imageBitmap)}")
                })
        }
    }
}

fun convertPathToUri(path: String?): Uri? {
    return Uri.Builder().path(path).build()
}

