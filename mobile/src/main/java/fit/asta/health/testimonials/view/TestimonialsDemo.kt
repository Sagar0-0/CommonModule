package fit.asta.health.testimonials.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.view.components.TestimonialsCardLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTestimonialsLayout(onNavigateUp: () -> Unit, testimonial: List<NetTestimonial>) {
    Scaffold(content = {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .background(color = Color(0xffF4F6F8))
        ) {

            testimonial.forEach { testData ->
                TestimonialsCardLayout(cardTitle = testData.title, cardTst = testData.testimonial)
            }

        }
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { /*TODO*/ },
            containerColor = Color(0xff0075FF),
            shape = CircleShape,
            modifier = Modifier.size(40.dp),
            contentColor = Color.White
        ) {
            IconButton(onClick = onNavigateUp) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }
        }
    }, topBar = {
        TopAppBar(title = {
            Text(
                text = "Testimonials",
                color = Color(0xff010101),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }, navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.NavigateBefore, "back", tint = Color(0xff0088FF))
            }
        })
    })
}


