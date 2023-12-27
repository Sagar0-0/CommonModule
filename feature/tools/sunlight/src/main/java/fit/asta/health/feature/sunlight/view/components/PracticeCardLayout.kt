package fit.asta.health.feature.sunlight.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable

fun PracticeCardLayout() {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)) {
            PracticeCard()
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)) {
            PracticeCard()
        }
    }

}