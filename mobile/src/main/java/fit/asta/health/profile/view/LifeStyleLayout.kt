@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.feedback.view.SubmitButton
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
    editState: MutableState<Boolean>,
    onSleepSchedule: () -> Unit,
    onWorkStyle: () -> Unit,
    onWorkSchedule: () -> Unit,
    onPhysicallyActive: () -> Unit,
    onCurrentActive: () -> Unit,
    onPreferredActivity: () -> Unit,
    onLifeStyleTargets: () -> Unit,
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
            editState = editState,
            onSleepSchedule
        )

        Spacer(modifier = Modifier.height(16.dp))

        SingleSelectionCard(
            icon = UserPropertyType.WorkStyle.icon,
            title = UserPropertyType.WorkStyle.title,
            value = lifeStyle.workStyle.name,
            editState = editState,
            onWorkStyle
        )

        Spacer(modifier = Modifier.height(16.dp))

        SessionCard(
            //icon = UserPropertyType.WorkSchedule.icon,
            title = UserPropertyType.WorkSchedule.title,
            session = lifeStyle.workingTime,
            editState = editState,
            onWorkSchedule
        )

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.physicalActivity?.let {
            SingleSelectionCard(
                icon = UserPropertyType.PhysActive.icon,
                title = UserPropertyType.PhysActive.title,
                value = it.name,
                editState = editState,
                onPhysicallyActive
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.curActivities?.let {
            ChipCard(
                icon = UserPropertyType.CurActivities.icon,
                title = UserPropertyType.CurActivities.title,
                list = it,
                editState = editState,
                onClick = onCurrentActive
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.prefActivities?.let {
            ChipCard(
                icon = UserPropertyType.PrefActivities.icon,
                title = UserPropertyType.PrefActivities.title,
                list = it,
                editState = editState,
                onClick = onPreferredActivity
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        lifeStyle.lifeStyleTargets?.let {
            ChipCard(
                icon = UserPropertyType.LifeStyleTargets.icon,
                title = UserPropertyType.LifeStyleTargets.title,
                list = it,
                editState = editState,
                onClick = onLifeStyleTargets
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            if (editState.value) {
                SubmitButton(text = "Update")
            }
        }
    }

}