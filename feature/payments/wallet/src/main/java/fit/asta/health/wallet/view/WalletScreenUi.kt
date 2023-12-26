package fit.asta.health.wallet.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.subscription.remote.model.Offer
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.remote.model.WalletTransactionType
import fit.asta.health.wallet.remote.model.getWalletTransactionType
import kotlinx.coroutines.launch
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenUi(
    modifier: Modifier,
    walletData: WalletResponse,
    offersList: List<Offer>?,
    onNavigateWithOffer: (Offer) -> Unit,
    onProceedToAdd: (String) -> Unit
) {
    var isAddMoneySheetVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val closeSheet = {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (!bottomSheetState.isVisible) {
                isAddMoneySheetVisible = false
            }
        }
    }

    if (isAddMoneySheetVisible) AppModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {
            closeSheet()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var amount by rememberSaveable { mutableStateOf("0") }
            AppTextField(
                modifier = Modifier.padding(AppTheme.spacing.level2),
                value = amount,
                onValueChange = {
                    amount = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onProceedToAdd(amount)
                    }
                )
            )
            AppTextButton(
                modifier = Modifier.padding(AppTheme.spacing.level2),
                textToShow = "Proceed to add",
                onClick = {
                    onProceedToAdd(amount)
                }
            )
        }

    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        offersList?.let {
            OffersCarousal(
                offersList = offersList
            ) {
                onNavigateWithOffer(it)
            }
        }

        Spacer(modifier = Modifier.padding(AppTheme.spacing.level2))

        WalletBalance(
            points = walletData.walletData.points,
            money = walletData.walletData.money,
            onAddMoneyClick = {
                isAddMoneySheetVisible = true
            }
        )

        val transactionHistory = walletData.walletTransactionData
        if (!transactionHistory.isNullOrEmpty()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2)
                    .height(1.dp)
                    .background(AppTheme.colors.surfaceVariant)
            )
            AppCard(
                modifier = Modifier
                    .padding(AppTheme.spacing.level2)
            ) {
                TitleTexts.Level2(
                    modifier = Modifier
                        .padding(
                            top = AppTheme.spacing.level2,
                            start = AppTheme.spacing.level2
                        ),
                    text = "Your transaction history:",
                    textAlign = TextAlign.Start
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.spacing.level2),
                    contentPadding = PaddingValues(AppTheme.spacing.level2)
                ) {
                    items(transactionHistory) { item ->
                        TransactionHistoryItem(item)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = DrawR.drawable.placeholder_tag),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                TitleTexts.Level2(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = StringR.string.no_transactions_text)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OffersCarousal(offersList: List<Offer>, onOfferClick: (Offer) -> Unit) {
    val pagerState = rememberPagerState { offersList.size }
    Box {
        AppHorizontalPager(
            modifier = Modifier.padding(AppTheme.spacing.level2),
            pagerState = pagerState,
            contentPadding = PaddingValues(horizontal = AppTheme.spacing.level3),
            pageSpacing = AppTheme.spacing.level2,
            enableAutoAnimation = true,
            userScrollEnabled = true
        ) { page ->
            AppCard(
                shape = AppTheme.shape.level1,
                onClick = {
                    onOfferClick(offersList[page])
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    BodyTexts.Level2(text = offersList[page].title)
                }
            }
        }

        // This function draws the Dot Indicator for the Pager
        AppExpandingDotIndicator(
            modifier = Modifier
                .padding(bottom = AppTheme.spacing.level2)
                .align(Alignment.BottomCenter),
            pagerState = pagerState
        )
    }

}

@Composable
fun TransactionHistoryItem(item: WalletResponse.WalletTransactionData) {
    val received = when (
        item.transactionType.getWalletTransactionType()
    ) {
        WalletTransactionType.SUBSCRIPTION_CASHBACK,
        WalletTransactionType.REFERRAL_CASHBACK,
        WalletTransactionType.ADD_MONEY -> {
            true
        }

        else -> {
            false
        }

    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = AppTheme.spacing.level2),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIcon(
            modifier = Modifier.padding(end = AppTheme.spacing.level1),
            imageVector = if (received) Icons.Default.MonetizationOn else Icons.Default.MoneyOff,
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(1f)) {
            TitleTexts.Level2(
                overflow = TextOverflow.Ellipsis,
                text = when (item.transactionType.getWalletTransactionType()) {
                    WalletTransactionType.SUBSCRIPTION_CASHBACK -> {
                        "Cashback received for Subscription"
                    }

                    WalletTransactionType.REFERRAL_CASHBACK -> {
                        "Cashback received for Referral"
                    }

                    WalletTransactionType.SEND_TO_BANK -> {
                        "Redeem to bank account"
                    }

                    WalletTransactionType.ADD_MONEY -> {
                        "Added money to wallet"
                    }

                    WalletTransactionType.WALLET_MONEY_USED -> {
                        "Made purchase"
                    }

                    WalletTransactionType.WALLET_POINTS_USED -> {
                        "Made purchase"
                    }
                }
            )
            TitleTexts.Level2(
                text = item.timeStamp
            )
        }
        TitleTexts.Level2(
            color = if (received) Color.Green else Color.Red,
            text = when (item.transactionType.getWalletTransactionType()) {
                WalletTransactionType.SUBSCRIPTION_CASHBACK -> {
                    "+ ${item.creditAmounts!!.points}"
                }

                WalletTransactionType.REFERRAL_CASHBACK -> {
                    "+ ${item.creditAmounts!!.points}"
                }

                WalletTransactionType.SEND_TO_BANK -> {
                    "- ${item.debitAmounts!!.money}"
                }

                WalletTransactionType.ADD_MONEY -> {
                    "+ ${item.creditAmounts!!.money}"
                }

                WalletTransactionType.WALLET_MONEY_USED -> {
                    "- ${item.debitAmounts!!.money}"
                }

                WalletTransactionType.WALLET_POINTS_USED -> {
                    "- ${item.debitAmounts!!.points}"
                }
            }
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2)
            .height(1.dp)
            .background(AppTheme.colors.onSecondaryContainer)
    )
}

@Composable
fun WalletBalance(points: Int, money: Int, onAddMoneyClick: () -> Unit) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(AppTheme.spacing.level1)
            ) {
                TitleTexts.Level2(text = "$money/$points")
                Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                TitleTexts.Level2(text = "Money/Points")
            }
            AppTextButton(
                textToShow = "Add money",
                modifier = Modifier.padding(AppTheme.spacing.level1),
                onClick = onAddMoneyClick
            )
        }
    }
}