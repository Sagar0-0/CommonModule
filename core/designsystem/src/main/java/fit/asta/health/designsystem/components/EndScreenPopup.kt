package fit.asta.health.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystemx.AstaThemeX

@Composable
fun EndScreenPopup(
    title: String,
    desc: String,
    onContinueClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AstaThemeX.spacingX.medium),
            shape = RoundedCornerShape(AstaThemeX.spacingX.small),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(AstaThemeX.spacingX.extraSmall)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    tint = Color.Green,
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier.size(AstaThemeX.iconSizeX.medium)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))

                Text(
                    text = desc,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )


                Button(
                    onClick = onContinueClick,
                    shape = AstaThemeX.shapeX.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AstaThemeX.spacingX.medium),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Continue",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun EndScreenPreview() {
    EndScreenPopup(title = "Thank you!", desc = "Your feedback has been submitted") {

    }
}