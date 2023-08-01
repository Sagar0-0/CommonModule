package fit.asta.health.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.profile.viewmodel.ProfileGetState
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalMaterial3Api
@Composable
fun ProfileContent(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onEdit: () -> Unit
) {


    when (val profileState = viewModel.state.collectAsState().value) {
        is ProfileGetState.Loading -> LoadingAnimation()
        is ProfileGetState.Success -> ProfileReadyScreen(
            userProfile = profileState.userProfile,
            onBack = onBack,
            onEdit = onEdit
        )
        is ProfileGetState.Error -> {}
        ProfileGetState.Empty -> CreateProfileLayout(onBack = onBack)
        ProfileGetState.NoInternet -> AppErrorScreen(onTryAgain = { viewModel.loadUserProfile() })
    }

}