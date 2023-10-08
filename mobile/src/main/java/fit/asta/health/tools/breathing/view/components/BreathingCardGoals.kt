package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun CardBreathingGoals() {
    Row {
        AppCard(
            modifier = Modifier
                .size(121.5.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                AppLocalImage(
                    painter = painterResource(id = R.drawable.sleepimage),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Column(
                modifier = Modifier
                    .size(121.5.dp)
                    .wrapContentHeight()
            ) {
                BodyTexts.Level2(text = "De-stress")
                BodyTexts.Level2(text = "Night Time")
            }
        }

        AppCard(
            modifier = Modifier
                .size(121.5.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                AppLocalImage(
                    painter = painterResource(id = R.drawable.sleepimage),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(
                modifier = Modifier
                    .size(121.5.dp)
                    .wrapContentHeight()
                //.padding(16.dp)
            ) {
                BodyTexts.Level2(text = "De-stress")
                BodyTexts.Level2(text = "Night Time")
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    CardBreathingGoals()
}