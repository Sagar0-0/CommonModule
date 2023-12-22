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
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.subscription.remote.model.DurationType
import fit.asta.health.subscription.remote.model.SubscriptionResponse.SubscriptionPlans.SubscriptionPlanType
import fit.asta.health.subscription.remote.model.SubscriptionType

/**
 * Preview function for the PlanScreen.
 */
@Preview
@Composable
fun PlanScreen() {
    AppTheme {
        SubscriptionDurationsScreen(SubscriptionPlanType(), {}) { _, _ ->

        }
    }
}

/**
 * Main content of the PlanScreen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDurationsScreen(
    planSubscriptionPlanType: SubscriptionPlanType,
    onBack: () -> Unit,
    onClick: (subType: SubscriptionType, durType: DurationType) -> Unit
) {
    val plansList: List<PlansData> =
        planSubscriptionPlanType.subscriptionDurationPlans.map { duration ->
            PlansData(
                durationType = duration.durationType,
                discount = duration.discountAmount,
                price = duration.priceMRP,
                desc = duration.dsc,
            )
        }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Explore ${planSubscriptionPlanType.planName} plans",
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
                            planSubscriptionPlanType.subscriptionType,
                            planSubscriptionPlanType.subscriptionDurationPlans[idx].durationType
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
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
                .padding(AppTheme.spacing.level2)
        ) {
            // Display plan description
            DisplayPlanDescription(plansData = plansData)
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
        LargeTexts.Level3(text = plansData.price)
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
        LargeTexts.Level2(text = plansData.calculateMonth())
        HeadingTexts.Level4(text = "MONTHS")
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
    BodyTexts.Level2(text = plansData.desc)
}

/**
 * Data class representing the details of a subscription plan.
 *
 * @property durationType SubscriptionDurationPlan of the plan in months.
 * @property discount Discount information for the plan.
 * @property price Price of the plan.
 * @property tax Tax details for the plan.
 * @property desc Description of the plan.
 * @property emi EMI details for the plan.
 */
data class PlansData(
    val durationType: DurationType = "",
    val discount: String = "",
    val price: String = "",
    val desc: String = ""
) {
    fun calculateMonth(): String {
        return when (durationType) {
            "1" -> {
                "1"
            }

            "2" -> {
                "3"
            }

            "3" -> {
                "6"
            }

            "4" -> {
                "12"
            }

            else -> {
                "X"
            }
        }
    }
}
