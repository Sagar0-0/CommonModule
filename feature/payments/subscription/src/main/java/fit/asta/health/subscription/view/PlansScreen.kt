package fit.asta.health.subscription.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.SubscriptionResponse.SubscriptionPlans.SubscriptionPlanCategory

/**
 * Preview function for the PlanScreen.
 */
@Preview
@Composable
fun PlanScreen() {
    AppTheme {
        SubscriptionDurationsScreen(SubscriptionPlanCategory(), {}) { _, _ ->

        }
    }
}

/**
 * Main content of the PlanScreen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDurationsScreen(
    planSubscriptionPlanCategory: SubscriptionPlanCategory,
    onBack: () -> Unit,
    onClick: (subType: String, durType: String) -> Unit
) {
    val plansList: List<PlansData> = planSubscriptionPlanCategory.durations.map { duration ->
        PlansData(
            month = duration.ttl,
            discount = duration.discount,
            price = duration.price,
            tax = duration.tax,
            desc = duration.dsc,
            emi = duration.emi
        )
    }
    AppScaffold(
        topBar = {
            AppTopBar(title = "Explore ${planSubscriptionPlanCategory.title} plans") {
                onBack()
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            itemsIndexed(plansList) { idx, plansData ->
                // Display each plan as a card
                PlansCard(plansData = plansData) {
                    onClick(
                        planSubscriptionPlanCategory.subscriptionType,
                        planSubscriptionPlanCategory.durations[idx].durationType
                    )
                }
            }
        }
    }
}

/**
 * Composable function to display individual plan cards.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
fun PlansCard(plansData: PlansData, onClick: () -> Unit) {
    // Card container with plan details
    AppCard(Modifier.fillMaxWidth(), onClick = onClick) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Display plan duration and months
                DisplayPlanDuration(plansData = plansData)

                // Display plan details
                DisplayPlanDetails(plansData = plansData)

                // Display arrow button for more actions
                DisplayArrowButton()
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2)
            ) {
                // Display plan description
                DisplayPlanDescription(plansData = plansData)
            }
        }
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
        LargeTexts.Level1(text = plansData.month)
        HeadingTexts.Level4(text = "MONTHS")
    }
}

/**
 * Composable function to display the plan details.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
private fun DisplayPlanDetails(plansData: PlansData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
        horizontalAlignment = Alignment.Start
    ) {
        // Display discount or "Free Trial"
        DisplayDiscountOrFreeTrial(plansData = plansData)

        // Display plan price, EMI, and tax
        DisplayPlanPriceEmiTax(plansData = plansData)
    }
}

/**
 * Composable function to display the discount or "Free Trial".
 *
 * @param plansData Data for a specific plan.
 */
@Composable
private fun DisplayDiscountOrFreeTrial(plansData: PlansData) {
    if (plansData.discount.isEmpty()) {
        LargeTexts.Level3(text = "Free Trial")
    } else {
        TitleTexts.Level4(
            text = plansData.discount,
            color = AppTheme.colors.onSurface.copy(0.5f)
        )
    }
}

/**
 * Composable function to display the plan price, EMI, and tax.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
private fun DisplayPlanPriceEmiTax(plansData: PlansData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTexts.Level1(text = plansData.price)
        Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
        plansData.emi?.let {
            BodyTexts.Level3(
                text = it,
                color = AppTheme.colors.onSurface.copy(0.3f)
            )
        }
    }
    TitleTexts.Level4(
        text = plansData.tax,
        color = AppTheme.colors.onSurface.copy(0.8f)
    )
}

/**
 * Composable function to display the arrow button for more actions.
 */
@Composable
private fun DisplayArrowButton() {
    AppSurface(
        shape = CircleShape,
        modifier = Modifier.size(AppTheme.iconSize.level5)
    ) {
        AppIconButton(imageVector = Icons.Filled.ArrowForward, onClick = {})
    }
}

/**
 * Composable function to display the plan description.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
private fun DisplayPlanDescription(plansData: PlansData) {
    BodyTexts.Level1(
        text = plansData.desc,
        modifier = Modifier.padding(end = AppTheme.spacing.level4),
        lineHeight = 30.sp,
    )
}

/**
 * Data class representing the details of a subscription plan.
 *
 * @property month Duration of the plan in months.
 * @property discount Discount information for the plan.
 * @property price Price of the plan.
 * @property tax Tax details for the plan.
 * @property desc Description of the plan.
 * @property emi EMI details for the plan.
 */
data class PlansData(
    val month: String = "",
    val discount: String = "",
    val price: String = "",
    val tax: String = "",
    val desc: String = "",
    val emi: String? = null
)

/**
 * Function to add a strikethrough effect to the input string.
 *
 * @param input The input string to be modified.
 * @return The input string with a strikethrough effect applied.
 */
fun addStrikethrough(input: String): String {
    // U+0336 is the Unicode combining diacritical mark for strike-through
    val strikethrough = "\u0336"
    return input.map { "$it$strikethrough" }.joinToString("")
}
