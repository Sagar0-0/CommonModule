package fit.asta.health.offers.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts


/**
 * Composable function for the main screen displaying subscription plan details.
 */
@Preview
@Composable
fun SubPlanScreen() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SubPlanContent()
        }
    }
}

/**
 * Composable function for the content of the subscription plan screen.
 */
@Composable
fun SubPlanContent() {
    AppSurface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
        shape = AppTheme.shape.level3,
        color = AppTheme.colors.onSurface.copy(0.1f)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            // Header Section
            SubPlanHeader()

            // Details Section
            SubPlanDetails()

            // Buy Now Button
            BuyNowButton()
        }
    }
}

/**
 * Composable function for the header section of the subscription plan.
 */
@Composable
fun SubPlanHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon and Warning Message
        AppIcon(
            imageVector = Icons.Filled.Warning,
            tint = Color.Yellow,
            modifier = Modifier.size(AppTheme.iconSize.level5)
        )
        TitleTexts.Level1(text = "PRICES INCREASING FROM")
    }
}

/**
 * Composable function for the details section of the subscription plan.
 */
@Composable
fun SubPlanDetails() {
    AppCard(
        modifier = Modifier.padding(
            horizontal = AppTheme.spacing.level2,
            vertical = AppTheme.spacing.level1
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            // Plan Duration
            LargeTexts.Level2(text = "12 MONTH")
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            AppDivider()
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))

            // Current Prices
            CurrentPricesRow()

            // GST Charges
            GSTChargesRow()

            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            // Only Today Heading
            HeadingTexts.Level1(
                text = "ONLY TODAY",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppTheme.spacing.level4))

            // Offers Available
            OffersAvailable()
        }
    }
}

/**
 * Composable function for the row displaying current prices.
 */
@Composable
fun CurrentPricesRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Current Price Content
        CurrentPriceContent()

        // Divider
        AppDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
        )

        // Current Price Content (again)
        CurrentPriceContent()
    }

    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
}

/**
 * Composable function for the row displaying GST charges.
 */
@Composable
fun GSTChargesRow() {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // GST Dividers
        AppDivider(
            modifier = Modifier
                .weight(1f)
                .width(1.dp)
        )

        // GST Charges Text
        CaptionTexts.Level4(
            text = "+ GST charges applicable",
            modifier = Modifier.padding(horizontal = AppTheme.spacing.level1)
        )

        // GST Divider
        AppDivider(
            modifier = Modifier
                .weight(1f)
                .width(1.dp)
        )
    }
}

/**
 * Composable function for displaying the current price details.
 */
@Composable
fun CurrentPriceContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        // Current Price Title
        TitleTexts.Level4(text = "Current Price")

        // Current Price Value
        LargeTexts.Level3(text = "$11,660")

        // Monthly Price Value
        BodyTexts.Level3(text = "$899 / Month")
    }
}

/**
 * Composable function for the "BUY NOW" button.
 */
@Composable
fun BuyNowButton() {
    AppFilledButton(
        textToShow = "BUY NOW",
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level4)
    )
}

/**
 * Composable function for displaying available offers.
 */
@Composable
fun OffersAvailable() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = AppTheme.spacing.level6, bottom = AppTheme.spacing.level2)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            // Offer Icon
            AppSurface(
                shape = CircleShape,
                modifier = Modifier.size(AppTheme.iconSize.level3),
                color = AppTheme.colors.primary
            ) {
                AppIcon(imageVector = Icons.Filled.Add, tint = AppTheme.colors.onSurface)
            }

            // Offer Text
            BodyTexts.Level1(text = "FREE 1-month extension")
        }
    }
}

