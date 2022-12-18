package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Preview
@Composable
fun PhotoUploadBottomSheetLayout() {
    Column(Modifier
        .fillMaxWidth()
        .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DividerLine()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.photoalbum),
                    contentDescription = null, Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Photo Gallery", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.cameraalt),
                    contentDescription = null, Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Take Photo", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        DoneButton()
    }
}

@Composable
fun DividerLine() {
    Divider(color = Color(0xff0088FF),
        modifier = Modifier
            .size(width = 80.dp, height = 8.dp)
            .clip(shape = RoundedCornerShape(4.dp)))
}