package fit.asta.health.testimonials

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.testimonials.view.TestimonialDemo2
import fit.asta.health.testimonials.view.components.TestimonialLayoutDemo
import fit.asta.health.testimonials.viewmodel.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestimonialsActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController

    private lateinit var binding: ActivityProfileNewBinding
    private val viewModel: TestimonialViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileNewBinding.inflate(layoutInflater)
        binding.profileComposeView.setContent {

            val testimonialState = viewModel.state.collectAsState().value

            navController = rememberNavController()

//            Box(Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colors.background)) {
//                NavHost(navController, startDestination = TstScreen.TstHome.route) {
//                    composable(route = TstScreen.TstHome.route) {
//                        TestimonialDemo2(onNavigateUp = {
//                            navController.navigate(route = TstScreen.TstCreate.route)
//                        })
//                    }
//                    composable(route = TstScreen.TstCreate.route) {
//                        TestimonialLayoutDemo(onNavigateTstCreate = {
//                            navController.popBackStack()
//                        })
//                    }
//                }
//            }

            TestimonialsPreview(navController = navController)

            setContentView(binding.root)
        }
    }
}

@Composable
fun TestimonialsPreview(navController: NavHostController) {
    Box(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        NavHost(navController, startDestination = TstScreen.TstHome.route) {
            composable(route = TstScreen.TstHome.route) {
                TestimonialDemo2(onNavigateUp = {
                    navController.navigate(route = TstScreen.TstCreate.route)
                })
            }
            composable(route = TstScreen.TstCreate.route) {
                TestimonialLayoutDemo(onNavigateTstCreate = {
                    navController.navigate(route = TstScreen.TstHome.route)
                })
            }
        }
    }
}


@Preview
@Composable
fun ScreenPreview() {
    TestimonialsPreview(navController = rememberNavController())
}