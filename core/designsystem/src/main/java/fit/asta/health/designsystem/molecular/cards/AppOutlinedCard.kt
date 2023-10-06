package fit.asta.health.designsystem.molecular.cards

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            AppOutlinedCard {
                CaptionTexts.Level1(text = "Card Testing")
            }
        }
    }
}

/**
 * [AppOutlinedCard] is default clickable Outlined card for the app.This Card handles click events,
 * calling its [onClick] lambda.
 *
 * @param modifier the [Modifier] to be applied to this card
 * @param enabled This determines if the Card is enabled or not
 * @param colors [CardColors] that will be used to resolve the color(s) used for this card.
 * @param elevation [CardElevation] used to resolve the elevation for this card.
 * @param border This is the border of the card which would be set accordingly
 * @param onClick called when this card is clicked
 * @param content components inside the card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedCard(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CardColors = CardDefaults.outlinedCardColors(),
    elevation: CardElevation = CardDefaults.outlinedCardElevation(),
    border: BorderStroke = CardDefaults.outlinedCardBorder(enabled),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    if (onClick != null)
        OutlinedCard(
            modifier = modifier,
            onClick = onClick,
            enabled = enabled,
            shape = AppTheme.shape.level3,
            colors = colors,
            elevation = elevation,
            border = border,
            content = content
        )
    else
        OutlinedCard(
            modifier = modifier,
            shape = AppTheme.shape.level3,
            colors = colors,
            elevation = elevation,
            border = border,
            content = content
        )
}