package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun WeatherCardImage(
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(151.dp)
        .clip(RoundedCornerShape(10.dp))
    ) {
        Image(painter = painterResource(id = R.drawable.weatherimage),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter))
        TemperatureAndWeather()
        LocationAndDate()
    }
}

@Composable
fun TemperatureAndWeather(
) {

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 14.dp, end = 18.dp, top = 10.dp, bottom = 62.41.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(100.dp, 80.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box {
                    Text(
                        text = "18",
                        fontFamily = interFontFamily,
                        fontSize = 72.sp,
                        color = Color.White,
                        modifier = Modifier.align(alignment = Alignment.TopCenter)
                    )
                }
                Box(Modifier.size(13.5.dp)) {
                    Image(painter = painterResource(id = R.drawable.temperaturedegreeimage),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit)
                }
            }
        }

        Box(modifier = Modifier.size(63.dp, 58.dp)) {
            Image(painter = painterResource(id = R.drawable.rainimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
        }
    }
}

@Composable
fun LocationAndDate(
) {

    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, FontWeight.Normal)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 14.dp, end = 18.dp, top = 104.dp, bottom = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(154.dp, 24.dp)) {
            Image(painter = painterResource(id = R.drawable.location),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit)
            Text(text = "Delhi",
                fontFamily = interFontFamily,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(start = 4.dp), fontSize = 14.sp, color = Color(0xFFFFFFFF))
        }
        Text(text = "Friday,24 October",
            color = Color.White,
            fontFamily = interFontFamily,
            fontSize = 14.sp)
    }
}