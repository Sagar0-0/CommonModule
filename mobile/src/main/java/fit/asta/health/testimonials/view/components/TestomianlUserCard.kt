package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.utils.getImageUrl

@Composable
fun UserCard(user: String, userOrg: String, userRole: String, url: String) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Box {
                AsyncImage(model = getImageUrl(url),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(72.dp),
                    contentScale = ContentScale.Crop)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = user, fontSize = 16.sp, color = Color.Black)

                Text(text = "$userRole, $userOrg", fontSize = 12.sp, color = Color(0xff8694A9))
            }
        }
    }

}