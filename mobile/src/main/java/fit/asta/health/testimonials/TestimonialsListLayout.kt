package fit.asta.health.testimonials

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.testimonials.view.TestimonialsList
import fit.asta.health.testimonials.viewmodel.list.TestimonialListViewModel


@Composable
fun TestimonialsListLayout(
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: TestimonialListViewModel = hiltViewModel(),
    player: Player,
) {

    AppScaffold(content = {
        TestimonialsList(it, viewModel, player = player)
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = onNavigateUp,
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            modifier = Modifier.size(40.dp),
            contentColor = Color.White
        ) {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit Button",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }, topBar = {
        AppTopBar(
            title = "Testimonials", onBack = onNavigateBack
        )
    })
}