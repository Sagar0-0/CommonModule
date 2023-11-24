package fit.asta.health.feature.orders.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
import fit.asta.health.resources.strings.R as StringR

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    orders: List<OrderData>,
    onOrderClick: (OrderData) -> Unit
) {
    if (orders.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder_tag),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            TitleTexts.Level2(
                textAlign = TextAlign.Center,
                text = stringResource(id = StringR.string.no_order_history)
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(orders) { order ->
                OrderItem(order) {
                    onOrderClick(order)
                }
            }
        }
    }
}

@Composable
private fun OrderItem(order: OrderData, onClick: () -> Unit) {
    AppCard(
        modifier = Modifier
            .padding(AppTheme.spacing.level2),
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                TitleTexts.Level2(text = order.title)
                Spacer(modifier = Modifier.padding(AppTheme.spacing.level1))
                TitleTexts.Level2(text = order.cDate.toString())
                Spacer(modifier = Modifier.padding(AppTheme.spacing.level1))
                TitleTexts.Level2(text = order.amt.toString())
                Spacer(modifier = Modifier.padding(AppTheme.spacing.level1))
                TitleTexts.Level2(text = order.status)
            }

            AppNetworkImage(
                model = getImgUrl(order.imgUrl)
            )
        }
    }
}
