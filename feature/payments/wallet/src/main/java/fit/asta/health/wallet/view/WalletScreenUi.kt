package fit.asta.health.wallet.view

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.background.AppModalBottomSheet
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.pager.AppExpandingDotIndicator
import fit.asta.health.designsystem.molecular.pager.AppHorizontalPager
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.offers.remote.model.OffersData
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.remote.model.WalletTransactionType
import fit.asta.health.wallet.remote.model.getWalletTransactionType
import kotlinx.coroutines.launch
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR


@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun WalletScreenUiPreview() {
    AppTheme {
        WalletScreenUi(
            modifier = Modifier,
            walletData = WalletResponse(
                walletData = WalletResponse.WalletData(
                    money = 100.0,
                    points = 9.0
                ),
                walletTransactionData = listOf(
                    WalletResponse.WalletTransactionData(
                        id = "lacinia",
                        uid = "feugait",
                        tid = "singulis",
                        transactionType = 3779,
                        referredBy = "lacus",
                        referee = "liber",
                        timeStamp = "ad",
                        from = "varius",
                        to = "sententiae",
                        debitAmounts = WalletResponse.WalletTransactionDebitData(
                            points = 4.5, money = 6.7
                        ),
                        creditAmounts = WalletResponse.WalletTransactionCreditData(
                            points = 4.5, money = 6.7
                        )
                    ),
                    WalletResponse.WalletTransactionData(
                        id = "lacinia",
                        uid = "feugait",
                        tid = "singulis",
                        transactionType = 3779,
                        referredBy = "lacus",
                        referee = "liber",
                        timeStamp = "ad",
                        from = "varius",
                        to = "sententiae",
                        debitAmounts = WalletResponse.WalletTransactionDebitData(
                            points = 4.5, money = 6.7
                        ),
                        creditAmounts = WalletResponse.WalletTransactionCreditData(
                            points = 4.5, money = 6.7
                        )
                    ),
                    WalletResponse.WalletTransactionData(
                        id = "lacinia",
                        uid = "feugait",
                        tid = "singulis",
                        transactionType = 3779,
                        referredBy = "lacus",
                        referee = "liber",
                        timeStamp = "ad",
                        from = "varius",
                        to = "sententiae",
                        debitAmounts = WalletResponse.WalletTransactionDebitData(
                            points = 4.5, money = 6.7
                        ),
                        creditAmounts = WalletResponse.WalletTransactionCreditData(
                            points = 4.5, money = 6.7
                        )
                    ),
                )
            ),
            onProceedToAdd = {

            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenUi(
    modifier: Modifier,
    walletData: WalletResponse,
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
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            var amount by rememberSaveable { mutableStateOf("") }
            AppOutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onProceedToAdd(amount)
                    }
                )
            )
            AppOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2),
                textToShow = "Proceed to add"
            ) {
                onProceedToAdd(amount)
            }
        }

    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        WalletBalance(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.spacing.level2)
                .padding(horizontal = AppTheme.spacing.level2),
            points = walletData.walletData.points,
            money = walletData.walletData.money,
            onAddMoneyClick = {
                isAddMoneySheetVisible = true
            }
        )

        val transactionHistory = walletData.walletTransactionData
        if (!transactionHistory.isNullOrEmpty()) {
            AppDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2)
            )
            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level2)
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
                    contentPadding = PaddingValues(horizontal = AppTheme.spacing.level2),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
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
fun OffersCarousal(offersList: List<OffersData>, onOfferClick: (OffersData) -> Unit) {
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
        WalletTransactionType.RefereeCredit,
        WalletTransactionType.ReferrerCredit,
        WalletTransactionType.AccountToWallet -> {
            true
        }

        else -> {
            false
        }

    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppIcon(
            modifier = Modifier.padding(end = AppTheme.spacing.level1),
            imageVector = if (received) Icons.Default.MonetizationOn else Icons.Default.MoneyOff,
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(1f)) {
            TitleTexts.Level3(
                overflow = TextOverflow.Ellipsis,
                text = when (item.transactionType.getWalletTransactionType()) {
                    WalletTransactionType.WalletMoneyUsed -> {
                        "Money spent on Subscription"
                    }

                    WalletTransactionType.WalletToAccount -> {
                        "Redeemed to bank"
                    }

                    WalletTransactionType.ReferrerCredit -> {
                        "Referrer Credit Received"
                    }

                    WalletTransactionType.AccountToWallet -> {
                        "Added money to wallet"
                    }

                    WalletTransactionType.RefereeCredit -> {
                        "Referee Credit Received"
                    }

                    WalletTransactionType.WalletPointsUsed -> {
                        "Points spent on Subscription"
                    }
                }
            )
            BodyTexts.Level3(
                text = item.timeStamp
            )
        }

        TitleTexts.Level2(
            color = if (received) Color.Green else Color.Red,
            text = when (item.transactionType) {
                WalletTransactionType.WalletMoneyUsed.code -> {
                    "- ${item.debitAmounts?.money}"
                }

                WalletTransactionType.WalletToAccount.code -> {
                    "- ${item.debitAmounts?.money}"
                }

                WalletTransactionType.ReferrerCredit.code -> {
                    "+ ${item.creditAmounts?.points}"
                }

                WalletTransactionType.AccountToWallet.code -> {
                    "+ ${item.creditAmounts?.money}"
                }

                WalletTransactionType.RefereeCredit.code -> {
                    "+ ${item.creditAmounts?.points}"
                }

                WalletTransactionType.WalletPointsUsed.code -> {
                    "- ${item.debitAmounts?.points}"
                }

                else -> {
                    ""
                }
            }
        )
    }
    Spacer(modifier = Modifier.padding(AppTheme.spacing.level1))
    AppDivider(
        color = AppTheme.colors.surface
    )
}

@Composable
fun WalletBalance(
    modifier: Modifier,
    points: Double,
    money: Double,
    onAddMoneyClick: () -> Unit
) {
    AppCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0)
            ) {
                TitleTexts.Level2(text = "$money/$points")
                TitleTexts.Level2(text = "Money/Points")
            }
            AppTextButton(
                textToShow = "Add money",
                onClick = onAddMoneyClick
            )
        }
    }
}