package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.profile.model.domain.Physique
import java.util.*

@Composable
fun FemaleLayout(
    m: Physique,
    checkedState: MutableState<Boolean>,
    onAge: () -> Unit,
    onGender: () -> Unit,
    onHeight: () -> Unit,
    onWeight: () -> Unit,
    onBMI: () -> Unit,
    onPregnancyWeek: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.age,
                cardType = "AGE",
                cardValue = m.age.toString(),
                checkedState,
                onAge)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.gender,
                cardType = "GENDER",
                cardValue = m.gender.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                checkedState = checkedState,
                onGender)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.height,
                cardType = "HEIGHT",
                cardValue = "${m.height}Cm",
                checkedState = checkedState,
                onHeight)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.weight,
                cardType = "WEIGHT",
                cardValue = "${m.weight}Kg",
                checkedState = checkedState,
                onWeight)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.bmi,
                cardType = "BMI",
                cardValue = "${m.bmi}",
                checkedState = checkedState,
                onBMI)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.pregnant,
                cardType = "PREGNANCY",
                cardValue = "${m.pregnancyWeek}Week",
                checkedState = checkedState,
                onPregnancyWeek)
        }
    }
}
