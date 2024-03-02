package fit.asta.health.sunlight.feature.components

//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.hoho.assignmentsun.model.MusicDTO
//import fit.asta.health.sunlight.feature.BlackTransparent
//import fit.asta.health.sunlight.feature.WhiteTransparent
//import fit.asta.health.resources.drawables.R as DrawR
//import fit.asta.health.resources.strings.R as StringR
//
//@Composable
//fun MusicPlayerCard(
//    modifier: Modifier = Modifier,
//    currMusic: MusicDTO
//) {
//    Column(
//        modifier = modifier
//            .background(
//                color = BlackTransparent,
//                shape = RoundedCornerShape(12.dp)
//            )
//            .width(170.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(12.dp) // use medium
//        )
//        {
//            Image(
//                painter = painterResource(id = DrawR.drawable.background_app),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(120.dp, 80.dp)
//                    .clip(RoundedCornerShape(12.dp)),
//                contentScale = ContentScale.FillBounds,
//            )
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = stringResource(id = StringR.string.menu),
//                tint = Color.White,
//                modifier = Modifier
//                    .padding(start = 8.dp)//small
//                    .clickable {
//                        //open menu
//                    }
//            )
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp), // use medium,
//            verticalAlignment = Alignment.CenterVertically
//        )
//        {
//            Column {
//                Text(
//                    text = currMusic.title,
//                    style = MaterialTheme.typography.bodyLarge
//                        .copy(Color.White)
//                )
//                Text(
//                    text = "${currMusic.date} • ${currMusic.duration}",
//                    style = MaterialTheme.typography.bodyLarge
//                        .copy(Color.White)
//                )
//            }
//            Icon(
//                imageVector = Icons.Default.PlayArrow,
//                contentDescription = stringResource(id = StringR.string.play),
//                tint = Color.White,
//                modifier = Modifier
//                    .padding(start = 8.dp)//small
//                    .background(color = WhiteTransparent, shape = CircleShape)
//                    .clickable {
//                        //play music
//                    }
//            )
//        }
//    }
//}
//
//
//@Composable
//fun MusicPlayerCardsPreview() {
//    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = DrawR.drawable.background_app),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.FillBounds
//        )
//    }
//    Row(
//        Modifier
//            .fillMaxWidth()
//            .padding(16.dp), // medium
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        MusicPlayerCard(
//            currMusic = MusicDTO(
//                "Relaxing Sleep",
//                date = "26 Jan",
//                backgroundImage = DrawR.drawable.background_app,
//                duration = "34 min"
//            )
//        )
//        MusicPlayerCard(
//            currMusic = MusicDTO(
//                "Relaxing Sleep",
//                date = "26 Jan",
//                backgroundImage = DrawR.drawable.background_app,
//                duration = "34 min"
//            )
//        )
//    }
//}