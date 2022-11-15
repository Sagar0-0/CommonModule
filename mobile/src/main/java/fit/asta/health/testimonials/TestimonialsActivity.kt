package fit.asta.health.testimonials

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.testimonials.model.domain.TstScreen
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.view.AllTestimonialsLayout
import fit.asta.health.testimonials.view.CreateTstScreen
import fit.asta.health.testimonials.viewmodel.TestimonialListState
import fit.asta.health.testimonials.viewmodel.TestimonialListViewModel
import fit.asta.health.testimonials.viewmodel.create.TestimonialGetState
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestimonialsActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController
    private lateinit var binding: ActivityProfileNewBinding
    private val viewModel: TestimonialListViewModel by viewModels()
    //val editViewModel: EditTestimonialViewModel by viewModels()

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
        binding = ActivityProfileNewBinding.inflate(layoutInflater)
        binding.profileComposeView.setContent {

            navController = rememberNavController()
            TestimonialsContent(state = viewModel.state.collectAsState().value)
        }
        setContentView(binding.root)
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

    NavHost(navController, startDestination = TstScreen.TstHome.route) {

        composable(route = TstScreen.TstHome.route) {

            AllTestimonialsLayout(onNavigateUp = {
                navController.navigate(route = TstScreen.TstCreate.route)
            }, testimonials = testimonials, onNavigateBack = {
                navController.popBackStack()
            })
        }

        composable(route = TstScreen.TstCreate.route) {
//            CreateTstScreen(onNavigateTstCreate = {
//                //navController.navigate(route = TstScreen.TstHome.route)
//                navController.popBackStack()
//            })
            LoadTestimonialForm(onNavigateTstCreate = { navController.popBackStack() })
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LoadTestimonialForm(
    onNavigateTstCreate: () -> Unit,
    getViewModel: TestimonialViewModel = hiltViewModel(),
) {
    when (getViewModel.state.collectAsState().value) {
        TestimonialGetState.Loading -> LoadingAnimation()
        TestimonialGetState.Empty -> CreateTstScreen(onNavigateTstCreate)
        is TestimonialGetState.Error -> NoInternetLayout()
        is TestimonialGetState.Success -> CreateTstScreen(onNavigateTstCreate)
    }
}