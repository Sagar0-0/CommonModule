@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing


/** [AppClickableCard] is default clickable card for the app. Cards contain contain content and
 * actions that relate information about a subject. This Card handles click events, calling its [onClick] lambda.
 * @param onClick called when this card is clicked
 * @param modifier the [Modifier] to be applied to this card
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppClickableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        onClick = onClick,
        content = content,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(spacing.small),
    )
}


/** [AppDefCard] is default card for the app.Cards contain contain content and actions that relate
 * information about a subject. Filled cards provide subtle separation from the background. This has
 * less emphasis than elevated or outlined cards.This Card does not handle input events.
 * @param modifier the [Modifier] to be applied to this card
 */

@Composable
fun AppDefCard(
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Card(
        modifier = modifier,
        content = content,
        shape = RoundedCornerShape(spacing.small),
        colors = colors,
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.smallMedium),
    )
}