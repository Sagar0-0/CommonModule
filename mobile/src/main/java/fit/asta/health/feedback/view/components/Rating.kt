package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import fit.asta.health.common.ui.theme.spacing

@Preview
@Composable
fun Rating() {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xffF4F6F8)),
        shape = RoundedCornerShape(spacing.small),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(spacing.small),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var rating by remember { mutableFloatStateOf(0f) }

            RatingBar(
                value = rating,
                onValueChange = { rating = it },
                onRatingChanged = {},
                config = RatingBarConfig().size(40.dp).activeColor(Color(0xffFFC700))
                    .inactiveColor(Color(0xffDFE6ED)).padding(spacing.small)
            )
        }
    }
}