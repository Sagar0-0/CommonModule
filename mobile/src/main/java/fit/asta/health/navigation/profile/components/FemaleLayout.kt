package fit.asta.health.navigation.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.profile.USER_GENDER

@Composable
fun FemaleLayout(m: Map<String, Any?>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.age, cardType = "AGE", cardValue = m["age"].toString())
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.gender,
                cardType = "GENDER",
                cardValue = m["gen"].toString())
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
                cardValue = m["ht"].toString())
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.weight,
                cardType = "WEIGHT",
                cardValue = m["wt"].toString())
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.bmi, cardType = "BMI", cardValue = m["bmi"].toString())
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.pregnant,
                cardType = "PREGNANCY",
                cardValue = m["age"].toString())
        }
    }
}