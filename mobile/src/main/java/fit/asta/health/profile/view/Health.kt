package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.ProfileItemType
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.SelectionCard
import fit.asta.health.profile.view.components.SleepSchedule
import fit.asta.health.profile.view.components.UpdateButton
import fit.asta.health.profile.view.components.UserLifeStyle


// Health Screen Layout

@Composable
fun HealthLayout(userHealth: Map<UserPropertyType, ArrayList<HealthProperties>>) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        userHealth.forEach {
            Display(profileItem = it.key, props = it.value)
        }

        Spacer(modifier = Modifier.height(16.dp))
        UpdateButton()
    }
}

@Composable
fun Display(profileItem: UserPropertyType, props: ArrayList<HealthProperties>) {

    Spacer(modifier = Modifier.height(16.dp))
    when (profileItem.type) {
        ProfileItemType.SessionCard -> SleepSchedule(
            cardTitle = profileItem.title,
            bedTime = props[0].from.toString(),
            wakeUpTime = props[0].to.toString()
        )
        ProfileItemType.PlainCard -> UserLifeStyle(
            cardImg = profileItem.icon,
            cardType = profileItem.title,
            cardValue = props[0].name
        )
        ProfileItemType.ChipsCard -> SelectionCard(
            cardImg = profileItem.icon,
            cardType = profileItem.title,
            cardList = props
        )
    }
}