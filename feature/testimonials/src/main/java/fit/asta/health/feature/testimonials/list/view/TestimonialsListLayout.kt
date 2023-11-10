package fit.asta.health.feature.testimonials.list.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.compose.LazyPagingItems
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestimonialsListLayout(
    testimonials: LazyPagingItems<Testimonial>,
    navigateToCreate: () -> Unit,
    onBack: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    AppScaffold(
        content = {
            TestimonialsList(
                paddingValues = it,
                testimonials = testimonials
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                imageVector = Icons.Filled.Edit,
                onClick = navigateToCreate
            )
        }, topBar = {
            AppTopBar(
                title = "Testimonials",
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    )
}