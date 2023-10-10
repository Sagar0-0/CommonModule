package fit.asta.health.navigation.track.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview() {
    AppTheme {
        AppSurface {
            TrackingNoteCard(
                labelIcon = R.drawable.image_note,
                title = "Over hydration",
                secondaryTitle = "You have crossed your daily intake",
                bodyText = "Over hydration can lead to water intoxication. This occurs when " +
                        "the amount of salt and other electrolytes in your body become too diluted."
            )
        }
    }
}

@Composable
fun TrackingNoteCard(
    modifier: Modifier = Modifier,
    labelIcon: Int,
    title: String,
    secondaryTitle: String,
    bodyText: String
) {
    Column {

        Row(
            modifier = modifier
                .padding(start = 12.dp, bottom = 4.dp, end = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = labelIcon),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(4.dp))

            TitleTexts.Level2(
                text = title,
                // Text and Font Properties
                textAlign = TextAlign.Start,
                color = Color.Red,
            )
        }

        TitleTexts.Level2(
            text = secondaryTitle,

            // Modifications
            modifier = Modifier
                .padding(start = 44.dp, bottom = 2.dp),

            // Text and Font Properties
            textAlign = TextAlign.Start,
            color = AppTheme.colors.onSurface,
        )

        TitleTexts.Level2(
            text = bodyText,
            // Modifications
            modifier = Modifier
                .padding(start = 44.dp, bottom = 12.dp, end = 6.dp),

            // Text and Font Properties
            textAlign = TextAlign.Start,
            color = AppTheme.colors.onSurface.copy(alpha = .7f),
        )
    }
}