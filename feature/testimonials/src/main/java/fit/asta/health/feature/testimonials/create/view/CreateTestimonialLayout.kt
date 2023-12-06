package fit.asta.health.feature.testimonials.create.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.feature.testimonials.create.vm.TestimonialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CreateTestimonialLayout(
    testimonialViewModel: TestimonialViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val userTestimonial by testimonialViewModel.userTestimonialState.collectAsStateWithLifecycle()

    CreateTestimonialScreen(
        testimonialViewModel = testimonialViewModel,
        onBack = onBack,
    )


//    when (userTestimonial) {
//        UiState.Idle, is UiState.Success -> {
//            CreateTestimonialScreen(
//                testimonialViewModel = testimonialViewModel,
//                onBack = onBack,
//            )
//        }
//
//        is UiState.Loading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                AppDotTypingAnimation()
//            }
//        }
//
//        is UiState.ErrorRetry -> {
//            AppErrorScreen {
//                testimonialViewModel.loadUserTestimonialData()
//            }
//        }
//
//        is UiState.NoInternet -> {
//            AppInternetErrorDialog {
//                testimonialViewModel.loadUserTestimonialData()
//            }
//        }
//
//        else -> {}
//    }
}

@Composable
fun ServerErrorLayout(error: Throwable) {

}