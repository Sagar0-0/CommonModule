package fit.asta.health.offers.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts


/**
 * Composable function to display the SubscriptionList using the specified AppTheme.
 */
@Preview
@Composable
fun SubscriptionList() {
    // Set up the overall theme for the composable
    AppTheme {
        // Use a Box to contain the SubscriptionListContent
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Display the content of the subscription list
            SubscriptionListContent()
        }
    }
}

/**
 * Composable function to render the list of subscriptions using LazyColumn.
 */
@Composable
fun SubscriptionListContent() {
    // Use LazyColumn to efficiently render a scrolling list of items
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        // Display a title for the subscription list
        item {
            TitleTexts.Level1(text = "Explore Premium Passes")
        }

        // Display SubscriptionPassCard for each item in the subscription list
        items(SubscriptionDataManager.subscriptionList) { list ->
            SubscriptionPassCard(subscriptionData = list)
        }
    }
}

/**
 * Composable function to render a subscription card with the provided [subscriptionData].
 *
 * @param subscriptionData Data class containing information about a subscription.
 */
@Composable
fun SubscriptionPassCard(subscriptionData: SubscriptionData) {
    // Use AppCard to create a card with a click listener
    AppCard(modifier = Modifier.fillMaxWidth(), onClick = {}) {
        // Use Row and Column to arrange content horizontally and vertically
        Row(
            modifier = Modifier.padding(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        ) {
            // Display an image using AppLocalImage
            AppLocalImage(
                painter = painterResource(id = subscriptionData.imageRes),
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            // Use Column to arrange text content vertically
            Column(Modifier.fillMaxWidth()) {
                // Display title, description, and other details
                TitleTexts.Level1(text = subscriptionData.title)
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                BodyTexts.Level1(
                    text = subscriptionData.desc,
                    maxLines = 3,
                    color = AppTheme.colors.onSurface.copy(0.5f)
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                AppDivider()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                // Display subscription price details
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    HeadingTexts.Level3(text = subscriptionData.price)
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                    CaptionTexts.Level2(
                        text = "onwards",
                        color = AppTheme.colors.onSurface.copy(0.7f)
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
    val imageRes: Int
)

/**
 * Object to manage subscription data.
 */
object SubscriptionDataManager {
    // List of SubscriptionData objects
    val subscriptionList: List<SubscriptionData> = listOf(
        SubscriptionData(
            title = "Basic Subscription",
            desc = "The Basic Subscription offers fundamental access to our platform, providing users with essential features to get started on their journey. Dive into a world of convenience with basic content and services tailored to meet your essential needs. Enjoy a seamless experience at an affordable price of $9.99 per month.",
            price = "$9.99 / month",
            imageRes = R.drawable.weatherimage
        ),
        SubscriptionData(
            title = "Premium Subscription",
            desc = "Upgrade to our Premium Subscription for an enhanced experience! Unlock a myriad of premium features that take your usage to the next level. Enjoy exclusive content, advanced functionalities, and priority access to new releases. Elevate your subscription game for just $19.99 per month.",
            price = "$19.99 / month",
            imageRes = R.drawable.weatherimage
        ),
        SubscriptionData(
            title = "Pro Subscription",
            desc = "Go all-in with our Pro Subscription – the ultimate package for power users. Access all features, services, and exclusive content with this comprehensive plan. Whether you're a professional or just want the best, the Pro Subscription has you covered for $29.99 per month. Experience excellence like never before!",
            price = "$29.99 / month",
            imageRes = R.drawable.weatherimage
        ),
        SubscriptionData(
            title = "Basic Subscription",
            desc = "The Basic Subscription offers fundamental access to our platform, providing users with essential features to get started on their journey. Dive into a world of convenience with basic content and services tailored to meet your essential needs. Enjoy a seamless experience at an affordable price of $9.99 per month.",
            price = "$9.99 / month",
            imageRes = R.drawable.weatherimage
        ),
        SubscriptionData(
            title = "Premium Subscription",
            desc = "Upgrade to our Premium Subscription for an enhanced experience! Unlock a myriad of premium features that take your usage to the next level. Enjoy exclusive content, advanced functionalities, and priority access to new releases. Elevate your subscription game for just $19.99 per month.",
            price = "$19.99 / month",
            imageRes = R.drawable.weatherimage
        ),
        SubscriptionData(
            title = "Pro Subscription",
            desc = "Go all-in with our Pro Subscription – the ultimate package for power users. Access all features, services, and exclusive content with this comprehensive plan. Whether you're a professional or just want the best, the Pro Subscription has you covered for $29.99 per month. Experience excellence like never before!",
            price = "$29.99 / month",
            imageRes = R.drawable.weatherimage
        ),
    )
}
