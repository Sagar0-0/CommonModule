package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserCard
import fit.asta.health.testimonials.view.theme.imageHeight
import fit.asta.health.ui.elevation
import fit.asta.health.ui.spacing
import fit.asta.health.ui.theme.ts
import fit.asta.health.utils.getImageUrl

@Composable
fun TestimonialImageCard(testimonial: Testimonial) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .clip(MaterialTheme.shapes.large),
        elevation = elevation.high
    ) {

        Column(Modifier.fillMaxWidth()) {
            BeforeAndCardLayout(testimonial)

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(spacing.medium)
            ) {
                Box(modifier = Modifier.padding(horizontal = spacing.medium)) {
                    FontFamily(Font(R.font.inter_light, FontWeight.Light))
                    Column {
                        Box {
                            Text(
                                text = "❝", style = ts.quote
                            )
                        }
                        Text(
                            text = testimonial.testimonial,
                            style = ts.quote,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Text(
                                text = "❞", style = ts.quoteBold
                            )
                        }
                        UserCard(
                            user = testimonial.user.name,
                            userOrg = testimonial.user.org,
                            userRole = testimonial.user.role,
                            url = testimonial.user.url
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BeforeAndCardLayout(testimonial: Testimonial) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.medium)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                testimonial.media.forEach {

                    Box(
                        Modifier.padding(spacing.minSmall),
                        contentAlignment = Alignment.BottomCenter
                    ) {

                        AsyncImage(
                            model = getImageUrl(it.url),
                            contentDescription = null,
                            Modifier
                                .fillMaxWidth(0.5f)
                                .height(imageHeight.large),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = it.title,
                            style = ts.quote,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(spacing.medium)
                        )
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(spacing.medium))
}