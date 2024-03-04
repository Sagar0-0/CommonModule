package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R as StringR

@Composable
fun SelectableRow(
    title: String,
    selectedColor: Color = AppTheme.colors.surfaceTint,
    unSelectedColor: Color = Color.Transparent,
    iconColor: Color = AppTheme.colors.primary,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .padding(vertical = 8.dp) //small
            .clickable {
                if (!isSelected) {
                    onClick.invoke()
                }
            }
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(
            1.dp, if (isSelected) selectedColor else unSelectedColor,
        )) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isSelected) Arrangement.SpaceBetween else Arrangement.Start
        ) {
            BodyTexts.Level1(
                text = title,
                modifier = Modifier.padding(12.dp), //add small padding,
                color = AppTheme.colors.onSurface
            )
            if (isSelected) {
                //show selected icon
                AppIcon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = StringR.string.done),
                    tint = iconColor,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}


@Composable
fun SelectableRowSkinColor(
    title: String,
    selectedColor: Color = AppTheme.colors.surfaceTint,
    unSelectedColor: Color = Color.Transparent,
    iconColor: Color = AppTheme.colors.primary,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    AppCard(
        modifier = Modifier
            .padding(vertical = 8.dp) //small
            .clickable {
                if (!isSelected) {
                    onClick.invoke()
                }
            }
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(
            2.dp, if (isSelected) selectedColor else unSelectedColor,
        ),
//        colors = CardDefaults.cardColors(containerColor = unSelectedColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isSelected) Arrangement.SpaceBetween else Arrangement.Start
        ) {
            BodyTexts.Level1(
                text = title,
                modifier = Modifier.padding(12.dp), //add small padding,
                color = AppTheme.colors.onSurface
            )
            if (isSelected) {
                //show selected icon
                AppIcon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(id = StringR.string.done),
                    tint = Color.Green,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}