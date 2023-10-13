package fit.asta.health.designsystem.molecular

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppToggleButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.strings.R

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
    ) {

        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)) {
                Icon(
                    painter = painterResource(id), contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
            ) {
                BodyTexts.Level2(
                    text = name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                TitleTexts.Level3(
                    text = type,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun ButtonWithColor(
    modifier: Modifier = Modifier, color: Color, text: String, onClick: () -> Unit
) {

    AppFilledButton(
        modifier = modifier,
        textToShow = text,
        onClick = {
            onClick()
        }, colors = ButtonDefaults.buttonColors(containerColor = color)
    )
}


@Composable
fun BottomSheetDragHandle(
    modifier: Modifier = Modifier,
    height: Dp = 24.dp,
    barWidth: Dp = 32.dp,
    barHeight: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
    onClick: () -> Unit = {}
) {
    Spacer(modifier = modifier
        .clickable { onClick() }
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

@Composable
fun DNDCard(modifier: Modifier, mCheckedState: Boolean, onCheckClicked: (Boolean) -> Unit) {
    AppCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HeadingTexts.Level3(text = stringResource(R.string.dnd_mode))
            AppToggleButton(
                checked = mCheckedState,
                onCheckedChange = onCheckClicked,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedTrackColor = MaterialTheme.colorScheme.background,
                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}
