package fit.asta.health.payments.wallet.view

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.wallet.model.WalletResponse

@Composable
fun WalletScreen(
    walletDataState: ResponseState<WalletResponse>,
    onBackPress: () -> Unit,
    onTryAgain: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Wallet", onBack = onBackPress)
        },
    ) { paddingValues ->
        when (walletDataState) {
            is ResponseState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            is ResponseState.Error -> {
                Log.e("Wallet", "WalletScreen: ${walletDataState.error}")
                AppErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    desc = "Something went wrong!",
                    isInternetError = false
                ) {
                    onTryAgain()
                }
            }

            is ResponseState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WalletBalance(walletDataState.data.data.walletData.credits) {

                    }

                    val transactionHistory = walletDataState.data.data.transactionData
                    if (!transactionHistory.isNullOrEmpty()) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(spacing.medium)
                                .height(1.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                        Card(
                            modifier = Modifier
                                .padding(spacing.medium),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = spacing.medium, start = spacing.medium),
                                text = "Your transaction history:",
                                textAlign = TextAlign.Start
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = spacing.medium),
                                contentPadding = PaddingValues(spacing.medium)
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
                                .padding(spacing.medium)
                                .weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.placeholder_tag),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.height(spacing.medium))
                            Text(
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.no_transactions_text)
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
fun TransactionHistoryItem(item: WalletResponse.Data.TransactionData) {
    val received = (item.creditType == 1 || item.creditType == 4)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = spacing.small),
            imageVector = if (received) Icons.Default.MonetizationOn else Icons.Default.MoneyOff,
            contentDescription = ""
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
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
                },
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = item.timeStamp,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Text(
            color = if (received) Color.Green else Color.Red,
            text = (if (received) "+" else "-") + item.credits.toString()
        )
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .height(1.dp)
            .background(MaterialTheme.colorScheme.onSecondaryContainer)
    )
}

@Composable
fun WalletBalance(amount: Int, onButtonClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(spacing.small)
            ) {
                Text(text = amount.toString(), style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(spacing.extraSmall))
                Text(text = "available balance")
            }
            Button(
                modifier = Modifier.padding(spacing.small),
                onClick = onButtonClick
            ) {
                Text(
                    modifier = Modifier.padding(spacing.small),
                    text = "Redeem"
                )
            }
        }
    }
}
