package fit.asta.health.designsystem.molecular.cards

import androidx.compose.ui.graphics.Color


import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.CaptionTexts


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            AppCardWithColor {
                CaptionTexts.Level1(text = "Card Testing")
            }
        }
    }
}


/**
 * [AppCard] is default filled clickable card for the app.This Card handles click events,
 * calling its [onClick] lambda.
 *
 * @param modifier the [Modifier] to be applied to this card
 * @param enabled This determines if the Card is enabled or not
 * @param shape This is the shape of the Composable Function
 * @param colors [CardColors] that will be used to resolve the color(s) used for this card.
 * @param elevation [CardElevation] used to resolve the elevation for this card.
 * @param border This is to give the card any custom Border we want
 * @param onClick called when this card is clicked
 * @param content components inside the card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCardWithColor(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shape.level2,
    containerColor : Color = AppTheme.colors.primaryContainer,
    contentColor : Color = AppTheme.colors.onPrimaryContainer,
    disabledContainerColor : Color = AppTheme.colors.primaryContainer.copy(alpha = 0.8f),
    disabledContentColor : Color = AppTheme.colors.onPrimaryContainer.copy(alpha = 0.8f),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (onClick != null)
        Card(
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor
            ),
            elevation = elevation,
            border = border,
            onClick = onClick,
            content = content
        )
    else
        Card(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor
            ),
            elevation = elevation,
            border = border,
            content = content
        )
}