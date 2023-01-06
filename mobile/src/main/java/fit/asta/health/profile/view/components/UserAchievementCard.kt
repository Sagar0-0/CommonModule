package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//User's Achievement Card
@Composable
fun UserAchievementCard(
    imageID: Int,
    userScore: String,
    cardType: String,
) {
    androidx.compose.material.Card(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
            Image(painter = painterResource(id = imageID),
                contentDescription = null,
                modifier = Modifier.size(42.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = userScore, fontSize = 20.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = cardType, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}