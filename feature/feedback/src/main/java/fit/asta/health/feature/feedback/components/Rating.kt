package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.molecular.other.AppRatingBar

@Composable
fun Rating(updatedRating: (Int) -> Unit) {
    val rating = remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AppRatingBar(rating = rating.intValue.toFloat()) {
            rating.intValue = it.toInt()
            updatedRating(rating.intValue)
        }
    }
}