package fit.asta.health.feedback.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

@Preview
@Composable
fun Rating() {
    Card(Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xffF4F6F8)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(0.dp)) {
        Row(Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            var rating by remember { mutableStateOf(0f) }

            RatingBar(value = rating,
                onValueChange = { rating = it },
                onRatingChanged = {},
                config = RatingBarConfig().size(40.dp).activeColor(Color(0xffFFC700))
                    .inactiveColor(Color(0xffDFE6ED)).padding(14.dp))
        }
    }
}