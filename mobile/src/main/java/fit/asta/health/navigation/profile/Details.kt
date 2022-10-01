package fit.asta.health.navigation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R


@Preview(showBackground = true)
@Composable
fun SpiralDesignDetailsPhoto() {

    Column(modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        //Spiral Photo
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.padding(start = 7.5.dp, end = 7.5.dp)) {
            Surface(shape = CircleShape,
                modifier = Modifier.size(188.dp),
                color = Color.Transparent,
                border = BorderStroke(1.dp, Color(0xff33A0FF))) {}
            Box {
                Image(painter = painterResource(id = R.drawable.userphoto),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(160.dp),
                    contentScale = ContentScale.Crop)
                Row(horizontalArrangement = Arrangement.End,
                    modifier = Modifier.align(alignment = Alignment.BottomEnd)) {
                    Image(painter = painterResource(id = R.drawable.cameraicon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape)
                            .border(border = BorderStroke(3.dp, color = Color.White))
                            .background(color = Color.Blue))
                }
            }
            Surface(shape = CircleShape,
                modifier = Modifier.size(218.dp),
                color = Color.Transparent,
                border = BorderStroke(1.dp, Color(0xff70BCFF))) {}
            Surface(shape = CircleShape,
                modifier = Modifier.size(248.dp),
                color = Color.Transparent,
                border = BorderStroke(1.dp, Color(0xff99CFFF))) {}
            Surface(shape = CircleShape,
                modifier = Modifier.size(278.dp),
                color = Color.Transparent,
                border = BorderStroke(1.dp, Color(0xffE5F3FF))) {}
        }

        Spacer(modifier = Modifier.height(30.dp))

        // User Details
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.Center) {
            Box(Modifier.width(305.dp), contentAlignment = Alignment.Center) {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Aastha Puri",
                        fontFamily = FontFamily.Default,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black)
                    Text(text = "+91 9987654321",
                        fontFamily = FontFamily.Default,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0x99000000))
                    Text(text = "aasthapuri@gmail.com",
                        fontFamily = FontFamily.Default,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = Color(0x99000000))
                }
            }
            EditIcon()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
            Box(Modifier
                .width(305.dp)
                .padding(end = 16.dp), contentAlignment = Alignment.Center) {
                Text(text = "Sheetal, A 1308, Gopalan Lakefront, Veerasandra Main road," +
                        " Electronic City, Bengaluru. ",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0x99000000),
                    lineHeight = 22.4.sp)
            }
            EditIcon()
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            DetailsCard(scoreBoard = "24/346",
                cardType = "LeaderBoard",
                imageID = R.drawable.leaderboard)
            DetailsCard(scoreBoard = "12",
                cardType = "Badges Earned",
                imageID = R.drawable.badgecompleted)
        }

        Spacer(modifier = Modifier.height(30.dp))

    }
}

@Composable
fun EditIcon() {
    Box {
        Image(painter = painterResource(id = R.drawable.edit),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(24.dp),
            alignment = Alignment.TopEnd)
    }
}

@Composable
fun DetailsCard(
    scoreBoard: String,
    cardType: String,
    imageID: Int,
) {
    Card(elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(Color(0xffffffff)),
        shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier
            .padding(16.dp)) {
            Image(painter = painterResource(id = imageID), contentDescription = null,
                modifier = Modifier.size(42.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = scoreBoard,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black)
                
                Spacer(modifier = Modifier.height(2.dp))

                Text(text = cardType,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff8694A9))
            }
        }
    }
}