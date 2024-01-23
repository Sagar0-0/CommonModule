package fit.asta.health.subscription

import android.content.res.Configuration
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
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppCheckBoxButton
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.discounts.remote.model.CouponResponse
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderRequestType
import fit.asta.health.wallet.remote.model.WalletResponse

/**
 * Composable function for the Buy Plan Screen. It sets up the UI using the AppTheme and a Box layout.
 */
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun BuyPlanScreen() {

    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SubscriptionCheckoutScreen(
                subscriptionCheckoutScreenData = SubscriptionCheckoutScreenData(
                    planName = "Bronze",
                    planDesc = "Some description here",
                    featuresList = listOf(
                        "abcolor",
                        "Default"
                    )
                ),
                couponResponseState = UiState.Success(
                    CouponResponse(

                    )
                ),
                walletResponseState = UiState.Success(
                    WalletResponse(
                        walletData = WalletResponse.WalletData(
                            money = 4.5, points = 6.7, id = "movet", uid = "quaeque"
                        )
                    )
                )
            ) {

            }
        }
    }
}

@Stable
data class SubscriptionCheckoutScreenData(
    val planMRP: Double = 0.0,
    val offerAmount: Double = 0.0,
    val imageUrl: String = "",
    val planName: String = "",
    val planDesc: String = "",
    val offerCode: String = "",
    val categoryId: String = "",
    val productId: String = "",
    val featuresList: List<String> = listOf(),
)

sealed interface BuyScreenEvent {
    data object BACK : BuyScreenEvent
    data object FetchWalletData : BuyScreenEvent
    data object ResetCouponState : BuyScreenEvent
    data class ProceedToBuy(val orderRequest: OrderRequest) : BuyScreenEvent
    data class ApplyCouponCode(val code: String, val productMRP: Double) : BuyScreenEvent
}

