package fit.asta.health.navigation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.profile.components.UserAchievementLayout
import fit.asta.health.navigation.profile.components.UserDetail1
import fit.asta.health.navigation.profile.components.UserDetail2
import fit.asta.health.navigation.profile.components.UserProfileImg
import fit.asta.health.navigation.profile.details.components.ProfileDetails

//Details Screen Layout

@Preview(showSystemUi = true)
@Composable
fun DetailsScreenLayout() {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(58.dp))
        Box(modifier = Modifier
            .fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                //User Profile Photo with Spiral Design
                UserProfileImg()

                Spacer(modifier = Modifier.height(10.dp))

                //User's Basic Information
                Box(Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        UserDetail1(userName = "Astha Puri",
                            userMail = "aasthapuri@gmail.com",
                            userNumber = "+91 9987654321")
                        Spacer(modifier = Modifier.height(24.dp))
                        UserDetail2(userAddress = "Sheetal, A 1308, Gopalan Lakefront, Veerasandra Main road," + " Electronic City, Bengaluru. ")
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
            .fillMaxWidth(),
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
    }
}
