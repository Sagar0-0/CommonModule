package fit.asta.health.subscription.view

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderRequestType
import fit.asta.health.resources.drawables.R
import fit.asta.health.subscription.remote.model.Offer
import fit.asta.health.subscription.remote.model.SubscriptionResponse.SubscriptionPlans.SubscriptionPlanCategory

/**
 * Composable function for the Buy Plan Screen. It sets up the UI using the AppTheme and a Box layout.
 */
@Preview
@Composable
private fun BuyPlanScreen() {

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProceedToBuyScreen(SubscriptionPlanCategory(), "0", null, {}, {})
        }
    }
}

/**
 * Composable function for the content of the Buy Plan Screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceedToBuyScreen(
    subscriptionPlanCategory: SubscriptionPlanCategory,
    durationType: String,
    offer: Offer? = null,
    onBack: () -> Unit,
    onProceedToBuy: (OrderRequest) -> Unit
) {
    val discountCode by rememberSaveable {
        mutableStateOf("")
    }
    val walletPoints by rememberSaveable {
        mutableIntStateOf(0)
    }
    val walletMoney by rememberSaveable {
        mutableIntStateOf(0)
    }
    val discountMoney by rememberSaveable {
        mutableIntStateOf(0)
    }

    // AppSurface is a custom composable providing a themed surface for the content.
    AppSurface(
        modifier = Modifier.fillMaxSize()
    ) {
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
                AppNetworkImage(
                    model = getImgUrl(subscriptionPlanCategory.imgUrl),
                    modifier = Modifier.aspectRatio(AppTheme.aspectRatio.fullScreen)
                )

                // Inner Box to position content over the image.
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 204.dp),
                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
                    ) {
                        // Various card composable and layouts are included.
                        ProMembershipCard(
                            subscriptionPlanCategory,
                            durationType
                        )
                        offer?.let {
                            OffersSection(it)
                        }
                        subscriptionPlanCategory.durations.firstOrNull {
                            it.durationType == durationType
                        }?.emi?.let {
                            EMISection(it)
                        }
                        HowItWorksSection(subscriptionPlanCategory.feature)
                    }
                }

                // Top bar with a back button.
                Box(
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AppTopBar(backIcon = Icons.Filled.ArrowBack, onBack = onBack)
                }
            }
        }

        // Bottom button in a separate Box at the bottom of the screen.
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            AppFilledButton(
                textToShow = "Continue to Buy",
                onClick = {
                    onProceedToBuy(
                        OrderRequest(
                            amtDetails = OrderRequest.AmtDetails(
                                amt = subscriptionPlanCategory.durations.first {
                                    it.durationType == durationType
                                }.price.toInt()
                                        - (offer?.offer
                                    ?: 0) - walletPoints - walletMoney - discountMoney,
                                discountCode = discountCode,
                                walletMoney = walletMoney,
                                walletPoints = walletPoints,
                                offerCode = offer?.code ?: ""
                            ),
                            subscriptionDetail = OrderRequest.SubscriptionDetail(
                                subType = subscriptionPlanCategory.subscriptionType,
                                durType = durationType
                            ),
                            type = OrderRequestType.Subscription.code
                        )
                    )
                },
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

/**
 * Composable function for displaying a professional plan card.
 */
@Composable
fun ProMembershipCard(subscriptionPlanCategory: SubscriptionPlanCategory, durationType: String) {
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
                TitleTexts.Level1(text = subscriptionPlanCategory.title)
                Column(horizontalAlignment = Alignment.End) {
                    HeadingTexts.Level4(
                        text = subscriptionPlanCategory.durations.find { it.durationType == durationType }?.price
                            ?: ""
                    )
                }
            }

            subscriptionPlanCategory.feature.forEach {
                Row(Modifier.fillMaxWidth()) {
                    AppIcon(imageVector = Icons.Filled.Build)
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
                    BodyTexts.Level1(text = it.ttl)
                }
            }
        }
    }
}

/**
 * Composable function for displaying a layout of offers.
 */
@Composable
fun OffersSection(offer: Offer) {

    // Column displaying offers with padding and spacing.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Title for the offers section.
        TitleTexts.Level1(text = "Offers")

        // Repeated OffersContent composable for each offer.
        IndividualOfferContent(offer.desc)
    }
}

/**
 * Composable function for displaying individual offer content.
 */
@Composable
private fun IndividualOfferContent(desc: String) {
    // Row displaying an icon, text, and a button for each offer.
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppIcon(painter = painterResource(id = R.drawable.sell))

        BodyTexts.Level1(
            text = desc,
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
fun EMISection(emi: String) {

    // Column displaying EMI details with padding and spacing.
    Column(
        modifier = Modifier
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
                text = "Starting from $emi/month",
            )
        }
    }
}

/**
 * Composable function for displaying "How it works" details.
 */
@Composable
fun HowItWorksSection(feature: List<SubscriptionPlanCategory.Feature>) {

    // Column displaying "How it works" details with padding and spacing.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        // Title for the "How it works" section.
        TitleTexts.Level1(text = "How it works")

        feature.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AppIcon(imageVector = Icons.Filled.Check)

                BodyTexts.Level1(
                    text = it.dsc
                )
            }
        }

    }
}