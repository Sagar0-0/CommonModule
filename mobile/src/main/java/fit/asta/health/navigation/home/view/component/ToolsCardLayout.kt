package fit.asta.health.navigation.home.view.component

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
import androidx.compose.ui.layout.ContentScale
import fit.asta.health.common.ui.components.generic.AppButtons
import fit.asta.health.common.ui.components.generic.AppCard
import fit.asta.health.common.ui.components.generic.AppDefServerImg
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.iconSize
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImgUrl

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
                        .aspectRatio(aspectRatio.square)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = spacing.small, bottomEnd = spacing.small
                            )
                        ),
                    contentScale = ContentScale.Crop,
                )
                ScheduleButtonIcon(onButtonClick = { /*TODO Schedule Button Click*/ })
            }
            AppTexts.TitleLarge(
                text = cardTitle, modifier = Modifier.padding(
                    start = spacing.small, top = spacing.small, bottom = spacing.small
                )
            )
        }
    }
}

@Composable
fun ScheduleButtonIcon(onButtonClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(spacing.small), horizontalArrangement = Arrangement.End
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(iconSize.regularSize)
                .clip(RoundedCornerShape(spacing.small))
                .background(color = Color.White)
        ) {
            AppButtons.AppIconButton(
                onClick = onButtonClick,
            ) {
                AppDefaultIcon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = "Schedule Icon",
                    tint = Color.DarkGray
                )
            }
        }
    }
}