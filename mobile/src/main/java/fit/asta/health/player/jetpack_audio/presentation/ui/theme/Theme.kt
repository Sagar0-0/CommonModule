package fit.asta.health.player.jetpack_audio.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun LOULATheme(
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colorScheme = LolaMusicColors,
            typography = LolaMusicTypography,
            shapes = LolaMusicShapes,
            content = content
        )
    }
}