package fit.asta.health.wallet.view

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppNonInternetErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenUi(
    walletDataState: UiState<WalletResponse>,
    onBackPress: () -> Unit,
    onTryAgain: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar(title = "Wallet", onBack = onBackPress)
        },
    ) { paddingValues ->
        when (walletDataState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
            }

            is UiState.ErrorMessage -> {
                AppNonInternetErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    issueDescription = walletDataState.resId.toStringFromResId()
                ) {
                    onTryAgain()
                }
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WalletBalance(walletDataState.data.walletData.credits) {

                    }

                    val transactionHistory = walletDataState.data.transactionData
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

            else -> {}
        }
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
fun WalletBalance(amount: Int, onButtonClick: () -> Unit) {
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
                TitleTexts.Level2(text = "available balance")
            }
            AppTextButton(
                textToShow = "Redeem",
                modifier = Modifier.padding(AppTheme.spacing.level1),
                onClick = onButtonClick
            )
        }
    }
}
