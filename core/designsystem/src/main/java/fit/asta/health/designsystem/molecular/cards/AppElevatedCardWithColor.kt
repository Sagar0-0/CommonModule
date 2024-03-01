package fit.asta.health.designsystem.molecular.cards


import android.content.res.Configuration
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            AppElevatedCardWithColor {
                CaptionTexts.Level1(text = "Card Testing")
            }
        }
    }
}


/**
 * [AppElevatedCard] is default clickable Elevated card for the app.This Card handles click events,
 * calling its [onClick] lambda.
 *
 * @param modifier the [Modifier] to be applied to this card
 * @param enabled This determines if the Card is enabled or not
 * @param colors [CardColors] that will be used to resolve the color(s) used for this card.
 * @param shape This function is used to provide a custom shape to the Card
 * @param elevation [CardElevation] used to resolve the elevation for this card.
 * @param onClick called when this card is clicked
 * @param content components inside the card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppElevatedCardWithColor(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor : Color = AppTheme.colors.primaryContainer,
    contentColor : Color = AppTheme.colors.onPrimaryContainer,
    disabledContainerColor : Color = AppTheme.colors.primaryContainer.copy(alpha = 0.8f),
    disabledContentColor : Color = AppTheme.colors.onPrimaryContainer.copy(alpha = 0.8f),
    shape: Shape = AppTheme.shape.level1,
    elevation: CardElevation = CardDefaults.elevatedCardElevation(
        defaultElevation = AppTheme.elevation.level1,
        pressedElevation = AppTheme.elevation.level1,
        focusedElevation = AppTheme.elevation.level1,
        hoveredElevation = AppTheme.elevation.level2,
        draggedElevation = AppTheme.elevation.level4,
        disabledElevation = AppTheme.elevation.level1
    ),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {

    if (onClick != null)
        ElevatedCard(
            modifier = modifier,
            enabled = enabled,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor
            ),
            shape = shape,
            elevation = elevation,
            onClick = onClick,
            content = content
        )
    else
        ElevatedCard(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor
            ),
            elevation = elevation,
            content = content
        )
}