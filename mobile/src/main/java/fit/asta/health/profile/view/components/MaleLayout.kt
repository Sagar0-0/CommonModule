package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.common.ui.theme.cardElevation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.Physique

@Composable
fun MaleLayout(
    m: Physique,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(spacing.small),
            elevation = CardDefaults.cardElevation(cardElevation.smallExtraMedium)
        ) {
            GenderOptionsLayout(
                cardImg = R.drawable.age, cardType = "AGE", cardValue = m.age.toString()
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(spacing.small),
            elevation = CardDefaults.cardElevation(cardElevation.smallExtraMedium)
        ) {
            GenderOptionsLayout(
                cardImg = R.drawable.gender, cardType = "GENDER", cardValue = when (m.gender) {
                    1 -> "Male"
                    2 -> "Female"
                    else -> {
                        "Others"
                    }
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(spacing.medium))
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(spacing.small),
            elevation = CardDefaults.cardElevation(cardElevation.smallExtraMedium)
        ) {
            GenderOptionsLayout(
                cardImg = R.drawable.height,
                cardType = "HEIGHT",
                cardValue = "${m.height.toInt()} Cm"
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(spacing.small),
            elevation = CardDefaults.cardElevation(cardElevation.smallExtraMedium)
        ) {
            GenderOptionsLayout(
                cardImg = R.drawable.weight,
                cardType = "WEIGHT",
                cardValue = "${m.weight.toInt()} Kg"
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth(0.53f)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(spacing.small),
            elevation = CardDefaults.cardElevation(cardElevation.smallExtraMedium)
        ) {
            GenderOptionsLayout(
                cardImg = R.drawable.bmi, cardType = "BMI", cardValue = m.bmi.toInt().toString()
            )
        }
        Spacer(modifier = Modifier.width(spacing.medium))
    }
}