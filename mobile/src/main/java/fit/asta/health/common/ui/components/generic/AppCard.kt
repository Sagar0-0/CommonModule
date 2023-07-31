@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing

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


@Composable
fun AppDefCard(content: @Composable ColumnScope.() -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        content = content,
        shape = RoundedCornerShape(spacing.small),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.smallMedium),
    )
}