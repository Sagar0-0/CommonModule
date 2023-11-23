package fit.asta.health.offers.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R


/**
 * Composable function to display the "Deal of the Day" section.
 */
@Preview
@Composable
fun DealOfTheDay() {
    // Remember the LazyListState for scroll position persistence
    val state = rememberLazyListState()

    // Apply the AppTheme to the entire composable
    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            // Column to arrange child composable vertically
            Column(
                modifier = Modifier
                    .padding(AppTheme.spacing.level2)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                // Display the title for the "Deal of the Day" section
                TitleTexts.Level1(text = "Deal of the Day")

                // LazyRow composable to horizontally scroll through deal items
                LazyRow(state = state) {
                    items(DealRepository.deals) { deal ->
                        // Display each deal item using the DealItem composable
                        DealItem(deal = deal)
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display an individual deal item.
 * @param deal The deal object containing information about the deal.
 */
@Composable
fun DealItem(deal: Deal) {
    // Box composable to create a container for the deal item
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(AppTheme.boxSize.level9),
        contentAlignment = Alignment.Center
    ) {

        // Use AppLocalImage to display the deal image
        AppLocalImage(
            painter = painterResource(id = deal.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(AppTheme.imageSize.level12)
                .clip(CircleShape)
                .border(
                    5.dp,
                    Color.White, CircleShape
                ),
            contentScale = ContentScale.Crop
        )

        // Box composable to create a container for the deal details
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            // Column to arrange child composable vertically for deal details
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display favorite icon
                AppIcon(
                    imageVector = Icons.Rounded.Favorite,
                    tint = Color.Red
                )
                // Display the title of the deal
                TitleTexts.Level1(text = deal.title)
                // Add spacing
                Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                // Display the month and discount details of the deal
                BodyTexts.Level3(text = deal.month)
                BodyTexts.Level3(text = deal.discount)
            }
        }
    }
}

/**
 * Data class representing a deal item.
 * @property title The title of the deal.
 * @property month The duration of the deal.
 * @property discount The discount details of the deal.
 * @property imageRes The resource ID of the deal image.
 */
data class Deal(
    val title: String,
    val month: String,
    val discount: String,
    val imageRes: Int
)

/**
 * Object containing a list of predefined deals.
 */
object DealRepository {
    val deals = listOf(
        Deal("ELITE", "FREE 3 months", "+ Extra $1,500 OFF", R.drawable.weatherimage),
        Deal("PRO", "FREE 2 months", "+ Extra $1,500 OFF", R.drawable.weatherimage),
        Deal("PLAY", "FREE 1-month", "+ Extra $5,000 OFF", R.drawable.weatherimage),
    )
}

