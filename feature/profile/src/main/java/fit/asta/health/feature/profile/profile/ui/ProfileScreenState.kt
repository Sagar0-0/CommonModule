package fit.asta.health.feature.profile.profile.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProfileResponse
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.feature.profile.show.UserProfileContent

@ExperimentalMaterial3Api
@Composable
fun ProfileScreenState(
    userProfileState: UiState<UserProfileResponse>,
    submitProfileState: UiState<SubmitProfileResponse>,
    onUiEvent: (UserProfileUiEvent) -> Unit,
) {
    AppUiStateHandler(
        uiState = userProfileState,
        onErrorRetry = {
            onUiEvent(UserProfileUiEvent.LoadUserProfile)
        },
        onErrorMessage = {
            onUiEvent(UserProfileUiEvent.OnBack)
        }
    ) {
        UserProfileContent(
            userProfileResponse = it,
            isScreenLoading = submitProfileState is UiState.Loading
        ) {
            onUiEvent(UserProfileUiEvent.OnBack)
        }
    }
}
