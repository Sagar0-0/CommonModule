@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import fit.asta.health.common.ui.theme.cardElevation


/** [AppClickableCard] is default clickable card for the app.This Card handles click events,
 * calling its [onClick] lambda.
 * @param onClick called when this card is clicked
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container
 * @param content components inside the card
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppClickableCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        onClick = onClick,
        content = content,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(),
        shape = shape,
    )
}


/** [AppDefCard] is default card for the app.Cards contain content and actions that relate
 * information about a subject.It do not handle any click events.
 * @param modifier the [Modifier] to be applied to this card
 * @param shape defines the shape of this card's container
 * @param content components inside the card
 */

@Composable
fun AppDefCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        content = content,
        shape = shape,
        colors = colors,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.smallMedium),
    )
}