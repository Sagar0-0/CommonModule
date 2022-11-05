package fit.asta.health.tools.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PracticeExpandedCard(
    cardTitle: String,
    cardImg: Int,
    cardValue: String,
    modifier: Modifier = Modifier,
) {

    Card(modifier = modifier
        .blur(radius = 5.dp)
        .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x66959393))) {
        Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = cardImg),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = cardTitle,
                    fontSize = 16.sp,
                    lineHeight = 25.2.sp,
                    fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = cardValue,
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}