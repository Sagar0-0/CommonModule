package fit.asta.health.navigation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun PhysiqueCard(
    cardTitle:String,
    cardTitleValue:String,
) {
    Surface(shape = RectangleShape,
        elevation = 2.dp,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(135.16.dp)) {
            Column(Modifier.padding(top = 8.dp, end = 8.dp, start = 16.dp)) {
                Row(horizontalArrangement = Arrangement.End,
                    modifier = Modifier.align(alignment = Alignment.End)) {
                    Image(painter = painterResource(id = R.drawable.edit),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        contentScale = ContentScale.Crop)
                }
                Spacer(modifier = Modifier.height(11.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.age),
                        contentDescription = null,
                        Modifier
                            .size(40.dp)
                            .align(Alignment.CenterVertically),
                        contentScale = ContentScale.Crop)
                    Spacer(modifier = Modifier.width(17.33.dp))
                    Column(verticalArrangement = Arrangement.SpaceBetween) {
                        Text(text = cardTitle,
                            style = TextStyle(color = Color.Black,
                                fontFamily = FontFamily.Default,
                                fontSize = 10.sp,
                                lineHeight = 16.sp,
                                letterSpacing = 1.5.sp)
                        )
                        Spacer(modifier = Modifier.height(11.dp))
                        Text(text = cardTitleValue,
                            style = TextStyle(color = Color.Black,
                                fontFamily = FontFamily.Default,
                                fontSize = 20.sp,
                                lineHeight = 24.sp,
                                letterSpacing = 0.15.sp)
                        )
                    }
                }
            }
        }
    }
}