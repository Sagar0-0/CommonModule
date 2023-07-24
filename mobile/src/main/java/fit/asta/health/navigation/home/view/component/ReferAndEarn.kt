package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun ReferAndEarn() {

    val interFontFamily = FontFamily(Font(R.font.inter_regular, FontWeight.Medium))

    val interMediumFontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.Medium))

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(132.dp)
        .background(Color.Transparent)) {
        Image(painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = 4.dp),
            contentScale = ContentScale.FillBounds)
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 9.dp, bottom = 9.dp, end = 13.5.dp, start = 6.5.dp)) {
            Box(Modifier.size(width = 130.dp, height = 106.dp)) {
                Image(painter = painterResource(id = R.drawable.refer_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Box {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Refer and Earn",
                        fontSize = 16.sp,
                        fontFamily = interMediumFontFamily,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 24.sp)
                    Text(text = "Send referral link to your friend to earn ₹ 100",
                        fontSize = 12.sp,
                        fontFamily = interFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {},
                        Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .size(width = 53.dp, height = 28.dp),
                        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                        Text(text = "Refer",
                            fontSize = 14.sp,
                            fontFamily = interMediumFontFamily,
                            color = MaterialTheme.colorScheme.onPrimary,
                            letterSpacing = 0.15.sp)
                    }
                }
            }
        }
    }
}