/**
 * Composable function for the content of the Buy Plan Screen.
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SubscriptionCheckoutScreen(
    subscriptionCheckoutScreenData: SubscriptionCheckoutScreenData,
    couponResponseState: UiState<CouponResponse>,
    walletResponseState: UiState<WalletResponse>,
    onEvent: (BuyScreenEvent) -> Unit,
) {
    val (couponCode, onCouponChange) = rememberSaveable {
        mutableStateOf("")
    }

    val couponDiscountMoney by rememberSaveable(couponResponseState) {
        mutableDoubleStateOf((couponResponseState as? UiState.Success)?.data?.discountAmount ?: 0.0)
    }

    val totalWalletPoints by rememberSaveable(walletResponseState) {
        mutableDoubleStateOf(
            (walletResponseState as? UiState.Success)?.data?.walletData?.points ?: 0.0
        )
    }

    val totalWalletMoney by rememberSaveable(walletResponseState) {
        mutableDoubleStateOf(
            (walletResponseState as? UiState.Success)?.data?.walletData?.money ?: 0.0
        )
    }

    var walletPointsUsed by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }

    var walletMoneyUsed by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }

    val finalPayableAmount =
        rememberSaveable(walletPointsUsed, walletMoneyUsed, couponDiscountMoney) {
            subscriptionCheckoutScreenData.planMRP - subscriptionCheckoutScreenData.offerAmount - walletMoneyUsed - walletPointsUsed - couponDiscountMoney
        }

    // AppSurface is a custom composable providing a themed surface for the content.
    AppScaffold(
        topBar = {
            AppTopBar(
                onBack = {
                    onEvent(BuyScreenEvent.BACK)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            // AppLocalImage is a custom composable to load and display local images.
            AppNetworkImage(
                model = getImgUrl(subscriptionCheckoutScreenData.imageUrl),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(AppTheme.aspectRatio.fullScreen)
            )

            // Various card composable and layouts are included.
            ProMembershipCard(
                planName = subscriptionCheckoutScreenData.planName,
                desc = subscriptionCheckoutScreenData.planDesc,
                finalAmount = finalPayableAmount.toString(),
                featuresList = subscriptionCheckoutScreenData.featuresList
            )

            AppUiStateHandler(
                uiState = walletResponseState,
                onLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(AppTheme.shape.level2)
                            .padding(AppTheme.spacing.level2)
                            .appShimmerAnimation(true)
                    ) {
                        BodyTexts.Level2(
                            modifier = Modifier
                                .padding(AppTheme.spacing.level2),
                            text = "Fetching Wallet Data..."
                        )
                    }
                },
                onErrorRetry = {
                    onEvent(BuyScreenEvent.FetchWalletData)
                }
            ) {
                WalletApplyingSection(
                    totalPoints = totalWalletPoints,
                    totalMoney = totalWalletMoney,
                    pointsApplied = walletPointsUsed,
                    moneyApplied = walletMoneyUsed,
                    finalPayableAmount = finalPayableAmount
                ) { points, money ->
                    walletPointsUsed = points
                    walletMoneyUsed = money
                }
            }

            CouponSection(
                couponResponseState = couponResponseState,
                couponCode = couponCode,
                onCouponChange = onCouponChange,
                onEvent = onEvent
            ) { discountCodeApplied ->
                onEvent(
                    BuyScreenEvent.ApplyCouponCode(
                        discountCodeApplied, subscriptionCheckoutScreenData.planMRP
                    )
                )
            }

            PriceBreakdownSection(
                subscriptionCheckoutScreenData.planMRP,
                subscriptionCheckoutScreenData.offerAmount,
                walletMoneyUsed,
                walletPointsUsed,
                couponDiscountMoney,
                finalPayableAmount
            )

            AppFilledButton(
                modifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level2)
                    .fillMaxWidth(),
                textToShow = "Continue to Buy",
                onClick = {
                    onEvent(
                        BuyScreenEvent.ProceedToBuy(
                            OrderRequest(
                                amtDetails = OrderRequest.AmtDetails(
                                    amt = finalPayableAmount,
                                    couponCode = (couponResponseState as? UiState.Success)?.data?.couponDetails?.code
                                        ?: "",
                                    walletMoney = walletMoneyUsed,
                                    walletPoints = walletPointsUsed,
                                    offerCode = subscriptionCheckoutScreenData.offerCode
                                ),
                                subscriptionDetail = OrderRequest.SubscriptionDetail(
                                    productCategoryId = subscriptionCheckoutScreenData.categoryId,
                                    productId = subscriptionCheckoutScreenData.productId
                                ),
                                type = OrderRequestType.Subscription.code
                            )
                        )
                    )
                }
            )
            Spacer(modifier = Modifier)
        }
    }
}

@Composable
fun PriceBreakdownSection(
    planMRP: Double,
    offerAmount: Double,
    walletMoneyUsed: Double,
    walletPointsUsed: Double,
    couponDiscountMoney: Double,
    finalPayableAmount: Double
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadingTexts.Level3(text = "Listing Price")
                HeadingTexts.Level3(text = planMRP.toString())
            }
            if (offerAmount != 0.0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeadingTexts.Level3(text = "Offer Discount")
                    HeadingTexts.Level3(text = "-$offerAmount")
                }
            }
            if (walletMoneyUsed != 0.0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeadingTexts.Level3(text = "Wallet Money used")
                    HeadingTexts.Level3(text = "-$walletMoneyUsed")
                }
            }
            if (walletPointsUsed != 0.0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeadingTexts.Level3(text = "Wallet Points used")
                    HeadingTexts.Level3(text = "-$walletPointsUsed")
                }
            }
            if (couponDiscountMoney != 0.0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HeadingTexts.Level3(text = "Coupon applied")
                    HeadingTexts.Level3(text = "-$couponDiscountMoney")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadingTexts.Level3(text = "Final Price")
                HeadingTexts.Level3(text = finalPayableAmount.toString())
            }
        }
    }
}

const val COUPON_CODE_SIZE_LIMIT = 10

@Composable
fun CouponSection(
    couponResponseState: UiState<CouponResponse>,
    couponCode: String,
    onCouponChange: (String) -> Unit,
    onEvent: (BuyScreenEvent) -> Unit,
    onApplyCode: (discountCode: String) -> Unit
) {
    AppUiStateHandler(
        uiState = couponResponseState,
        onIdle = {
            AppOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                value = couponCode,
                onValueChange = onCouponChange,
                label = "Coupon code",
                trailingIcon = Icons.Default.ArrowForwardIos,
                onTrailingIconClicked = {
                    onApplyCode(couponCode)
                },
                appTextFieldType = AppTextFieldValidator(
                    AppTextFieldType.Custom(
                        COUPON_CODE_SIZE_LIMIT,
                        COUPON_CODE_SIZE_LIMIT
                    )
                )
            )
        },
        onErrorMessage = {
            onEvent(BuyScreenEvent.ResetCouponState)
        },
        onErrorRetry = {
            onEvent(BuyScreenEvent.ResetCouponState)
        }
    ) {
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level2)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeadingTexts.Level3(text = "Coupon $couponCode applied")
                AppIconButton(
                    onClick = {
                        onEvent(BuyScreenEvent.ResetCouponState)
                    }
                ) {
                    AppIcon(imageVector = Icons.Default.Cancel)
                }
            }
        }
    }
}

@Composable
fun WalletApplyingSection(
    totalPoints: Double,
    totalMoney: Double,
    pointsApplied: Double,
    moneyApplied: Double,
    finalPayableAmount: Double,
    onApplyWallet: (pointsApplied: Double, moneyApplied: Double) -> Unit
) {
    var pointsChecked by rememberSaveable {
        mutableStateOf(false)
    }
    var moneyChecked by rememberSaveable {
        mutableStateOf(false)
    }
    val pointsToDisplay by rememberSaveable(pointsApplied) {
        mutableDoubleStateOf(totalPoints - pointsApplied)
    }
    val moneyToDisplay by rememberSaveable(moneyApplied) {
        mutableDoubleStateOf(totalMoney - moneyApplied)
    }

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadingTexts.Level3(text = "Wallet Points: $pointsToDisplay")
                if (totalPoints > 0) {
                    AppCheckBoxButton(
                        checked = pointsChecked
                    ) { checked ->
                        pointsChecked = checked
                        onApplyWallet(
                            if (checked) {
                                if (finalPayableAmount > totalPoints) {
                                    totalPoints
                                } else {
                                    finalPayableAmount
                                }
                            } else {
                                0.0
                            },
                            0.0
                        )
                    }
                }
            }

            AppDivider(color = AppTheme.colors.onSurfaceVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadingTexts.Level3(text = "Wallet Money: $moneyToDisplay")
                if (totalMoney > 0) {
                    AppCheckBoxButton(
                        checked = moneyChecked
                    ) { checked ->
                        moneyChecked = checked
                        onApplyWallet(
                            0.0,
                            if (checked) {
                                if (finalPayableAmount > totalMoney) {
                                    totalMoney
                                } else {
                                    finalPayableAmount
                                }
                            } else {
                                0.0
                            }
                        )
                    }
                }
            }
        }
    }


}

/**
 * Composable function for displaying a professional plan card.
 */
@Composable
fun ProMembershipCard(
    planName: String,
    desc: String,
    finalAmount: String,
    featuresList: List<String>
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    HeadingTexts.Level1(text = planName)
                    CaptionTexts.Level2(text = desc)
                }
                HeadingTexts.Level4(
                    text = "$finalAmount-/"
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                featuresList.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppIcon(imageVector = Icons.Filled.Build)
                        Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
                        BodyTexts.Level3(text = it)
                    }
                }
            }
        }
    }
}