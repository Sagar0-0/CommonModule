package fit.asta.health.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fit.asta.health.common.ui.theme.spacing

@Composable
fun BottomNavigationSection(lastPage: Boolean, onNextClick: () -> Unit, onSkipClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = spacing.medium),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = if (lastPage) Arrangement.End else Arrangement.SpaceBetween
    ) {
        if (lastPage) {
            OnBoardingButton(
                text = "Finish",
                modifier = Modifier
                    .padding(end = spacing.medium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                textColor = MaterialTheme.colorScheme.onPrimary
            ) {
                onSkipClick()
            }
        } else {
            OnBoardingButton(
                text = "Skip",
                modifier = Modifier
                    .padding(start = spacing.medium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            ) {
                onSkipClick()
            }
            OnBoardingButton(
                text = "Next",
                modifier = Modifier
                    .padding(end = spacing.medium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                textColor = MaterialTheme.colorScheme.onPrimary
            ) {
                onNextClick()
            }
        }
    }
}

@Composable
fun OnBoardingButton(
    modifier: Modifier,
    textColor: Color,
    colors: ButtonColors,
    text: String,
    onClick: () -> Unit
) {
    Button(modifier = modifier, colors = colors, onClick = onClick, shape = shapes.extraLarge) {
        Text(text = text, color = textColor)
    }
}