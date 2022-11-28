package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoNotDisturb
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.R

@Composable
fun SunlightSlotsCardLayout(modifier: Modifier = Modifier) {

    val superscript = SpanStyle(baselineShift = BaselineShift.Superscript,
        fontSize = 10.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold)

    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {
        Column(modifier = Modifier
            .background(Color.Transparent)
            .padding(8.dp)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color.White)) {
                    androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_clock_black_24dp),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xff0277BD))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp))

                Text(fontSize = 12.sp, text = buildAnnotatedString {
                    append("24")
                    withStyle(superscript) {
                        append("o")
                    }
                    append("C")
                }, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.DoNotDisturb,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp))

                Text(text = "1 UVI",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_clock_black_24dp),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp))

                Text(text = " 11:30am",
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }

        }
    }

}
