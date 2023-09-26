package fit.asta.health.feature.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.designsystem.components.generic.AppErrorScreen
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.feature.profile.create.CreateProfileLayout
import fit.asta.health.feature.profile.show.ProfileReadyScreen
import fit.asta.health.feature.profile.show.vm.ProfileGetState
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalMaterial3Api
@Composable
fun ProfileContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onEdit: () -> Unit,
) {
    val profileState by viewModel.state.collectAsState()

    when (profileState) {
        is ProfileGetState.Loading -> LoadingAnimation()
        is ProfileGetState.Success -> {
            ProfileReadyScreen(
                userProfile = (profileState as ProfileGetState.Success).userProfile,
                onBack = onBack,
                onEdit = onEdit
            )
        }

        is ProfileGetState.Empty -> CreateProfileLayout(onBack = onBack)
        is ProfileGetState.NoInternet -> {
            AppErrorScreen(onTryAgain = { viewModel.loadUserProfile() })
        }

        is ProfileGetState.Error -> {
            // Handle error case if needed
        }
    }
}
