package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserCard
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.ui.spacing
import fit.asta.health.ui.theme.Dark02


@Composable
fun TestimonialTextCard(testimonial: Testimonial) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .clip(MaterialTheme.shapes.medium),
        elevation = cardElevation.small
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

            Box(modifier = Modifier.padding(horizontal = spacing.medium)) {
                FontFamily(Font(R.font.inter_light, FontWeight.Light))

                Column {
                    Box {
                        Text(
                            text = "❝",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = testimonial.testimonial,
                        fontSize = 16.sp,
                        color = Dark02,
                        fontWeight = FontWeight.Thin,
                        lineHeight = 24.sp,
                        letterSpacing = 0.5.sp
                    )
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Text(text = "❞", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                    }

                    UserCard(
                        user = testimonial.user.name,
                        userOrg = testimonial.user.org,
                        userRole = testimonial.user.role,
                        url = testimonial.user.url
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

        }
    }
}

