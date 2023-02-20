package fit.asta.health.testimonials.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
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
        TestimonialGetState.NoInternet -> NoInternetLayout(onTryAgain = {
            getViewModel.onLoad()
        })
        is TestimonialGetState.Error -> ServerErrorLayout(state.error)
        is TestimonialGetState.Success -> CreateTstScreen(
            stringResource(R.string.testimonial_title_edit),
            onNavigateTstCreate,
            onNavigateTstHome
        )
        TestimonialGetState.Empty -> CreateTstScreen(
            stringResource(R.string.testimonial_title_create),
            onNavigateTstCreate,
            onNavigateTstHome
        )
        else -> LoadingAnimation()
    }
}

@Composable
fun ServerErrorLayout(error: Throwable) {

}