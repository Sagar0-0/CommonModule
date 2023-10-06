package fit.asta.health.feature.feedback.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppCard

@Composable
fun rating(): MutableState<Int> {
    val rating = remember { mutableIntStateOf(0) }
    AppCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppTheme.spacing.level2),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RatingBar(
                value = rating.intValue.toFloat(),
                onValueChange = { rating.intValue = it.toInt() },
                onRatingChanged = {},
                config = RatingBarConfig().size(40.dp).activeColor(Color(0xffFFC700))
                    .inactiveColor(AppTheme.colors.onBackground.copy(0.25f))
                    .padding(AppTheme.spacing.level2)
            )
        }
    }
    return rating
}