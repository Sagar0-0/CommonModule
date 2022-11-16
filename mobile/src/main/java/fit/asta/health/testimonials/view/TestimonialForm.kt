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
    when (val state = getViewModel.state.collectAsState().value) {
        TestimonialGetState.Loading -> LoadingAnimation()
        TestimonialGetState.NoInternet -> NoInternetLayout()
        is TestimonialGetState.Error -> ServerErrorLayout(state.error)
        is TestimonialGetState.Success -> CreateTstScreen(
            getViewModel.title.asString(),
            onNavigateTstCreate,
            onNavigateTstHome
        )
    }
}

@Composable
fun ServerErrorLayout(error: Throwable) {

}