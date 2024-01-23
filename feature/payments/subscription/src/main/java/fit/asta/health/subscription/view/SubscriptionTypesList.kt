package fit.asta.health.subscription.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.SubscriptionCategoryData


/**
 * Composable function to display the SubscriptionList using the specified AppTheme.
 */
@Preview
@Composable
private fun SubscriptionListPreview() {
    // Set up the overall theme for the composable
    AppTheme {
        // Use a Box to contain the SubscriptionListContent
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Display the content of the subscription list
            SubscriptionTypesPager(
                listOf(
                    SubscriptionCategoryData(
                        id = "ut",
                        type = 8192,
                        title = "deterruisset",
                        desc = "quo",
                        imageUrl = "https://search.yahoo.com/search?p=luptatum",
                        price = "doctus",
                        cur = 6675,
                        sym = "nascetur"

                    ),
                    SubscriptionCategoryData(
                        id = "ut",
                        type = 8192,
                        title = "deterruisset",
                        desc = "quo",
                        imageUrl = "https://search.yahoo.com/search?p=luptatum",
                        price = "doctus",
                        cur = 6675,
                        sym = "nascetur"
                    ),
                    SubscriptionCategoryData(
                        id = "ut",
                        type = 8192,
                        title = "deterruisset",
                        desc = "quo",
                        imageUrl = "https://search.yahoo.com/search?p=luptatum",
                        price = "doctus",
                        cur = 6675,
                        sym = "nascetur"
                    ),
                )
            ) {

            }
        }
    }
}

/**
 * Composable function to render the list of subscriptions using LazyColumn.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubscriptionTypesPager(
    subscriptionPlans: List<SubscriptionCategoryData>,
    onClick: (categoryId: String) -> Unit
) {
    val subPlans = subscriptionPlans.map {
        SubscriptionData(
            title = it.title,
            desc = it.desc,
            price = it.price,
            imageRes = it.imageUrl,
            categoryId = it.type
        )
    }
    val pagerState = rememberPagerState { subPlans.size }

    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        // Display a title for the subscription list
        HeadingTexts.Level1(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2),
            text = "Explore Premium Plans"
        )
        // Display SubscriptionPassCard for each item in the subscription list
        Box {
            AppHorizontalPager(
                modifier = Modifier
                    .padding(bottom = AppTheme.spacing.level0),
                pagerState = pagerState,
                contentPadding = PaddingValues(AppTheme.spacing.noSpacing),
                pageSpacing = AppTheme.spacing.level2,
                userScrollEnabled = true
            ) { page ->
                SubscriptionPassCard(subscriptionData = subPlans[page]) {
                    onClick(it)
                }
            }

            // This function draws the Dot Indicator for the Pager
            AppExpandingDotIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                pagerState = pagerState
            )
        }
    }
}

/**
 * Composable function to render a subscription card with the provided [subscriptionData].
 *
 * @param subscriptionData Data class containing information about a subscription.
 */
@Composable
fun SubscriptionPassCard(
    subscriptionData: SubscriptionData,
    onClick: (categoryId: String) -> Unit
) {
    // Use AppCard to create a card with a click listener
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        onClick = {
            onClick(subscriptionData.categoryId.toString())
        }
    ) {
        // Use Row and Column to arrange content horizontally and vertically
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Display an image using AppNetworkImage

            AppNetworkImage(
                model = getImgUrl(subscriptionData.imageRes),
                modifier = Modifier
                    .size(AppTheme.imageSize.level11)
                    .aspectRatio(ratio = AppTheme.aspectRatio.square)
            )
            // Use Column to arrange text content vertically
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level1),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                // Display title, description, and other details
                TitleTexts.Level1(text = subscriptionData.title, maxLines = 1)
                CaptionTexts.Level2(
                    text = subscriptionData.desc,
                    maxLines = 3,
                    color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level3)
                )
                AppDivider(modifier = Modifier.fillMaxWidth())
                // Display subscription price details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                ) {
                    TitleTexts.Level2(text = subscriptionData.price)
                    CaptionTexts.Level2(
                        text = "onwards",
                        color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level4),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Data class to represent subscription information.
 *
 * @property title Title of the subscription.
 * @property desc Description of the subscription.
 * @property price Price of the subscription.
 * @property imageRes Resource ID of the subscription image.
 */
data class SubscriptionData(
    val title: String,
    val desc: String,
    val price: String,
    val imageRes: String,
    val categoryId: Int
)
