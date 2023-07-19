package fit.asta.health.testimonials.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.R
import fit.asta.health.navigation.home.view.component.ErrorScreenLayout
import fit.asta.health.navigation.home.view.component.LoadingAnimation
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
    onNavigateImgCropper: () -> Unit = {},
    onNavigateAfterImgCropper: () -> Unit = {},
    ) {
    when (val state = getViewModel.state.collectAsState().value) {
        TestimonialGetState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }

        is TestimonialGetState.Error -> ErrorScreenLayout(onTryAgain = {
            getViewModel.loadTestimonial()
        }, isInternetError = false)

        is TestimonialGetState.Success -> CreateTstScreen(
            stringResource(R.string.testimonial_title_edit),
            onNavigateTstCreate,
            onNavigateTstHome,
            onNavigateImgCropper,
            getViewModel = getViewModel,
            onNavigateAfterImgCropper = onNavigateAfterImgCropper
        )

        TestimonialGetState.Empty -> CreateTstScreen(
            stringResource(R.string.testimonial_title_create),
            onNavigateTstCreate,
            onNavigateTstHome,
            onNavigateImgCropper,
            getViewModel = getViewModel,
            onNavigateAfterImgCropper = onNavigateAfterImgCropper
        )

        is TestimonialGetState.NetworkError -> ErrorScreenLayout(onTryAgain = {
            getViewModel.loadTestimonial()
        })
    }
}

@Composable
fun ServerErrorLayout(error: Throwable) {

}