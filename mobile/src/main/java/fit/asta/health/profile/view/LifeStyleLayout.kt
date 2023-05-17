@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.LifeStyle
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.ProfileChipCard
import fit.asta.health.profile.view.components.ProfileSessionCard
import fit.asta.health.profile.view.components.ProfileSingleSelectionCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


// Health Screen Layout

@Composable
fun LifeStyleLayout(
    lifeStyle: LifeStyle,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        ProfileSessionCard(
            //icon = UserPropertyType.SleepSchedule.icon,
            title = UserPropertyType.SleepSchedule.title,
            session = lifeStyle.sleep,
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileSessionCard(
            //icon = UserPropertyType.WorkSchedule.icon,
            title = UserPropertyType.WorkSchedule.title,
            session = lifeStyle.workingTime,
        )

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.physicalActivity?.let {
            ProfileSingleSelectionCard(
                icon = UserPropertyType.PhysActive.icon,
                title = UserPropertyType.PhysActive.title,
                value = when (it) {
                    1 -> "Less"
                    2 -> "Moderate"
                    3 -> "Very"
                    else -> {
                        null
                    }
                }.toString(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.workingEnv?.let {
            ProfileSingleSelectionCard(
                icon = UserPropertyType.WorkingEnv.icon,
                title = UserPropertyType.WorkingEnv.title,
                value = when (it) {
                    1 -> "Standing"
                    2 -> "Sitting"
                    else -> {
                        null
                    }
                }.toString(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.workStyle?.let {
            ProfileSingleSelectionCard(
                icon = UserPropertyType.WorkStyle.icon,
                title = UserPropertyType.WorkStyle.title,
                value = when (it) {
                    1 -> "Indoor"
                    2 -> "Outdoor"
                    else -> {
                        null
                    }
                }.toString(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.workingHours?.let {
            ProfileSingleSelectionCard(
                icon = UserPropertyType.WorkingHours.icon,
                title = UserPropertyType.WorkingHours.title,
                value = when (it) {
                    1 -> "Morning"
                    2 -> "Afternoon"
                    3 -> "Evening"
                    else -> {
                        null
                    }
                }.toString(),
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.curActivities?.let {
            ProfileChipCard(
                icon = UserPropertyType.CurActivities.icon,
                title = UserPropertyType.CurActivities.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.prefActivities?.let {
            ProfileChipCard(
                icon = UserPropertyType.PrefActivities.icon,
                title = UserPropertyType.PrefActivities.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.lifeStyleTargets?.let {
            ProfileChipCard(
                icon = UserPropertyType.LifeStyleTargets.icon,
                title = UserPropertyType.LifeStyleTargets.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

}