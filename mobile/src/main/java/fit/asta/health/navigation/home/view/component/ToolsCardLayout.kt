package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.utils.getImageUrl

@Composable
fun ToolsCardLayout(
    imgUrl: String,
    cardTitle: String,
    modifier: Modifier = Modifier,
) {


    Column(modifier = modifier) {
        Box {
            AsyncImage(model = getImageUrl(url = imgUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth())
            Box(modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = fit.asta.health.R.drawable.schedule),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp, end = 8.dp))
                }
            }
        }
        Card(modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()) {
            Text(text = cardTitle,
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)))
        }
    }

}

@Composable
fun ToolsCardLayoutDemo(
    imgUrl: String,
    cardTitle: String,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
) {

    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {
        Column(modifier = Modifier.background(Color.Transparent)) {

            Box {

                AsyncImage(model = getImageUrl(url = imgUrl),
                    contentDescription = null,
                    modifier = imageModifier
                        .height(107.dp)
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                    contentScale = ContentScale.Crop)

                Box(contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 8.dp, end = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color(0x38000000))) {
                    androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = com.google.android.material.R.drawable.ic_clock_black_24dp),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(6.dp))
                    }
                }
            }

            Text(text = cardTitle,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                color = Color(0xDE000000),
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Normal)

        }
    }

}