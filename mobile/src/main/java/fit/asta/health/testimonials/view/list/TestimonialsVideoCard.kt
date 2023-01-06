package fit.asta.health.testimonials.view.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fit.asta.health.R
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.view.components.UserCard
import fit.asta.health.utils.getImageUrl


@Composable
fun TestimonialsVideoCard(testimonial: Testimonial) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp)),
        elevation = 10.dp
    ) {
        Column(Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.height(16.dp))

            PlayVideoLayout(testimonial)

            UserCard(
                user = testimonial.user.name,
                userOrg = testimonial.user.org,
                userRole = testimonial.user.role,
                url = testimonial.user.url
            )
        }
    }
}

@Composable
fun PlayVideoLayout(testimonial: Testimonial) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 5.dp, color = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                val media = testimonial.media?.get(0)
                if (media != null) {
                    AsyncImage(
                        model = getImageUrl(media.url),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    PlayButton()
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun PlayButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .clip(CircleShape)
            .size(42.dp)
            .background(color = Color.White)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.asana_play_img),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}