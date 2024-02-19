package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.sunlight.feature.utils.toUVIndexColor

@Composable
fun UvIndexColorIndicator(modifier: Modifier=Modifier) {
    Row(
        modifier = modifier
            .wrapContentSize()
//            .border(BorderStroke(1.dp,AppTheme.colors.onSurface),
//                shape = AppTheme.shape.level2)
            .padding(AppTheme.spacing.level1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ColorIndexItem(color = 1.toUVIndexColor(), title = "1..2")
        ColorIndexItem(color = 3.toUVIndexColor(), title = "3..5")
        ColorIndexItem(color = 6.toUVIndexColor(), title = "6..7")
        ColorIndexItem(color = 8.toUVIndexColor(), title = "8..10")
        ColorIndexItem(color = 11.toUVIndexColor(), title = "11+")
    }
}

@Composable
fun ColorIndexItem(color: Color, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(end = AppTheme.spacing.level1)
    ) {
        Divider(
            color = color,
            thickness = AppTheme.spacing.level1,
            modifier = Modifier.width(20.dp)
                .padding(end = AppTheme.spacing.level1)
        )
        CaptionTexts.Level5(text = title)
    }
}