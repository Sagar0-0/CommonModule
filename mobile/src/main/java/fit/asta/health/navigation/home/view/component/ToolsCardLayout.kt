package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fit.asta.health.R

@Composable
fun ColumnScope.ToolsCardLayout(
    imgUrl: String,
    cardTitle: String,
) {

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.weight(1f)) {
        Box {
            AsyncImage(model = imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth())
            Box(modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)) {
                Image(painter = painterResource(id = R.drawable.schedule),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, end = 8.dp),
                    contentScale = ContentScale.Fit)
            }
        }
        Card(modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()) {
            Text(text = cardTitle,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
        }
    }
}

