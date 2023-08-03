package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.theme.spacing


/**The [AppVerticalGrid] composable is a custom composable function that provides
 *  a vertical grid layout for displaying items in a grid format.
 * @param count The number of columns in the grid layout.
 * @param modifier the modifier to apply to this layout
 * @param verticalArrangement The vertical arrangement of the layout's children
 * @param horizontalArrangement The horizontal arrangement of the layout's children
 * */

@Composable
fun AppVerticalGrid(
    count: Int,
    modifier: Modifier = Modifier, content: LazyGridScope.() -> Unit,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = count),
        content = content,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(spacing.medium),
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement
    )
}