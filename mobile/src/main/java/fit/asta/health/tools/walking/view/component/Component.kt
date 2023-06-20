package fit.asta.health.tools.walking.view.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.common.ui.theme.spacing

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    name: String,
    type: String,
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {

        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            Column( verticalArrangement = Arrangement.spacedBy(spacing.small)) {
                Icon(
                    painter = painterResource(id), contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(spacing.small),
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = type,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun ButtonWithColor(
    modifier: Modifier=Modifier, color: Color, text: String, onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = {
            onClick()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = color)
    )

    {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun DistanceCardItem(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes id: Int,
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .width(170.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(id), contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = name,
                    style = MaterialTheme.typography.h6
                )
            }
            Row(
                modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Center
            ) {
                RoundIconButton(imageVector = Icons.Default.Remove, onClick = {

                })
                Text(
                    text = "99.00",
                    modifier = Modifier
                        .padding(horizontal = 9.dp)
                        .align(Alignment.CenterVertically)
                )
                RoundIconButton(imageVector = Icons.Default.Add, onClick = {

                })
            }
        }
    }
}

@Composable
fun BottomSheetDragHandle(
    modifier: Modifier = Modifier,
    height: Dp = 24.dp,
    barWidth: Dp = 32.dp,
    barHeight: Dp = 4.dp,
    color: Color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f),
    onClick: () -> Unit = {}
) {
    Spacer(modifier = modifier
        .clickable { onClick }
        .drawBehind {
            val barWidthPx = barWidth.toPx()
            val barHeightPx = barHeight.toPx()
            val x = size.width / 2 - barWidthPx / 2
            val y = size.height / 2 - barHeightPx / 2
            drawRoundRect(
                color = color,
                topLeft = Offset(x, y),
                size = Size(barWidthPx, barHeightPx),
                cornerRadius = CornerRadius(barHeightPx / 2)
            )
        }
        .fillMaxWidth()
        .height(height))
}

