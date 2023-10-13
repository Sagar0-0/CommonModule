package fit.asta.health.feature.address.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R

@Composable
internal fun CurrentLocationUi(name: String, area: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1),
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            AppIcon(
                modifier = Modifier
                    .padding(end = AppTheme.spacing.level1)
                    .size(AppTheme.iconSize.level3),
                imageVector = Icons.Default.LocationOn,
                contentDescription = R.string.location.toStringFromResId()
            )
            BodyTexts.Level1(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = name,
            )
        }
        BodyTexts.Level1(
            modifier = Modifier.padding(AppTheme.spacing.level0),
            text = area
        )
    }

}