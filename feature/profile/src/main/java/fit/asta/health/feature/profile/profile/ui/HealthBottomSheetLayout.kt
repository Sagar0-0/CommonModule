package fit.asta.health.feature.profile.profile.ui

import androidx.compose.runtime.Composable
import fit.asta.health.designsystem.molecular.AppUiStateHandler

@Composable
fun HealthBottomSheetLayout(
    userProfileState: UserProfileState
) {
    AppUiStateHandler(
        uiState = userProfileState.healthPropertiesState,
        isScreenLoading = false
    ) {
        ItemSelectionLayout(
            userProfileState = userProfileState,
            healthProperties = it
        )
    }
}