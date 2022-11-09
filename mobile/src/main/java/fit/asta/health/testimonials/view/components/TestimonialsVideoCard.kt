package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R

@Preview
@Composable
fun TestimonialsVideoCard() {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clip(RoundedCornerShape(8.dp)),
        elevation = 10.dp) {
        Column(Modifier
            .fillMaxWidth()
        ) {
            Row(Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Surface(shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(width = 5.dp, color = Color(0xffE0F1FF)),
                        modifier = Modifier.fillMaxWidth()) {
                        Image(painter = painterResource(id = R.drawable.weatherimage),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop)
                    }
                    PlayButton()
                }
            }

            ArtistCard2(user = "Kristin Watson", userOrg = " EkoHunt", userRole = "CTO", model = "")
        }
    }
}

@Composable
fun PlayButton() {
    IconButton(onClick = { /*TODO*/ },
        modifier = Modifier
            .clip(CircleShape)
            .size(42.dp)
            .background(color = Color.White)) {
        Icon(painter = painterResource(id = R.drawable.asana_play_img),
            contentDescription = null,
            tint = Color(0xff008CFF),
            modifier = Modifier.size(24.dp))
    }
}