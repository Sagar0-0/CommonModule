package fit.asta.health.testimonials

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fit.asta.health.testimonials.view.LoadTestimonialForm
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialsNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = TestimonialsRoute.Home.route) {

        composable(route = TestimonialsRoute.Home.route) {

        }

        composable(route = TestimonialsRoute.Create.route) {

            LoadTestimonialForm(onNavigateTstCreate = { navController.popBackStack() },
                onNavigateTstHome = {
                    navController.navigate(route = TestimonialsRoute.Home.route)
                })
        }
    }
}