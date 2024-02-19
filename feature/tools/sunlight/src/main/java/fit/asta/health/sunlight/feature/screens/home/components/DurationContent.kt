package fit.asta.health.sunlight.feature.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.components.DurationProgressBar
import fit.asta.health.sunlight.feature.screens.home.homeScreen.SunlightHomeState
import fit.asta.health.resources.strings.R as StringR

@Composable
fun DurationContent(
    homeState: State<SunlightHomeState>,
) {
    Column {
        HeadingTexts.Level3(
            text = stringResource(id = StringR.string.total_duration),
            modifier = Modifier.padding(vertical = AppTheme.spacing.level1)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            DurationProgressBar(homeState)
        }
    }

}