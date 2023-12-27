package fit.asta.health.feature.orders.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.orders.remote.model.OrderDetailData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Preview("Orders Light")
@Preview(
    name = "Orders Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun OrderDetailScreenPreview() {
    AppTheme {
        OrderDetailScreen(
            orderData = OrderDetailData(
                amt = 8575,
                cDate = 9353,
                discount = 8530,
                offer = "malesuada",
                orderId = "accusata",
                paymentId = "xxxxxxxxxx",
                paymentMode = "definitionem",
                sts = "Active",
                ttl = "Title",
                type = 5208,
                url = "https://www.google.com/#q=graeci",
                walletMoney = 3096,
                walletPoints = 2893
            )
        ) {

        }
    }
}

@Composable
fun OrderDetailScreen(
    modifier: Modifier = Modifier,
    orderData: OrderDetailData,
    onUiEvent: (OrderDetailUiEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadingTexts.Level1(text = orderData.ttl)
                AppNetworkImage(
                    model = getImgUrl(orderData.url)
                )
            }
        }

        AppElevatedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Order Id")
                    TitleTexts.Level2(text = orderData.orderId)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Date")
                    TitleTexts.Level2(text = orderData.cDate.toString())
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Payment Mode")
                    TitleTexts.Level2(text = orderData.paymentMode)
                }
            }
        }

        AppElevatedCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleTexts.Level2(text = "Status")
                TitleTexts.Level2(text = orderData.sts)
            }
        }

        AppElevatedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "MRP")
                    TitleTexts.Level2(text = orderData.amt.toString())
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Discount")
                    TitleTexts.Level2(text = orderData.discount.toString())
                }

                Divider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Total Payable Amount")
                    TitleTexts.Level2(text = orderData.amt.toString())
                }
            }
        }

        AppElevatedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                TitleTexts.Level2(text = "Offers you got:")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                    ) {
                        AppIcon(imageVector = Icons.Default.PlayArrow)
                        BodyTexts.Level2(
                            modifier = Modifier.weight(1f),
                            text = orderData.offer
                        )
                    }

                    AppIcon(imageVector = Icons.Default.Check)
                }
            }
        }

    }
}