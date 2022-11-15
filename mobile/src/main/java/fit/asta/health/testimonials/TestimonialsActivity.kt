package fit.asta.health.testimonials

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.view.AllTestimonialsLayout
import fit.asta.health.testimonials.view.TestimonialsRoute
import fit.asta.health.testimonials.view.create.CreateTstScreen
import fit.asta.health.testimonials.viewmodel.TestimonialListState
import fit.asta.health.testimonials.viewmodel.TestimonialListViewModel
import fit.asta.health.testimonials.viewmodel.create.TestimonialGetState
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestimonialsActivity : AppCompatActivity() {

    private val viewModel: TestimonialListViewModel by viewModels()

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, TestimonialsActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestimonialsContent(state = viewModel.state.collectAsState().value)
        }
    }
}

@Composable
fun TestimonialsContent(state: TestimonialListState) {

    when (state) {
        is TestimonialListState.Error -> NoInternetLayout()
        is TestimonialListState.Loading -> LoadingAnimation()
        is TestimonialListState.Success -> TestimonialsScreen(navController = rememberNavController(),
            testimonials = state.testimonials)
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TestimonialsScreen(
    navController: NavHostController,
    testimonials: List<NetTestimonial>,
) {

    NavHost(navController, startDestination = TestimonialsRoute.Home.route) {

        composable(route = TestimonialsRoute.Home.route) {

            AllTestimonialsLayout(onNavigateUp = {
                navController.navigate(route = TestimonialsRoute.Create.route)
            }, testimonials = testimonials, onNavigateBack = {
                navController.popBackStack()
            })
        }

        composable(route = TestimonialsRoute.Create.route) {
//            CreateTstScreen(onNavigateTstCreate = {
//                //navController.navigate(route = TstScreen.TstHome.route)
//                navController.popBackStack()
//            })
            LoadTestimonialForm(onNavigateTstCreate = { navController.popBackStack() },
                onNavigateTstHome = {
                    navController.navigate(route = TestimonialsRoute.Home.route)
                })
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LoadTestimonialForm(
    onNavigateTstCreate: () -> Unit,
    getViewModel: TestimonialViewModel = hiltViewModel(),
    onNavigateTstHome: () -> Unit,
) {

    when (getViewModel.state.collectAsState().value) {
        TestimonialGetState.Loading -> LoadingAnimation()
        TestimonialGetState.Empty -> CreateTstScreen(onNavigateTstCreate, onNavigateTstHome)
        is TestimonialGetState.Error -> NoInternetLayout()
        is TestimonialGetState.Success -> CreateTstScreen(onNavigateTstCreate, onNavigateTstHome)
    }

}