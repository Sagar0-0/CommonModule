package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.profile.model.domain.ProfileItem
import fit.asta.health.profile.view.components.SelectionCard
import fit.asta.health.profile.view.components.SleepSchedule
import fit.asta.health.profile.view.components.UpdateButton
import fit.asta.health.profile.view.components.UserLifeStyle


// Health Screen Layout

@Composable
fun HealthLayout(userHealth: ArrayList<ProfileItem>) {

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {

        userHealth.forEach {
            Display(profileItem = it)

        }

        Spacer(modifier = Modifier.height(16.dp))
        UpdateButton()
    }
}

@Composable
fun Display(profileItem: ProfileItem) {

    Spacer(modifier = Modifier.height(16.dp))
    when (profileItem) {
        is ProfileItem.ChipCard -> SelectionCard(cardImg = profileItem.icon,
            cardType = profileItem.title,
            cardList = profileItem.value)
        is ProfileItem.PlainCard -> UserLifeStyle(cardImg = R.drawable.indoorwork,
            cardType = profileItem.title,
            cardValue = profileItem.value)
        is ProfileItem.SessionCard -> SleepSchedule(cardTitle = profileItem.title,
            bedTime = profileItem.startTime,
            wakeUpTime = profileItem.startTime)
    }
}