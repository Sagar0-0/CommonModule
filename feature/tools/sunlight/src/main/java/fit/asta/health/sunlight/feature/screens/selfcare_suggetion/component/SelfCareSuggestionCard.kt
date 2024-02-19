package fit.asta.health.sunlight.feature.screens.selfcare_suggetion.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.remote.model.Bev
import fit.asta.health.sunlight.remote.model.Food
import fit.asta.health.sunlight.remote.model.Suppl

@Composable
fun SelfCareSuggestionCardFood(item: Food) {
    AppElevatedCard(
        modifier = Modifier
            .width(340.dp)
            .height(400.dp)
            .padding(end = AppTheme.spacing.level2)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.spacing.level2)
        ) {
            AppNetworkImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                model = item.url ?: ""
            )
            HeadingTexts.Level2(
                text = item.name ?: "-",
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            CaptionTexts.Level3(
                text = item.dsc ?: "-",
                maxLines = 5,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun SelfCareSuggestionCardFood(item: Bev) {
    AppElevatedCard(
        modifier = Modifier
            .width(340.dp)
            .height(400.dp)
            .padding(end = AppTheme.spacing.level2)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.spacing.level2)
        ) {
            AppNetworkImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                model = item.url ?: ""
            )
            HeadingTexts.Level2(
                text = item.name ?: "-",
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            CaptionTexts.Level3(
                text = item.dsc ?: "-",
                maxLines = 5,
                textAlign = TextAlign.Center,
            )
        }
    }
}



@Composable
fun SelfCareSuggestionCardSupplements(item: Suppl) {
    AppElevatedCard(
        modifier = Modifier
            .width(340.dp)
            .height(600.dp)
            .padding(end = AppTheme.spacing.level2)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(AppTheme.spacing.level2)
        ) {
            AppNetworkImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                model = "https://d3i8zhfqdh4o9t.cloudfront.net${item.url}"
            )
            BodyTexts.Level2(
                text = item.name ?: "-",
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
                CaptionTexts.Level3(
                    text = "Brand:-${item.brand ?: "-"}",
                    maxLines = 1
                )
                CaptionTexts.Level3(
                    text = "Ingredients:-${item.ingr}",
                    maxLines = 2
                )
                CaptionTexts.Level3(
                    text = "Recommended Serving:-${item.serRcm ?: "-"}",
                    maxLines = 1
                )
            }
            CaptionTexts.Level3(
                text = "Description:-${item.dsc ?: "-"}",
                maxLines = 8
            )
            Box(modifier = Modifier.fillMaxSize()) {
                CaptionTexts.Level5(
                    text = "Warning :- ${item.warn}",
                    color = AppTheme.colors.error,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}