package fit.asta.health.designsystem.components.functional

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@Composable
fun WeatherCardImage(
    temperature: String,
    location: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(151.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.weatherimage),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )
        TemperatureAndWeather(temperature = temperature)
        LocationAndDate(location = location, date = date)
    }
}

@Composable
fun TemperatureAndWeather(temperature: String) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 18.dp, top = 10.dp, bottom = 62.41.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box {
                    AppTexts.HeadlineLarge(
                        text = temperature,
                        color = Color.White,
                        modifier = Modifier.align(alignment = Alignment.TopCenter)
                    )
                }
                Box(Modifier.size(13.5.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.temperaturedegreeimage),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        Box {
            Image(
                painter = painterResource(id = R.drawable.rainimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun LocationAndDate(
    location: String,
    date: String,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 14.dp, end = 18.dp, top = 104.dp, bottom = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Sharp.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colors.onPrimary
        )
        TitleTexts.Level2(
            text = location,
            color = MaterialTheme.colors.onPrimary
        )
        TitleTexts.Level2(text = date, color = MaterialTheme.colors.onPrimary)
    }
}