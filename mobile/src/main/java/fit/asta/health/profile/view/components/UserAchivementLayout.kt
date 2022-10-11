package fit.asta.health.profile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.R

/*
* User's Achievement Layout
* */

@Composable
fun UserAchievementLayout() {
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