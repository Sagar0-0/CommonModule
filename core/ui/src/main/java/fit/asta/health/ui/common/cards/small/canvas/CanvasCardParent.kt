package fit.asta.health.ui.common.cards.small.canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@Composable
fun CanvasCardParent(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
            .drawBehind { cardBackGround(cornerRadius = 8.dp, circleDiameter = 48.dp) },
        contentAlignment = Alignment.TopCenter
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppLocalImage(
                    painter = painterResource(id = R.drawable.star_foreground)
                )

                Column {
                    CaptionTexts.Level3(text = "tags")
                    TitleTexts.Level4(text = "Water")
                    BodyTexts.Level3(text = "Need to do this for 10 minutes xxxxxxxxxxxxxxxxxxxxx")
                }
            }

            BodyTexts.Level3(text = "11.00AM")
        }

        CircleCanvas(
            modifier = Modifier.align(Alignment.BottomEnd),
            padding = 8.dp,
            circleDiameter = 48.dp,
            index = 0,
            circleColor = Color.Red
        ) {
            // todo :-
        }
    }
}