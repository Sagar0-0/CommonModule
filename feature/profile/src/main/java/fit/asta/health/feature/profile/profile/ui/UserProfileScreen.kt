package fit.asta.health.feature.profile.profile.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.profile.remote.model.UserProfileResponse

@ExperimentalMaterial3Api
@Composable
fun UserProfileScreen(
    userProfileState: UserProfileState,
    userProfileResponseState: UiState<UserProfileResponse>,
    submitProfileState: UiState<SubmitProfileResponse>,
) {

}
