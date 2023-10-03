package fit.asta.health.designsystem.organism.track

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.core.designsystem.R

// Preview Composable Function
@Preview(
    "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppScreen {
        TrackingDetailsCard(
            imageList = listOf(
                R.drawable.star_foreground,
                R.drawable.star_foreground,
                R.drawable.star_foreground
            ),
            headerTextList = listOf("Inhaled Quantity", "Total Breathes", "Calories"),
            valueList = listOf("11,000 litres", "6620", "258 kcal")
        )
    }
}

@Composable
fun TrackingDetailsCard(
    imageList: List<Int>,
    headerTextList: List<String>,
    valueList: List<String>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        imageList.forEachIndexed { index, image ->

            Column(
                modifier = Modifier.padding(vertical = AppTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppLocalImage(
                    painter = painterResource(id = image),
                    contentDescription = "Inhaled quantity",
                    modifier = Modifier.size(AppTheme.imageSize.large)
                )

                CaptionTexts.Level2(
                    text = headerTextList[index],
                    textAlign = TextAlign.Start,
                    color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level4),
                )

                BodyTexts.Level3(
                    text = valueList[index],
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}