package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.components.generic.AppClickableCard
import fit.asta.health.common.ui.components.generic.AppDefServerImg
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.aspectRatio
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getMediaUrl

@Composable
fun ToolsCardLayout(
    cardTitle: String,
    type: String,
    imgUrl: String,
    onClick: (type: String) -> Unit,
) {
    AppClickableCard(onClick = { onClick(type) }) {
        Column(modifier = Modifier.background(Color.Transparent)) {
            Box {

                AppDefServerImg(
                    model = getMediaUrl(url = imgUrl), contentDescription = cardTitle,
                    modifier = Modifier
                        .aspectRatio(aspectRatio.original)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = spacing.small, bottomEnd = spacing.small
                            )
                        ),
                    contentScale = ContentScale.Crop,
                )

                Box(contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(spacing.small))
                        .padding(start = (spacing.small - 2.dp), top = (spacing.small - 2.dp))
                        .clickable { /*TODO */ }) {
                    AppDefaultIcon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "Schedule Icon",
                        tint = Color.DarkGray
                    )
                }
            }
            AppTexts.TitleLarge(
                text = cardTitle, modifier = Modifier.padding(
                    start = spacing.small, top = spacing.small, bottom = spacing.small
                )
            )
        }
    }
}