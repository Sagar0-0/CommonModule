package fit.asta.health.navigation.profile

import androidx.compose.foundation.*
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Preview
@Composable
fun SpiralDesignDetailsPhoto() {

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .background(color = Color.LightGray)) {

        Spacer(modifier = Modifier.height(58.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 7.5.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //User Profile Photo with Spiral Design
                UserProfileImg()

                Spacer(modifier = Modifier.height(10.dp))

                //User's Basic Information
                Box(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        UserDetail1()
                        Spacer(modifier = Modifier.height(24.dp))
                        UserDetail2()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        //User's Achievement

        UserAchievementLayout()

        Spacer(modifier = Modifier.height(30.dp))

        // User's Profile Details
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            ProfileDetails(imageID = R.drawable.ssidchart,
                profileType = "Level",
                verticalPadding = 13,
                horizontalPadding = 13.0)
            ProfileDetails(imageID = R.drawable.description,
                profileType = "Plan",
                verticalPadding = 13,
                horizontalPadding = 17.0)
            ProfileDetails(imageID = R.drawable.sportsscore,
                profileType = "Goal",
                verticalPadding = 13,
                horizontalPadding = 16.0)
            ProfileDetails(imageID = R.drawable.healthandsafety,
                profileType = "Health",
                verticalPadding = 13,
                horizontalPadding = 8.88)
        }

        Spacer(modifier = Modifier.height(30.dp))

    }
}


@Composable
private fun UserDetail2() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "Sheetal, A 1308, Gopalan Lakefront, Veerasandra Main road," + " Electronic City, Bengaluru. ",
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color(0x99000000),
            lineHeight = 22.4.sp)
    }
}


@Composable
private fun UserDetail1() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
}

// User's Profile Photo with Design Layout
@Composable
private fun UserProfileImg() {
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
}

//Edit Icon Layout
@Composable
fun EditIcon() {
    Box {
        Image(painter = painterResource(id = R.drawable.edit),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(24.dp))
    }
}

//User's Profile Details
@Composable
fun ProfileDetails(
    imageID: Int,
    profileType: String,
    horizontalPadding: Double,
    verticalPadding: Int,
) {
    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.width(67.75.dp)) {
        Column(modifier = Modifier.padding(horizontal = horizontalPadding.dp,
            vertical = verticalPadding.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = imageID),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = profileType,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                color = Color.Black)
        }
    }
}


//Bottom Update Button
@Composable
fun UpdateButton() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RectangleShape),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(Color(0xff0088FF))) {
        Text(text = "UPDATE",
            fontFamily = FontFamily.Default,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            lineHeight = 16.sp,
            letterSpacing = 1.25.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = 17.dp))
    }
}

//User's Achievements Layout
@Composable
private fun UserAchievementLayout() {
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(Modifier
            .fillMaxWidth()
            .weight(1f)) {
            UserAchievementCard(imageID = R.drawable.leaderboard,
                userScore = "24/512",
                cardType = "LeaderBoard")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(Modifier
            .fillMaxWidth()
            .weight(1f)) {
            UserAchievementCard(imageID = R.drawable.badgecompleted,
                userScore = "12",
                cardType = "Badges Earned")
        }
    }
}


//User's Achievement Card
@Composable
private fun UserAchievementCard(
    imageID: Int,
    userScore: String,
    cardType: String,
) {
    androidx.compose.material.Card(modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
            Image(painter = painterResource(id = imageID),
                contentDescription = null,
                modifier = Modifier.size(42.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = userScore, fontSize = 20.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(2.dp))

                Text(text = cardType, fontSize = 12.sp, color = Color(0xff8694A9))
            }
        }
    }
}