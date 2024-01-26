package fit.asta.health.feature.orders.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.toDateFormat
import fit.asta.health.data.orders.remote.OrderId
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
import fit.asta.health.resources.strings.R as StringR

@Preview("Orders Light")
@Preview(
    name = "Orders Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun OrderScreenPreview() {
    AppTheme {
        OrdersScreen(
            orders = listOf(
                OrderData(
                    amt = 897.8,
                    cDate = 2248,
                    orderId = "nullam",
                    paymentId = "pericula",
                    type = 6133,
                    title = "Title",
                    imageUrl = "https://duckduckgo.com/?q=movet",
                    status = "pulvinar"
                ),
                OrderData(
                    amt = 89.78,
                    cDate = 2248,
                    orderId = "nullam",
                    paymentId = "pericula",
                    type = 6133,
                    title = "Title",
                    imageUrl = "https://duckduckgo.com/?q=movet",
                    status = "pulvinar"
                )
            )
        ) {

        }
    }
}

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    orders: List<OrderData>,
    onOrderClick: (OrderId) -> Unit
) {
    if (orders.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_tag),
                    contentDescription = ""
                )
                TitleTexts.Level2(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = StringR.string.no_order_history)
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            modifier = modifier
        ) {
            items(orders.reversed()) { order ->
                OrderItem(order) {
                    onOrderClick(order.orderId)
                }
            }
        }
    }
}

@Composable
private fun OrderItem(order: OrderData, onClick: () -> Unit) {
    AppElevatedCard(
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(AppTheme.spacing.level2),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
            ) {
                HeadingTexts.Level1(text = order.title)
                BodyTexts.Level2(text = "Date: " + order.cDate.toDateFormat())
                TitleTexts.Level2(text = order.amt.toString() + "/-")
            }

            Box {
                AppNetworkImage(
                    model = getImgUrl(order.imageUrl),
                    modifier = Modifier
                        .size(AppTheme.imageSize.level11)
                        .aspectRatio(ratio = AppTheme.aspectRatio.square)
                )
                BodyTexts.Level2(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = AppTheme.spacing.level2, end = AppTheme.spacing.level2),
                    text = order.status
                )
            }
        }
    }
}
