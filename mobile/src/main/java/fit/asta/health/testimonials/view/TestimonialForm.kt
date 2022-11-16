package fit.asta.health.testimonials.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.testimonials.view.create.CreateTstScreen
import fit.asta.health.testimonials.viewmodel.create.TestimonialGetState
import fit.asta.health.testimonials.viewmodel.create.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

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