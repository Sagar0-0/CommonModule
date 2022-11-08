package fit.asta.health.testimonials

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.AllTestimonialsLayout
import fit.asta.health.testimonials.view.components.TestimonialLayoutDemo
import fit.asta.health.testimonials.viewmodel.TestimonialListState
import fit.asta.health.testimonials.viewmodel.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestimonialsActivity : AppCompatActivity() {

    private lateinit var navController: NavHostController
    private lateinit var binding: ActivityProfileNewBinding
    private val viewModel: TestimonialViewModel by viewModels()
    //private val editViewModel: EditTestimonialViewModel by viewModels()

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

            val testimonialState = viewModel.state.collectAsState().value
            navController = rememberNavController()
            TestimonialsContent(state = viewModel.state.collectAsState().value)

        }
        setContentView(binding.root)
    }

}

@Composable
fun TestimonialsScreen(
    navController: NavHostController,
    testimonial: List<Testimonial>,
) {

    Box(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        NavHost(navController, startDestination = TstScreen.TstHome.route) {

            composable(route = TstScreen.TstHome.route) {

                AllTestimonialsLayout(onNavigateUp = {
                    navController.navigate(route = TstScreen.TstCreate.route)
                }, testimonial = testimonial)

            }
            composable(route = TstScreen.TstCreate.route) {

//                val editViewModelDemo: EditTestimonialViewModel = hiltViewModel()

                TestimonialLayoutDemo(onNavigateTstCreate = {

//                    editViewModelDemo.onEvent(TestimonialEvent.OnSaveClick)
                    //navController.navigate(route = TstScreen.TstHome.route)
                })
            }
        }
    }

}

//@Preview
//@Composable
//fun ScreenPreview() {
//    TestimonialsScreen(navController = rememberNavController(),)
//}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestimonialsContent(state: TestimonialListState) {

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }
    }) {
        when (state) {
            is TestimonialListState.Error -> NoInternetLayout()
            is TestimonialListState.Loading -> LoadingAnimation()
            is TestimonialListState.Success -> TestimonialsScreen(navController = rememberNavController(),
                testimonial = state.testimonial)
        }
    }

}