package fit.asta.health.feature.testimonialsx.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
import fit.asta.health.feature.testimonialsx.navigation.TestimonialNavRoutesX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestimonialHomeScreenControl(
    navigate: (String) -> Unit,
    onBack: () -> Unit
) {

    // Scroll Behaviour variable used to get the scroll height and the top app bar visibility states
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Scaffold which serves as a Base Parent UI for this Screen
    AppScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

            // TopAppBar
            AppTopBar(
                title = "Testimonials",
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {

            // Floating Action Button to navigate to the Create Testimonial Screen
            AppFloatingActionButton(
                imageVector = Icons.Filled.Edit,
                onClick = { navigate(TestimonialNavRoutesX.Create.route) }
            )
        }
    ) { paddingValues ->


        // TODO :-
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}