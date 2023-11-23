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
import fit.asta.health.wallet.remote.model.WalletResponse
import kotlinx.coroutines.launch
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenUi(
    modifier: Modifier,
    walletData: WalletResponse,
    offersList: List<String>,
    onProceedToAdd: (String) -> Unit
) {
    var isAddMoneySheetVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(true)
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

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OffersCarousal(
            offersList = offersList
        ) {
            // TODO: NAVIGATE TO THAT PARTICULAR OFFER
        }

        Spacer(modifier = Modifier.padding(AppTheme.spacing.level2))

        WalletBalance(
            amount = walletData.walletData.credits,
            onAddMoneyClick = {
                isAddMoneySheetVisible = true
            }
        )

        val transactionHistory = walletData.transactionData
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
fun OffersCarousal(offersList: List<String>, onOfferClick: (String) -> Unit) {
    val pagerState = rememberPagerState { offersList.size }
    Box {
        AppHorizontalPager(
            pagerState = pagerState,
            contentPadding = PaddingValues(horizontal = AppTheme.spacing.level3),
            pageSpacing = AppTheme.spacing.level2,
            enableAutoAnimation = true,
            userScrollEnabled = true
        ) { page ->
            AppCard(
                modifier = Modifier.fillMaxSize(),
                shape = AppTheme.shape.level1,
                onClick = {
                    onOfferClick(offersList[page])
                }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    BodyTexts.Level2(text = offersList[page])
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
fun TransactionHistoryItem(item: WalletResponse.TransactionData) {
    val received = (item.creditType == 1 || item.creditType == 4)
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
                text = when (item.creditType) {
                    1 -> {
                        "Cashback received for Referral"
                    }

                    2 -> {
                        "Redeem to bank account"
                    }

                    else -> {
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
            text = (if (received) "+" else "-") + item.credits.toString()
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
fun WalletBalance(amount: Int, onAddMoneyClick: () -> Unit) {
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
                TitleTexts.Level2(text = amount.toString())
                Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                TitleTexts.Level2(text = "Available balance")
            }
            AppTextButton(
                textToShow = "Add money",
                modifier = Modifier.padding(AppTheme.spacing.level1),
                onClick = onAddMoneyClick
            )
        }
    }
}