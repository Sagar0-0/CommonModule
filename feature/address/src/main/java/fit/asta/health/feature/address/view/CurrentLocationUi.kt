package fit.asta.health.feature.address.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.resources.strings.R

@Composable
internal fun CurrentLocationUi(name: String, area: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(AstaThemeX.appSpacing.small),
        horizontalAlignment = Alignment.Start
    ) {
        Row {
            Icon(
                modifier = Modifier
                    .padding(end = AstaThemeX.appSpacing.extraSmall1)
                    .size(AstaThemeX.appIconSize.mediumSmall),
                imageVector = Icons.Default.LocationOn,
                contentDescription = R.string.location.toStringFromResId(),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = name,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Text(
            modifier = Modifier.padding(AstaThemeX.appSpacing.minSmall),
            text = area,
            style = MaterialTheme.typography.titleMedium
        )
    }

}