package fit.asta.health.feature.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.feature.profile.create.CreateProfileLayout
import fit.asta.health.feature.profile.show.ProfileReadyScreen
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
        is UiState.Loading -> AppDotTypingAnimation()
        is UiState.Success -> {
            ProfileReadyScreen(
                userProfileResponse = (profileState as UiState.Success).data,
                onBack = onBack,
                onEdit = onEdit
            )
        }

        is UiState.NoInternet -> {
            AppInternetErrorDialog { viewModel.loadUserProfile() }
        }

        else -> {
            CreateProfileLayout(onBack = onBack)
        }
    }
}
