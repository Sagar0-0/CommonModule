package fit.asta.health.feature.testimonials.list.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.designsystem.components.generic.AppButtons
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.feature.testimonials.list.vm.TestimonialListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestimonialsListLayout(
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: TestimonialListViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    AppScaffold(content = {
        TestimonialsList(paddingValues = it, viewModel = viewModel)
    }, floatingActionButton = {
        AppButtons.AppFAB(onClick = onNavigateUp, content = {
            AppDefaultIcon(
                imageVector = Icons.Filled.Edit, contentDescription = "Create Testimonial"
            )
        })
    }, topBar = {
        AppTopBar(
            title = "Testimonials", onBack = onNavigateBack, scrollBehavior = scrollBehavior
        )
    }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection))
}