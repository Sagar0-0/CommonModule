package fit.asta.health.navigation.tools.ui.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.AppTheme

@Composable
fun ToolsCardLayout(
    cardTitle: String,
    type: String,
    imgUrl: String,
    onClick: (type: String) -> Unit,
) {
    AppCard(onClick = { onClick(type) }) {
        Column(modifier = Modifier.background(Color.Transparent)) {
            Box {
                AppDefServerImg(
                    model = getImgUrl(url = imgUrl), contentDescription = cardTitle,
                    modifier = Modifier
                        .aspectRatio(AppTheme.aspectRatio.square)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = AppTheme.spacing.small,
                                bottomEnd = AppTheme.spacing.small
                            )
                        ),
                    contentScale = ContentScale.Crop,
                )
                ScheduleButtonIcon(onButtonClick = { /*TODO Schedule Button Click*/ })
            }
            AppTexts.TitleLarge(
                text = cardTitle, modifier = Modifier.padding(
                    start = AppTheme.spacing.small,
                    top = AppTheme.spacing.small,
                    bottom = AppTheme.spacing.small
                )
            )
        }
    }
}

@Composable
fun ScheduleButtonIcon(
    onButtonClick: () -> Unit,
    imageVector: ImageVector = Icons.Filled.Schedule,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.small), horizontalArrangement = Arrangement.End
    ) {
        ScheduleIconLayout(onButtonClick, imageVector)
    }
}

@Composable
fun ScheduleIconLayout(
    onButtonClick: () -> Unit,
    imageVector: ImageVector = Icons.Filled.Schedule,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(AppTheme.iconSize.regularSize)
            .clip(RoundedCornerShape(AppTheme.spacing.small))
            .background(color = Color.White)
    ) {
        AppButtons.AppIconButton(
            onClick = onButtonClick,
        ) {
            AppDefaultIcon(
                imageVector = imageVector,
                contentDescription = "Schedule Icon",
                tint = Color.DarkGray
            )
        }
    }
}