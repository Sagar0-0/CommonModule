package fit.asta.health.feature.orders.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.toDateFormat
import fit.asta.health.data.orders.remote.model.OrderDetailData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
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
                amt = 85.75,
                cDate = 9353,
                couponDiscount = 853.0,
                offerDiscount = null,
                orderId = "accusata asdfg wer asdfg qwert ertghj dsdfg",
                paymentId = "xxxxxxxxxx",
                paymentMode = "definitionem",
                sts = "Active",
                ttl = "Title",
                type = 5208,
                imageUrl = "https://www.google.com/#q=graeci",
                walletMoney = 30.96,
                walletPoints = 28.93
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
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeadingTexts.Level1(
                    modifier = Modifier.padding(AppTheme.spacing.level2),
                    text = orderData.ttl
                )
                AppNetworkImage(
                    model = getImgUrl(orderData.imageUrl),
                    modifier = Modifier
                        .size(AppTheme.imageSize.level11)
                        .aspectRatio(ratio = AppTheme.aspectRatio.square)
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
                    TitleTexts.Level2(text = "Order Id:")
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                    TitleTexts.Level2(text = orderData.orderId, maxLines = 1)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Date:")
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                    TitleTexts.Level2(text = orderData.cDate.toDateFormat())
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Payment Mode:")
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                    orderData.paymentMode?.let { TitleTexts.Level2(text = it) }
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

                if (orderData.offerDiscount != 0.0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TitleTexts.Level2(text = "Offer")
                        TitleTexts.Level2(text = "-" + orderData.offerDiscount.toString())
                    }
                }

                if (orderData.couponDiscount != 0.0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TitleTexts.Level2(text = "Discount")
                        TitleTexts.Level2(text = "-" + orderData.couponDiscount.toString())
                    }
                }

                if (orderData.walletMoney != 0.0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TitleTexts.Level2(text = "Wallet Money")
                        TitleTexts.Level2(text = "-" + orderData.walletMoney.toString())
                    }
                }

                if (orderData.walletPoints != 0.0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TitleTexts.Level2(text = "Wallet Points")
                        TitleTexts.Level2(text = "-" + orderData.walletPoints.toString())
                    }
                }


                AppDivider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Paid Amount")
                    TitleTexts.Level2(text = orderData.paid.toString())
                }
            }
        }

//        AppElevatedCard {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(AppTheme.spacing.level2),
//                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
//            ) {
//                TitleTexts.Level2(text = "Offers you got:")
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
//                    ) {
//                        AppIcon(imageVector = Icons.Default.PlayArrow)
//                        orderData.offerDiscount?.let {
//                            BodyTexts.Level2(
//                                modifier = Modifier.weight(1f),
//                                text = it.toString()
//                            )
//                        }
//                    }
//
//                    AppIcon(imageVector = Icons.Default.Check)
//                }
//            }
//        }

    }
}