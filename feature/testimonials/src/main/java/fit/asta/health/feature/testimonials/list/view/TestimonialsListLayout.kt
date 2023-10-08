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
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
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
        AppFloatingActionButton(imageVector = Icons.Filled.Edit, onClick = onNavigateUp)
    }, topBar = {
        AppTopBar(title = "Testimonials", onBack = onNavigateBack, scrollBehavior = scrollBehavior)
    }, modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection))
}