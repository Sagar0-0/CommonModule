package fit.asta.health.testimonials

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import fit.asta.health.common.ui.components.generic.AppDefaultIcon
import fit.asta.health.common.ui.components.generic.AppFAB
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
        AppFAB(onClick = onNavigateUp, content = {
            AppDefaultIcon(
                imageVector = Icons.Filled.Edit, contentDescription = "Create Testimonial"
            )
        })
    }, topBar = {
        AppTopBar(
            title = "Testimonials", onBack = onNavigateBack
        )
    })
}