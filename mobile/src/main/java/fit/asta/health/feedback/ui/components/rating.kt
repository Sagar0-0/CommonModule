package fit.asta.health.feedback.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import fit.asta.health.common.ui.theme.spacing

@Preview
@Composable
fun rating(): MutableState<Int> {
    val rating = remember { mutableIntStateOf(0) }
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(spacing.small),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RatingBar(
                value = rating.intValue.toFloat(),
                onValueChange = { rating.intValue = it.toInt() },
                onRatingChanged = {},
                config = RatingBarConfig().size(40.dp).activeColor(Color(0xffFFC700))
                    .inactiveColor(MaterialTheme.colorScheme.onBackground.copy(0.25f))
                    .padding(spacing.small)
            )
        }
    }
    return rating
}