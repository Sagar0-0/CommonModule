package fit.asta.health.feature.profile.show.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun UserSleepCycles(
    columnType: String,
    columnValue: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleTexts.Level3(text = columnType)
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        TitleTexts.Level3(text = columnValue)
    }
}