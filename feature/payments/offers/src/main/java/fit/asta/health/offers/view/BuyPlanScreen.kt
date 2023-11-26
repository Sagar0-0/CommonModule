package fit.asta.health.offers.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

/**
 * Composable function for the Buy Plan Screen. It sets up the UI using the AppTheme and a Box layout.
 */
@Preview
@Composable
fun BuyPlanScreen() {

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ContentForBuyingPlan()
        }
    }
}

/**
 * Composable function for the content of the Buy Plan Screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentForBuyingPlan() {

    // AppSurface is a custom composable providing a themed surface for the content.
    AppSurface(modifier = Modifier.fillMaxSize()) {

        // Column composable to arrange child composable vertically with scrolling enabled.
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = AppTheme.spacing.level8),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            // Image and various card composable are arranged vertically within a Box layout.
            Box {

                // AppLocalImage is a custom composable to load and display local images.
                AppLocalImage(
                    painter = painterResource(id = R.drawable.weatherimage),
                    modifier = Modifier.aspectRatio(AppTheme.aspectRatio.fullScreen)
                )

                // Inner Box to position content over the image.
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.padding(top = 204.dp),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
                    ) {
                        // Various card composable and layouts are included.
                        ProMembershipCard()
                        PickStartDateCard()
                        OffersSection()
                        EMISection()
                        HowItWorksSection()
                    }
                }

                // Top bar with a back button.
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AppTopBar(backIcon = Icons.Filled.ArrowBack, onBack = {})
                }
            }
        }

        // Bottom button in a separate Box at the bottom of the screen.
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            AppSurface(
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth(),
                color = AppTheme.colors.onSurface
            ) {
                AppFilledButton(
                    textToShow = "PICK A START DATE",
                    onClick = {},
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.spacing.level6,
                            vertical = AppTheme.spacing.level1
                        )
                        .fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Composable function for displaying a professional plan card.
 */
@Composable
fun ProMembershipCard() {

    // AppCard is a custom composable for displaying cards with padding and rounded corners.
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {

            // Row displaying plan details and pricing.
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TitleTexts.Level1(text = "12 Months\ncultpass PRO")
                Column(horizontalAlignment = Alignment.End) {
                    CaptionTexts.Level3(text = addStrikethrough("$19990"))
                    HeadingTexts.Level4(text = "$8990")
                }
            }

            // Rows displaying features of the plan.
            Row(Modifier.fillMaxWidth()) {
                AppIcon(imageVector = Icons.Filled.Build)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
                BodyTexts.Level1(text = "Unlimited PRO gym access")
            }

            Row(Modifier.fillMaxWidth()) {
                AppIcon(imageVector = Icons.Filled.Add)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
                BodyTexts.Level1(text = "2 sessions at ELITE gyms or cult centers per month")
            }
        }
    }
}

/**
 * Composable function for displaying a card to pick a start date.
 */
@Composable
fun PickStartDateCard() {

    // AppCard is used for the card with padding and a click listener.
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2), onClick = {}
    ) {

        // Row displaying an icon and title for picking a start date.
        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            AppIcon(painter = painterResource(id = R.drawable.date_range))
            TitleTexts.Level1(text = "Pick a Start Date")
        }
    }
}

/**
 * Composable function for displaying a layout of offers.
 */
@Composable
fun OffersSection() {

    // Column displaying offers with padding and spacing.
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Title for the offers section.
        TitleTexts.Level1(text = "Offers")

        // Repeated OffersContent composable for each offer.
        IndividualOfferContent()
        IndividualOfferContent()
        IndividualOfferContent()
    }
}

/**
 * Composable function for displaying individual offer content.
 */
@Composable
private fun IndividualOfferContent() {
    // Row displaying an icon, text, and a button for each offer.
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppIcon(painter = painterResource(id = R.drawable.sell))

        BodyTexts.Level1(
            text = "Only Today - Get FREE 2.5 months extension.",
            modifier = Modifier.fillMaxWidth(0.7f)
        )

        AppIconButton(
            painter = painterResource(id = R.drawable.navigate_next),
            modifier = Modifier.padding(start = AppTheme.spacing.level2), onClick = {}
        )
    }
}

/**
 * Composable function for displaying EMI details.
 */
@Composable
fun EMISection() {

    // Column displaying EMI details with padding and spacing.
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Title for the EMI section.
        TitleTexts.Level1(text = "No Cost EMI")

        // Row displaying an icon, text, and a button for EMI details.
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppIcon(imageVector = Icons.Filled.ShoppingCart)

            BodyTexts.Level1(
                text = "Starting from 841/month",
            )

            AppTextButton(textToShow = "Details") {

            }
        }
    }
}

/**
 * Composable function for displaying "How it works" details.
 */
@Composable
fun HowItWorksSection() {

    // Column displaying "How it works" details with padding and spacing.
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Title for the "How it works" section.
        TitleTexts.Level1(text = "How it works")

        // Row displaying an icon and a text for each step.
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppIcon(imageVector = Icons.Filled.Check)

            BodyTexts.Level1(
                text = "Choose from the wide variety of online workouts and join in from anywhere",
            )
        }
    }
}