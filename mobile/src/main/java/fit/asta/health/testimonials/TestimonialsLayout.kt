package fit.asta.health.testimonials

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.testimonials.view.TestimonialsList
import fit.asta.health.testimonials.viewmodel.list.TestimonialListViewModel
import fit.asta.health.ui.theme.cardElevation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestimonialsLayout(
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: TestimonialListViewModel = hiltViewModel(),
) {

    Scaffold(content = {
        TestimonialsList(it, viewModel)
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
        TopAppBar(title = {
            Text(
                text = "Testimonials",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    Icons.Outlined.NavigateBefore,
                    "back",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ), modifier = Modifier.shadow(elevation = cardElevation.medium)
        )
    }, containerColor = MaterialTheme.colorScheme.background)
}