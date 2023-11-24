package fit.asta.health.feature.orders.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(orderData: OrderData, onUiEvent: (OrderDetailUiEvent) -> Unit) {
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Order Detail",
                onBack = {
                    onUiEvent(OrderDetailUiEvent.Back)
                }
            )
        }
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TitleTexts.Level2(text = orderData.title)
                    AppNetworkImage(
                        model = getImgUrl(orderData.imgUrl)
                    )
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
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

            Card(modifier = Modifier.fillMaxWidth()) {
                TitleTexts.Level2(text = "Status")
                TitleTexts.Level2(text = orderData.status)
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "MRP")
                    TitleTexts.Level2(text = orderData.mrp)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Discount")
                    TitleTexts.Level2(text = orderData.discount)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Taxes")
                    TitleTexts.Level2(text = orderData.taxes)
                }

                Divider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleTexts.Level2(text = "Total Payable Amount")
                    TitleTexts.Level2(text = orderData.totalAmount)
                }

            }

            Card(modifier = Modifier.fillMaxWidth()) {
                TitleTexts.Level2(text = "Offers you get")
                orderData.offersApplied.forEach { offer ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AppIcon(imageVector = Icons.Default.ArrowForward)
                        TitleTexts.Level2(modifier = Modifier.weight(1f), text = offer)
                        AppIcon(imageVector = Icons.Default.Check)
                    }
                }
            }

        }
    }
}