package fit.asta.health.feature.profile.profile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState

@Composable
fun CreateProfileTwoButtonLayout(
    userProfileState: UserProfileState,
    isLastButtonSubmit: Boolean = false,
    onSubmitClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        AppFilledButton(
            textToShow = "Previous",
            onClick = { userProfileState.currentPageIndex-- },
            modifier = Modifier.weight(1f),
        )
        AppFilledButton(
            textToShow = if (isLastButtonSubmit) "Submit" else "Next",
            onClick = { if (isLastButtonSubmit) onSubmitClick() else userProfileState.currentPageIndex++ },
            modifier = Modifier.weight(1f),
        )
    }
}