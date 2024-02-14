package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.SubscriptionDurationsData

/**
 * Preview function for the PlanScreen.
 */
@Preview
@Composable
fun PlanScreen() {
    AppTheme {
        SubscriptionDurationsScreen(
            SubscriptionDurationsData(
                planDurations = listOf(
                    SubscriptionDurationsData.Duration(
                        cur = 7733,
                        default = 8676,
                        dsc = "regione",
                        id = "similique",
                        price = "ipsum",
                        sub = "saepe",
                        sym = "legere",
                        tag = "utinam",
                        ttl = "electram"
                    ),
                    SubscriptionDurationsData.Duration(
                        cur = 7733,
                        default = 8676,
                        dsc = "regione",
                        id = "similique",
                        price = "ipsum",
                        sub = "saepe",
                        sym = "legere",
                        tag = "utinam",
                        ttl = "electram"
                    ),
                    SubscriptionDurationsData.Duration(
                        cur = 7733,
                        default = 8676,
                        dsc = "regione",
                        id = "similique",
                        price = "ipsum",
                        sub = "saepe",
                        sym = "legere",
                        tag = "utinam",
                        ttl = "electram"
                    ),
                ),
                planFeatures = listOf(
                    SubscriptionDurationsData.Feature(
                        dsc = "facilis", ttl = "mei", url = "http://www.bing.com/search?q=solet"
                    ),
                    SubscriptionDurationsData.Feature(
                        dsc = "facilis", ttl = "mei", url = "http://www.bing.com/search?q=solet"
                    ),
                    SubscriptionDurationsData.Feature(
                        dsc = "facilis", ttl = "mei", url = "http://www.bing.com/search?q=solet"
                    ),
                ),
                offers = listOf(
                    SubscriptionDurationsData.Offer(
                        areas = listOf(),
                        code = "dissentiunt",
                        con = "ponderum",
                        dsc = "atqui",
                        end = "mediocrem",
                        id = "interesset",
                        ofr = 6989,
                        start = "libris",
                        sts = 9717,
                        ttl = "sed",
                        type = 6900,
                        unit = 1866,
                        url = "https://duckduckgo.com/?q=tamquam"
                    ),
                    SubscriptionDurationsData.Offer(
                        areas = listOf(),
                        code = "dissentiunt",
                        con = "ponderum",
                        dsc = "atqui",
                        end = "mediocrem",
                        id = "interesset",
                        ofr = 6989,
                        start = "libris",
                        sts = 9717,
                        ttl = "sed",
                        type = 6900,
                        unit = 1866,
                        url = "https://duckduckgo.com/?q=tamquam"
                    ),
                    SubscriptionDurationsData.Offer(
                        areas = listOf(),
                        code = "dissentiunt",
                        con = "ponderum",
                        dsc = "atqui",
                        end = "mediocrem",
                        id = "interesset",
                        ofr = 6989,
                        start = "libris",
                        sts = 9717,
                        ttl = "sed",
                        type = 6900,
                        unit = 1866,
                        url = "https://duckduckgo.com/?q=tamquam"
                    ),
                ),
                termsAndConditions = SubscriptionDurationsData.Tnc(
                    eff = "nam",
                    id = "laoreet",
                    last = "novum",
                    sec = listOf(),
                    type = "ridiculus"
                )
            ), {}
        ) { _ ->

        }
    }
}

/**
 * Main content of the PlanScreen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDurationsScreen(
    data: SubscriptionDurationsData,
    onBack: () -> Unit,
    onNavigateToFinalPayment: (productId: String) -> Unit
) {
    val offersList: List<OffersUiData>? =
        data.offers?.map {
            OffersUiData(
                productId = it.areas[0].prod.toString(),
                type = it.type,
                url = it.url
            )
        }
    val featuresList: List<FeaturesUiData> =
        data.planFeatures.map {
            FeaturesUiData(
                title = it.ttl,
                description = it.dsc,
                url = it.url
            )
        }
    val durationsList: List<DurationUiData> =
        data.planDurations.map { duration ->
            DurationUiData(
                title = duration.ttl,
                productId = duration.id,
                price = duration.price,
                desc = duration.dsc,
            )
        }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Explore plan duration",
                onBack = onBack
            )
        }
    ) { padd ->
        Column(
            modifier = Modifier
                .padding(padd)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            offersList?.let {
                OffersBanner(it) { _, productId ->
                    onNavigateToFinalPayment(productId)
                }
            }
            FeaturesSection(featuresList)
            DurationsSection(durationsList, onNavigateToFinalPayment)
            Spacer(modifier = Modifier)
        }
    }
}

@Composable
fun DurationsSection(durationsList: List<DurationUiData>, onClick: (productId: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        HeadingTexts.Level1(text = "Select Duration:")
        durationsList.forEach { plansData ->
            PlanDurationCard(durationUiData = plansData) {
                onClick(plansData.productId)
            }
        }
    }
}

@Composable
fun FeaturesSection(featuresList: List<FeaturesUiData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        HeadingTexts.Level1(text = "Features included:")
        featuresList.forEach {
            AppCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level1)
                ) {
                    AppNetworkImage(
                        model = getImageUrl(it.url),
                        modifier = Modifier
                            .size(AppTheme.imageSize.level11)
                            .aspectRatio(ratio = AppTheme.aspectRatio.square)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = AppTheme.spacing.level0),
                    ) {
                        TitleTexts.Level2(text = it.title)
                        CaptionTexts.Level2(text = it.description)
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display individual plan cards.
 *
 * @param durationUiData Data for a specific plan.
 */
