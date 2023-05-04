package fit.asta.health.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.profile.viewmodel.ProfileGetState
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalMaterial3Api
@Composable
fun ProfileContent(viewModel: ProfileViewModel = hiltViewModel()) {

    val context = LocalContext.current

    when (val profileState = viewModel.state.collectAsState().value) {
        is ProfileGetState.Loading -> LoadingAnimation()
        is ProfileGetState.Success -> ProfileReadyScreen(userProfile = profileState.userProfile)
        is ProfileGetState.Error -> {}
        ProfileGetState.Empty -> CreateUserProfileActivity.launch(context = context)
        ProfileGetState.NoInternet -> NoInternetLayout(onTryAgain = { viewModel.loadUserProfile() })
    }

}