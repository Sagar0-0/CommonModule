package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserTst
import fit.asta.health.testimonials.view.theme.cardElevation
import fit.asta.health.testimonials.view.theme.imageHeight
import fit.asta.health.ui.spacing
import fit.asta.health.utils.getImageUrl

@Composable
fun TestimonialImageCard(testimonial: Testimonial) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium),
        shape = MaterialTheme.shapes.medium,
        elevation = cardElevation.small,
        backgroundColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(Modifier.fillMaxWidth()) {

            BeforeAndCardLayout(testimonial)

            UserTst(spacing, testimonial)

            Spacer(modifier = Modifier.height(spacing.medium))

        }
    }

}

@Composable
fun BeforeAndCardLayout(testimonial: Testimonial) {

    val tstImageMedia = testimonial.media

    Row(
        Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {

        if (tstImageMedia.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(testimonial.media.size),
                modifier = Modifier.height(imageHeight.large),
                userScrollEnabled = false,
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {

                tstImageMedia.forEach {
                    item {
                        AsyncImage(
                            model = getImageUrl(it.url),
                            contentDescription = null,
                            modifier = Modifier
                                .height(imageHeight.large)
                                .clip(MaterialTheme.shapes.large),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

            }
        } else {
            Text(text = "NO MEDIA FILE PRESENT", modifier = Modifier.align(CenterVertically))
        }


    }

    Spacer(modifier = Modifier.height(spacing.medium))
}