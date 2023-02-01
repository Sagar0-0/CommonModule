package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserTst
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.ui.spacing


@Composable
fun TestimonialTextCard(testimonial: Testimonial) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .clip(MaterialTheme.shapes.medium), elevation = cardElevation.small
    ) {

        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium)
            ) {
                Text(
                    text = testimonial.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 22.4.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            UserTst(spacing, testimonial)

            Spacer(modifier = Modifier.height(spacing.medium))

        }
    }
}

