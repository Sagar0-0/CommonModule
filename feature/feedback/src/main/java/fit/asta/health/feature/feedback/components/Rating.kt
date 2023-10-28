package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import fit.asta.health.designsystem.molecular.other.AppRatingBar

@Composable
fun Rating(
    rating: Int,
    updatedRating: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AppRatingBar(
            modifier = Modifier.semantics { contentDescription = "Rating" },
            rating = rating.toFloat()
        ) {
            updatedRating(it.toInt())
        }
    }
}