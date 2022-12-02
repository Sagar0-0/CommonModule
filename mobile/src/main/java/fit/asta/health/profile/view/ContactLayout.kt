package fit.asta.health.profile.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.profile.model.domain.Address
import fit.asta.health.profile.model.domain.Contact

@Composable
fun ContactLayout(mainProfile: Contact, checkedState: MutableState<Boolean>) {

    Column(modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {

        //User Profile Photo with Spiral Design
        UserProfileImg()

        Spacer(modifier = Modifier.height(30.dp))
        // User Details
        UserDetails(mainProfile.name,
            mainProfile.phone,
            mainProfile.email,
            mainProfile.address,
            checkedState)

        Spacer(modifier = Modifier.height(30.dp))

        //User's Achievement
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            DetailsCard(scoreBoard = "24/346",
                cardType = "LeaderBoard",
                imageID = R.drawable.leaderboard)
            DetailsCard(scoreBoard = "12",
                cardType = "Badges Earned",
                imageID = R.drawable.badgecompleted)
        }

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

        //Update Button
        if (checkedState.value) {
            UpdateButton()
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// User's Detail Layout
@Composable
private fun UserDetails(
    name: String,
    phoneNumber: String,
    email: String,
    address: Address,
    checkedState: MutableState<Boolean>,
) {


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.Center) {
        Box(Modifier.width(305.dp), contentAlignment = Alignment.Center) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = name,
                    fontFamily = FontFamily.Default,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black)
                Text(text = "+91 $phoneNumber",
                    fontFamily = FontFamily.Default,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0x99000000))
                Text(text = email,
                    fontFamily = FontFamily.Default,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0x99000000))
            }
        }


        Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            if (checkedState.value) {
                EditIcon()
            }
        }


    }

    Spacer(modifier = Modifier.height(30.dp))

    val adr =
        address.address + " " + address.street + " " + address.city + " " + address.country + " - " + address.pin
    Row(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
        Box(Modifier
            .width(305.dp)
            .padding(end = 16.dp), contentAlignment = Alignment.Center) {
            Text(text = adr,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color(0x99000000),
                lineHeight = 22.4.sp)
        }

        Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            if (checkedState.value) {
                EditIcon()
            }
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

                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = painterResource(id = R.drawable.cameraicon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape)
                            .border(border = BorderStroke(3.dp, color = Color.White))
                            .background(color = Color.Blue),
                        contentScale = ContentScale.Crop)
                }


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
fun EditIcon(condition: (() -> Unit)? = null) {

    IconButton(onClick = { /*TODO*/ }) {
        Icon(imageVector = Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xff0088FF))
    }

}

// User's Achievement Card Layout
@Composable
fun DetailsCard(
    scoreBoard: String,
    cardType: String,
    imageID: Int,
) {
    Card(elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(Color(0xffffffff)),
        shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = imageID),
                contentDescription = null,
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

@Composable
fun UpdateButton() {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
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