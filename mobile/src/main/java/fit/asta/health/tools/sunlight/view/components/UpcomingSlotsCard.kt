package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.tools.sunlight.model.network.response.ResponseData

@Composable
fun UpcomingSlotsCard(apiState: ResponseData.SunlightToolData) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x66959393))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                Row {
                    Box {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sunny),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xffFED85B)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Sunny\n24 C",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 19.6.sp,
                        color = Color.White)
                }

                IconButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .size(24.dp),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0x66000001))) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(painter = painterResource(id = com.google.android.material.R.drawable.ic_clock_black_24dp),
                            contentDescription = null,
                            tint = Color(0xff0088FF),
                            modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Today",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.6.sp,
                )
                Text(
                    text = "11:00 am",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.6.sp,
                )
            }

        }
    }
}