package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

data class SkinExposerData(
    val percentValue: String,
    val cardImg: Int,
)

@Preview
@Composable
fun SkinExposureLayout() {

    val cardList = listOf(
        SkinExposerData(percentValue = "40%", cardImg = R.drawable.girl_avatar_40),
        SkinExposerData(percentValue = "30%", cardImg = R.drawable.boy_avatar_30),
        SkinExposerData(percentValue = "45%", cardImg = R.drawable.boy_avatar_30),
        SkinExposerData(percentValue = "50%", cardImg = R.drawable.boy_avatar_30),
        SkinExposerData(percentValue = "25%", cardImg = R.drawable.boy_avatar_30),
        SkinExposerData(percentValue = "40%", cardImg = R.drawable.boy_avatar_30),
        SkinExposerData(percentValue = "20%", cardImg = R.drawable.boy_avatar_30),
        SkinExposerData(percentValue = "20%", cardImg = R.drawable.boy_avatar_30),
    )

}


@Preview
@Composable
fun SkinExposureCard() {
    Card(modifier = Modifier
        .blur(radius = 5.dp)
        .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x66959393))) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.boy_avatar_30),
                contentDescription = null)
            Text(text = "40%", fontSize = 18.sp, fontWeight = FontWeight.Bold, lineHeight = 25.2.sp)
        }

        Box(contentAlignment = Alignment.TopStart) {
            androidx.compose.material.Icon(imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.Green,
                modifier = Modifier.size(20.dp))
        }
    }
}