package fit.asta.health.navigation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Preview(showSystemUi = true)
@Composable
fun ProfileTopBar() {
    Row(Modifier
        .fillMaxWidth()
        .drawWithContent {
            drawContent()
            clipRect { // Not needed if you do not care about painting half stroke outside
                val strokeWidth = Stroke.DefaultMiter
                val y = size.height // - strokeWidth
                // if the whole line should be inside component
                drawLine(brush = SolidColor(Color(0xffE6E6E6)),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Square,
                    start = Offset.Zero.copy(y = y),
                    end = Offset(x = size.width, y = y))
            }
        }, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.back),
                contentDescription = null,
                modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xff000000))
        }
        Image(painter = painterResource(id = R.drawable.view),
            contentDescription = null,
            modifier = Modifier.size(24.dp))
    }
}