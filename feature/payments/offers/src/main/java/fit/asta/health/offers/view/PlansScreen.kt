package fit.asta.health.offers.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

/**
 * Preview function for the PlanScreen.
 */
@Preview
@Composable
fun PlanScreen() {
    AppTheme {
        // The main container for the PlanScreen
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PlanScreenContent()
        }
    }
}

/**
 * Main content of the PlanScreen.
 */
@Composable
fun PlanScreenContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        item {
            // Title section
            TitleTexts.Level1(text = "Explore Subscriptions")
        }

        items(PlansDataManager.plansList) { plansData ->
            // Display each plan as a card
            PlansCard(plansData = plansData)
        }
    }
}

/**
 * Composable function to display individual plan cards.
 *
 * @param plansData Data for a specific plan.
 */
@Composable
fun PlansCard(plansData: PlansData) {
    // Card container with plan details
    AppCard(Modifier.fillMaxWidth()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            Row(
                Modifier.fillMaxWidth(),
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
        BodyTexts.Level3(
            text = plansData.emi,
            color = AppTheme.colors.onSurface.copy(0.3f)
        )
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
    val emi: String = "",
)

/**
 * Object managing the list of subscription plans.
 */
object PlansDataManager {
    val plansList = listOf(
        PlansData("7"),
        PlansData(
            "24",
            addStrikethrough("$3999"),
            "$2457",
            "+ $442 GST",
            generateOfferText(),
            "($102/month)"
        ),
        PlansData(
            "12",
            addStrikethrough("$2400"),
            "$2457",
            "+ $442 GST",
            generateOfferText(),
            "($102/month)"
        ),
        PlansData(
            "6",
            addStrikethrough("$1749"),
            "$2457",
            "+ $442 GST",
            generateOfferText(),
            "($102/month)"
        ),
        PlansData(
            "3",
            addStrikethrough("$1099"),
            "$2457",
            "+ $442 GST",
            generateOfferText(),
            "($102/month)"
        ),
    )

    private fun generateOfferText() = """
        + Extra $500 OFF applied
        + Only Today: Free Amazon voucher worth $250
        + Get FREE 4 Months extension
    """.trimIndent()
}

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
