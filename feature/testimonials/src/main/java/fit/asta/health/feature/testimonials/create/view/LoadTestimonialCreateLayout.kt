package fit.asta.health.feature.testimonials.create.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.AppNonInternetErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun LoadTestimonialCreateLayout(
    onNavigateTstCreate: () -> Unit,
    getViewModel: TestimonialViewModel = hiltViewModel(),
    onNavigateTstHome: () -> Unit,
    //onNavigateImgCropper: () -> Unit = {},
    //onNavigateAfterImgCropper: () -> Unit = {},
) {

    when (getViewModel.state.collectAsState().value) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AppDotTypingAnimation()
            }
        }

        is UiState.ErrorRetry -> AppNonInternetErrorScreen { getViewModel.loadTestimonial() }

        is UiState.Success -> CreateTstScreen(
            stringResource(R.string.testimonial_title_edit),
            onNavigateTstCreate,
            onNavigateTstHome,
            getViewModel = getViewModel,
        )

        UiState.Idle -> CreateTstScreen(
            stringResource(R.string.testimonial_title_create),
            onNavigateTstCreate,
            onNavigateTstHome,
            getViewModel = getViewModel,
        )

        is UiState.NoInternet -> AppInternetErrorDialog {
            getViewModel.loadTestimonial()
        }

        else -> {}
    }
}

@Composable
fun ServerErrorLayout(error: Throwable) {

}