@Composable
fun PlanDurationCard(durationUiData: DurationUiData, onClick: () -> Unit) {
    // Card container with plan details
    AppCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Display plan duration and months
                DisplayPlanDuration(durationUiData = durationUiData)

                // Display plan details
                DisplayPriceDetails(durationUiData = durationUiData)

                // Display arrow button for more actions
                DisplayArrowButton()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Display plan description
                DisplayPlanDescription(durationUiData = durationUiData)
            }
        }

    }
}

@Composable
private fun RowScope.DisplayPriceDetails(durationUiData: DurationUiData) {
    Column(
        modifier = Modifier.align(Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TitleTexts.Level3(text = durationUiData.price)
        BodyTexts.Level2(
            text = durationUiData.price + "/- OFF",
            modifier = Modifier.alpha(AppTheme.alphaValues.level2),
            color = AppTheme.colors.onSurface.copy(AppTheme.alphaValues.level2)
        )
    }
}

/**
 * Composable function to display the plan duration and months.
 *
 * @param durationUiData Data for a specific plan.
 */
@Composable
private fun DisplayPlanDuration(durationUiData: DurationUiData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleTexts.Level2(text = durationUiData.title)
        TitleTexts.Level4(text = "MONTHS")
    }
}

///**
// * Composable function to display the plan details.
// *
// * @param plansData Data for a specific plan.
// */
//@Composable
//private fun DisplayPlanDetails(plansData: DurationUiData) {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
//        horizontalAlignment = Alignment.Start
//    ) {
//        // Display discount or "Free Trial"
//        DisplayDiscountOrFreeTrial(plansData = plansData)
//
//        // Display plan price, EMI, and tax
//        DisplayPlanPriceEmiTax(plansData = plansData)
//    }
//}

///**
// * Composable function to display the discount or "Free Trial".
// *
// * @param plansData Data for a specific plan.
// */
//@Composable
//private fun DisplayDiscountOrFreeTrial(plansData: DurationUiData) {
//    if (plansData.discount.isEmpty()) {
//        LargeTexts.Level3(text = "Free Trial")
//    } else {
//        TitleTexts.Level4(
//            text = plansData.discount,
//            color = AppTheme.colors.onSurface.copy(0.5f)
//        )
//    }
//}

///**
// * Composable function to display the plan price, EMI, and tax.
// *
// * @param plansData Data for a specific plan.
// */
//@Composable
//private fun DisplayPlanPriceEmiTax(plansData: DurationUiData) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        TitleTexts.Level1(text = plansData.price)
//        Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
//        plansData.emi?.let {
//            BodyTexts.Level3(
//                text = it,
//                color = AppTheme.colors.onSurface.copy(0.3f)
//            )
//        }
//    }
//    TitleTexts.Level4(
//        text = plansData.tax,
//        color = AppTheme.colors.onSurface.copy(0.8f)
//    )
//}

/**
 * Composable function to display the arrow button for more actions.
 */
@Composable
private fun DisplayArrowButton() {
    AppIcon(imageVector = Icons.Filled.ArrowForward)
}

/**
 * Composable function to display the plan description.
 *
 * @param durationUiData Data for a specific plan.
 */
@Composable
private fun DisplayPlanDescription(durationUiData: DurationUiData) {
    CaptionTexts.Level2(text = durationUiData.desc)
}

data class FeaturesUiData(
    val title: String,
    val description: String,
    val url: String
)

data class DurationUiData(
    val title: String = "",
    val productId: String = "",
    val discount: String = "",
    val price: String = "",
    val desc: String = ""
)
