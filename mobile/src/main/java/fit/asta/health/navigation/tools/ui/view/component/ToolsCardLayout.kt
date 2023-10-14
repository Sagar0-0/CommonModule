package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun ToolsCardLayout(
    cardTitle: String,
    type: String,
    imgUrl: String,
    onClick: (type: String) -> Unit,
) {
    AppCard(
        onClick = { onClick(type) },
        shape = AppTheme.shape.level1
    ) {
        Box {
            // Tools Card Images
            AppNetworkImage(
                modifier = Modifier
                    .aspectRatio(AppTheme.aspectRatio.square)
                    .clip(AppTheme.shape.level1),
                model = getImgUrl(url = imgUrl), contentDescription = cardTitle,
                contentScale = ContentScale.Crop,
            )

            // Tools Card Right Top Button
            ScheduleButtonIcon {
                /*TODO Schedule Button Click*/
            }
        }

        // Tools Card Texts
        BodyTexts.Level1(
            text = cardTitle, modifier = Modifier.padding(AppTheme.spacing.level1)
        )
    }
}

@Composable
fun ScheduleButtonIcon(
    imageVector: ImageVector = Icons.Filled.Schedule,
    onButtonClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level1),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(AppTheme.iconSize.level4)
                .clip(AppTheme.shape.level1)
                .background(AppTheme.colors.surface)
        ) {
            AppIconButton(
                imageVector = imageVector, onClick = onButtonClick
            )
        }
    }
}