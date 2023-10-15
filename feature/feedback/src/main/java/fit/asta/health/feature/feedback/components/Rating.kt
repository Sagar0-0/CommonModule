package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import fit.asta.health.designsystem.AppTheme

@Composable
fun Rating(updatedRating: (Int) -> Unit) {
    val rating = remember { mutableIntStateOf(0) }

//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.Center
//    ) {
//        AppRatingBar(rating = rating.intValue.toFloat()) {
//            rating.intValue = it.toInt()
//            updatedRating(rating.intValue)
//        }
//    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        RatingBar(
            value = rating.intValue.toFloat(),
            onValueChange = {
                rating.intValue = it.toInt()
                updatedRating(rating.intValue)
            },
            onRatingChanged = {},
            config = RatingBarConfig().size(AppTheme.boxSize.level3)
                .activeColor(AppTheme.colors.tertiary)
                .inactiveColor(AppTheme.colors.onBackground.copy(AppTheme.alphaValues.level3))
                .padding(AppTheme.spacing.level1)
        )
    }
}