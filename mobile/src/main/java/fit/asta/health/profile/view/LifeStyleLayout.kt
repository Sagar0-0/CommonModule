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
import fit.asta.health.profile.view.components.ChipCard
import fit.asta.health.profile.view.components.SessionCard
import fit.asta.health.profile.view.components.SingleSelectionCard
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

        SessionCard(
            //icon = UserPropertyType.SleepSchedule.icon,
            title = UserPropertyType.SleepSchedule.title,
            session = lifeStyle.sleep,
        )

        Spacer(modifier = Modifier.height(16.dp))

        SessionCard(
            //icon = UserPropertyType.WorkSchedule.icon,
            title = UserPropertyType.WorkSchedule.title,
            session = lifeStyle.workingTime,
        )

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.physicalActivity?.let {
            SingleSelectionCard(
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

        SingleSelectionCard(
            icon = UserPropertyType.WorkingEnv.icon,
            title = UserPropertyType.WorkingEnv.title,
            value = lifeStyle.workingEnv.toString(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        SingleSelectionCard(
            icon = UserPropertyType.WorkStyle.icon,
            title = UserPropertyType.WorkStyle.title,
            value = lifeStyle.workStyle.toString(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        SingleSelectionCard(
            icon = UserPropertyType.WorkingHours.icon,
            title = UserPropertyType.WorkingHours.title,
            value = lifeStyle.workingHours.toString(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.curActivities?.let {
            ChipCard(
                icon = UserPropertyType.CurActivities.icon,
                title = UserPropertyType.CurActivities.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.prefActivities?.let {
            ChipCard(
                icon = UserPropertyType.PrefActivities.icon,
                title = UserPropertyType.PrefActivities.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.lifeStyleTargets?.let {
            ChipCard(
                icon = UserPropertyType.LifeStyleTargets.icon,
                title = UserPropertyType.LifeStyleTargets.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

}