package fit.asta.health.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.profile.viewmodel.ProfileState
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalMaterial3Api
@Composable
fun ProfileContent(viewModel: ProfileViewModel = hiltViewModel()) {

    when (val profileState = viewModel.state.collectAsState().value) {
        is ProfileState.Loading -> LoadingAnimation()
        is ProfileState.Success -> ProfileReadyScreen(userProfile = profileState.userProfile)
        is ProfileState.Error -> NoInternetLayout(onTryAgain = {})
        ProfileState.Empty -> TODO()
        ProfileState.NoInternet -> TODO()
    }
}