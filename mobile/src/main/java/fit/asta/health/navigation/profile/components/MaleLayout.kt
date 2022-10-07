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
fun MaleLayout() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.age, cardType = "AGE", cardValue = "36")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.gender,
                cardType = "GENDER",
                cardValue = USER_GENDER)
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
                cardValue = "165")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.weight,
                cardType = "WEIGHT",
                cardValue = "65")
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(modifier = Modifier.fillMaxWidth(0.52f)) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.bmi, cardType = "BMI", cardValue = "25")
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}