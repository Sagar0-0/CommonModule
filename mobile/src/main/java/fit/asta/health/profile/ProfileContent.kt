package fit.asta.health.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import fit.asta.health.navigation.home.view.component.LoadingAnimation
import fit.asta.health.navigation.home.view.component.NoInternetLayout
import fit.asta.health.profile.intent.ProfileState

@ExperimentalMaterial3Api
@Composable
fun ProfileContent(profileState: ProfileState) {
    when (profileState) {
        is ProfileState.Loading -> LoadingAnimation()
        is ProfileState.Success -> ProfileReadyScreen(userProfile = profileState.userProfile)
        is ProfileState.Error -> NoInternetLayout()
    }
}