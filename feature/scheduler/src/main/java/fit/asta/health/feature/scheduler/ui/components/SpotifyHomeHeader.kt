package fit.asta.health.feature.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.resources.strings.R as StringR

@Composable
fun SpotifyHomeHeader(onSearchIconClicked: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Welcoming Text with User Name
        HeadingTexts.Level1(text = stringResource(StringR.string.spotify))

        // Search Icon
        AppIconButton(
            imageVector = Icons.Outlined.Search,
            modifier = Modifier.size(AppTheme.iconSize.level3),

            // Modifications
            iconDesc = stringResource(StringR.string.search),
            onClick = onSearchIconClicked
        )
    }
}