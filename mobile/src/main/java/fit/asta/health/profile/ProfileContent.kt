package fit.asta.health.profile

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
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

    val context = LocalContext.current

    when (val profileState = viewModel.state.collectAsState().value) {
        is ProfileState.Loading -> LoadingAnimation()
        is ProfileState.Success -> ProfileReadyScreen(userProfile = profileState.userProfile)
        is ProfileState.Error -> {
            if (profileState.error.cause == null) {
                CreateUserProfileActivity.launch(context = context)
            }
            Log.d(
                "validate",
                "Profile Error -> ${profileState.error.cause} and ${profileState.error.message}"
            )
        }
        ProfileState.Empty -> TODO()
        ProfileState.NoInternet -> NoInternetLayout(onTryAgain = {})
    }

}