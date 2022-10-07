package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun NameAndMoodHomeScreenHeader() {

    val poppinsFontFamily = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium)
    )

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(59.dp),
        verticalArrangement = Arrangement.SpaceBetween) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(153.dp, 36.dp)) {
                Text(text = "Hello Aastha ",
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily)
            }
            Box {
                Text(text = "\uD83D\uDC4B", fontSize = 24.sp,
                    fontFamily = poppinsFontFamily)
            }
        }
        Box(modifier = Modifier
            .size(147.dp, 15.dp)) {
            Text(text = "What’s your mood today ?",
                fontSize = 12.sp,
                fontFamily = interFontFamily,
                color = Color(0xFFA9A7B1))
        }
    }
}