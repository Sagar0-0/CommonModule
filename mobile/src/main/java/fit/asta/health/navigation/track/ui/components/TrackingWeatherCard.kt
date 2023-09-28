package fit.asta.health.navigation.track.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme

// Preview Composable Function
@Preview(
    "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        Surface {
            TrackingWeatherCard(
                weatherType = "Sunny",
                temperature = "22",
                location = "Bengaluru",
                image = R.drawable.image_sun
            )
        }
    }
}

@Composable
fun TrackingWeatherCard(
    modifier: Modifier = Modifier,
    weatherType: String,
    temperature: String,
    location: String,
    image: Int
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.extraSmall)
        ) {

            // Weather type and the temperature
            Text(
                text = "$weatherType - $temperature °C",

                // Text Features
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onSurface,
            )


            // Location
            Text(
                text = location,

                // Text Features
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f),
            )

        }

        // Image of the weather type
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.size(90.dp),
            alignment = Alignment.CenterEnd
        )
    }
}