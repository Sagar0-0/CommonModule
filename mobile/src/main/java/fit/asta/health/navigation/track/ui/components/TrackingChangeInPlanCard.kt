package fit.asta.health.navigation.track.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            TrackingChangeInPlanCard(
                startLabelIcon = R.drawable.image_sunny,
                endLabelIcon = R.drawable.image_upward_arrow,
                value = "500 mL",
                bodyText = "Due to high rise in temperature extra 500 ml " +
                        " of water is added to the Target. "
            ) {
                // Sunny Text and the temperature in Degree
                Column {

                    // Sunny Text
                    TitleTexts.Level2(
                        text = "Sunny",

                        // Text and Font Properties
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.onSurface,
                    )

                    // Degree Text
                    TitleTexts.Level2(
                        text = "32 °C",
                        // Text and Font Properties
                        textAlign = TextAlign.Start,
                        color = AppTheme.colors.onSurface,
                    )
                }
            }
        }
    }
}

/**
 * This Function makes the Card Which shows the user that his daily intake has been increased due to
 * Sunny Conditions
 */
@Composable
fun TrackingChangeInPlanCard(
    modifier: Modifier = Modifier,
    startLabelIcon: Int,
    endLabelIcon: Int,
    value: String,
    bodyText: String,
    titleComposable: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Sunny Icon
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = startLabelIcon),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Drawing the Header Title Composable
                titleComposable()
            }

            // 500mL Text with the Upward Arrow Icon
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 500mL addition Text
                TitleTexts.Level2(
                    text = value,

                    // Text and Font Properties
                    textAlign = TextAlign.Start,
                    color = AppTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.width(6.dp))

                // Upward Icon Text
                Image(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = endLabelIcon),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Body Text of the function explaining the user what happened and why his intake has increased
        TitleTexts.Level2(
            text = bodyText,

            // Text and Font Properties
            textAlign = TextAlign.Start,
            color = AppTheme.colors.onSurface.copy(alpha = .6f),
        )
    }
}