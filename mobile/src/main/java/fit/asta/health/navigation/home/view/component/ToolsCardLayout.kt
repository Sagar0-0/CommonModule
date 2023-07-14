package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.getImageUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsCardLayout(
    cardTitle: String,
    type: String,
    imgUrl: String,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onClick: (type: String) -> Unit,
) {

    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        onClick = {
            onClick(type)
        }) {

        Column(modifier = Modifier.background(Color.Transparent)) {

            Box {

                AsyncImage(
                    model = getImageUrl(url = imgUrl),
                    contentDescription = null,
                    modifier = imageModifier
                        .aspectRatio(ratio = 1f)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = spacing.small, bottomEnd = spacing.small
                            )
                        ),
                    contentScale = ContentScale.Crop
                )

                Box(contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(start = 6.dp, top = 6.dp)
                        .clickable {

                        }) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.DarkGray
                    )
                }
            }

            Text(
                text = cardTitle,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
                color = Color(0xDE000000),
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

