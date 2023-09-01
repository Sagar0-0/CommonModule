package fit.asta.health.profile.feature.show.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.theme.spacing

@Composable
fun UserSleepCycles(
    columnType: String,
    columnValue: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTexts.TitleSmall(text = columnType)
        Spacer(modifier = Modifier.height(spacing.small))
        AppTexts.TitleSmall(text = columnValue)
    }
}