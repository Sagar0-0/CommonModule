@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.components.generic

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import fit.asta.health.designsystemx.AstaThemeX


/** [AppCard] is default clickable card for the app.This Card handles click events,
 * calling its [onClick] lambda.
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container
 * @param colors [CardColors] that will be used to resolve the color(s) used for this card.
 * @param elevation [CardElevation] used to resolve the elevation for this card.
 * @param onClick called when this card is clicked
 * @param content components inside the card
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = AstaThemeX.appElevation.smallMedium),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        onClick = onClick,
        content = content,
        modifier = modifier,
        colors = colors,
        elevation = elevation,
        shape = shape,
    )
}


/** [AppCard] is default card for the app.Cards contain content and actions that relate
 * information about a subject.It do not handle any click events.
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container
 * @param colors [CardColors] that will be used to resolve the color(s) used for this card.
 * @param elevation [CardElevation] used to resolve the elevation for this card.
 * @param content components inside the card
 */

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = AstaThemeX.appElevation.smallMedium),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        content = content,
        shape = shape,
        colors = colors,
        elevation = elevation,
    )
}