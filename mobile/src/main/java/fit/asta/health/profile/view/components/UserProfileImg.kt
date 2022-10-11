package fit.asta.health.profile.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.R

// User's Profile Photo with Design Layout

@Composable
fun UserProfileImg() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(start = 7.5.dp, end = 7.5.dp)) {
        Surface(shape = CircleShape,
            modifier = Modifier.size(188.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, Color(0xff33A0FF))) {}
        Box {
            Image(painter = painterResource(id = R.drawable.userphoto),
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(160.dp),
                contentScale = ContentScale.Crop)
            Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier.align(alignment = Alignment.BottomEnd)) {
                Image(painter = painterResource(id = R.drawable.cameraicon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .border(border = BorderStroke(3.dp, color = Color.White))
                        .background(color = Color.Blue))
            }
        }
        Surface(shape = CircleShape,
            modifier = Modifier.size(218.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, Color(0xff70BCFF))) {}
        Surface(shape = CircleShape,
            modifier = Modifier.size(248.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, Color(0xff99CFFF))) {}
        Surface(shape = CircleShape,
            modifier = Modifier.size(278.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, Color(0xffE5F3FF))) {}
    }
}