package fit.asta.health.navigation.track.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.texts.TitleTexts

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
        AppSurface {
            TrackingYourMoodCard(
                moodImage = R.drawable.image_happy_face,
                moodText = "I feel Happy Today"
            )
        }
    }
}

@Composable
fun TrackingYourMoodCard(
    moodImage: Int,
    moodText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitleTexts.Level2(
                text = "Your Mood",

                // Text Feature
                textAlign = TextAlign.Start,
                color = AppTheme.colors.onSurface.copy(alpha = .7f),
            )
            Image(
                painter = painterResource(id = moodImage),
                contentDescription = null,
                modifier = Modifier
                    .size(58.dp)
                    .padding(top = 8.dp)
            )
        }

        TitleTexts.Level2(
            text = moodText,
            modifier = Modifier
                .weight(1f),
            // Text Feature
            textAlign = TextAlign.Center,
            color = Color.Blue,
        )
    }
}