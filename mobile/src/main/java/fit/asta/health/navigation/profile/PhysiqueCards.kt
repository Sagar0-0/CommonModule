package fit.asta.health.navigation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

var USER_GENDER: String = "Male"

@Preview
@Composable
fun UserBasicHealthDetail() {

    Column(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
        .verticalScroll(rememberScrollState())) {

        if (USER_GENDER == "Female") {
            FemaleLayout()
        } else {
            MaleLayout()
        }

        Spacer(modifier = Modifier.height(16.dp))
        UserBodyType(bodyType = "BODY TYPE",
            bodyImg = R.drawable.bodyfat,
            bodyStatus = "Fat at Belly and Thighs")
        Spacer(modifier = Modifier.height(16.dp))
        UpdateButton()
    }
}

@Composable
fun UserBasicDetailsCardLayout(
    cardImg: Int,
    cardType: String,
    cardValue: String,
) {
    Column {
        Row(modifier = Modifier
            .padding(top = 8.dp, end = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Image(painter = painterResource(id = R.drawable.edit),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.height(11.dp))

        Box(Modifier.padding(start = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = cardImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp))

                Spacer(modifier = Modifier.padding(8.dp))

                Column {
                    Text(text = cardType,
                        fontSize = 10.sp,
                        color = Color(0xDE000000),
                        lineHeight = 16.sp,
                        letterSpacing = 1.5.sp)
                    Spacer(modifier = Modifier.height(11.dp))
                    Text(text = cardValue,
                        fontSize = 20.sp,
                        color = Color(0xDE000000),
                        lineHeight = 24.sp,
                        letterSpacing = 0.15.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(41.dp))

    }
}


@Composable
fun UserBodyType(
    bodyType: String,
    bodyImg: Int,
    bodyStatus: String,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = bodyType,
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xDE000000))
                Image(painter = painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 55.dp)) {
                Image(painter = painterResource(id = bodyImg),
                    contentDescription = null,
                    modifier = Modifier.size(width = 70.dp, height = 109.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End) {
                Text(text = bodyStatus,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.4.sp,
                    color = Color(0xDE000000))
            }
        }
    }
}


@Composable
fun FemaleLayout() {
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
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.bmi, cardType = "BMI", cardValue = "25")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .shadow(elevation = 5.dp),
            shape = RoundedCornerShape(8.dp)) {
            UserBasicDetailsCardLayout(cardImg = R.drawable.pregnant,
                cardType = "PREGNANCY",
                cardValue = "16")
        }
    }
}

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

//@Composable
//fun userCardUnit(unit: String): String {
//    return Text(text = unit,
//        fontSize = 14.sp,
//        letterSpacing = 0.4.sp,
//        lineHeight = 16.sp,
//        color = Color(0xDE000000)).toString()
//}