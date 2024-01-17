package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
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
    onClick: (productId: String) -> Unit
) {
    val plansList: List<PlansData> =
        data.planDurations.map { duration ->
            PlansData(
                title = duration.ttl,
                productId = "1",//TODO: USE PRODUCT ID FROM API
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
        Column {
            LazyColumn(
                modifier = Modifier
                    .padding(padd)
                    .fillMaxSize()
                    .padding(start = AppTheme.spacing.level2, end = AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                itemsIndexed(plansList) { idx, plansData ->
                    // Display each plan as a card
                    PlanDurationCard(plansData = plansData) {
                        onClick(
                            plansData.productId
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(AppTheme.spacing.level2))
        }


    }
}

/**
 * Composable function to display individual plan cards.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
fun PlanDurationCard(plansData: PlansData, onClick: () -> Unit) {
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
                DisplayPlanDuration(plansData = plansData)

                // Display plan details
                DisplayPriceDetails(plansData = plansData)

                // Display arrow button for more actions
                DisplayArrowButton()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Display plan description
                DisplayPlanDescription(plansData = plansData)
            }
        }

    }
}

@Composable
private fun RowScope.DisplayPriceDetails(plansData: PlansData) {
    Column(
        modifier = Modifier.align(Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        TitleTexts.Level3(text = plansData.price)
        BodyTexts.Level2(
            text = plansData.price + "/- OFF",
            modifier = Modifier.alpha(AppTheme.alphaValues.level2),
            color = Color.LightGray
        )
    }
}

/**
 * Composable function to display the plan duration and months.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
private fun DisplayPlanDuration(plansData: PlansData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleTexts.Level2(text = plansData.title)
        TitleTexts.Level4(text = "MONTHS")
    }
}

///**
// * Composable function to display the plan details.
// *
// * @param plansData Data for a specific plan.
// */
//@Composable
//private fun DisplayPlanDetails(plansData: PlansData) {
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
//private fun DisplayDiscountOrFreeTrial(plansData: PlansData) {
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
//private fun DisplayPlanPriceEmiTax(plansData: PlansData) {
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
 * @param plansData Data for a specific plan.
 */
@Composable
private fun DisplayPlanDescription(plansData: PlansData) {
    CaptionTexts.Level2(text = plansData.desc)
}


data class PlansData(
    val title: String = "",
    val productId: String = "",
    val discount: String = "",
    val price: String = "",
    val desc: String = ""
)
