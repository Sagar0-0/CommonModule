package fit.asta.health.feature.spotify.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.background.AppScreen
import fit.asta.health.designsystem.molecular.texts.BodyTexts

@Composable
fun AstaMusicScreen(
    modifier: Modifier = Modifier
) {
    AppScreen {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BodyTexts.Level1(text = "Asta Music Screen")
        }
    }
}