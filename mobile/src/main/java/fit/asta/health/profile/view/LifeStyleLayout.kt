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

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        SessionCard(
            //icon = UserPropertyType.SleepSchedule.icon,
            title = UserPropertyType.SleepSchedule.title,
            session = lifeStyle.sleep,
            editState = editState,
            onSleepSchedule)

        Spacer(modifier = Modifier.height(16.dp))

        SingleSelectionCard(icon = UserPropertyType.WorkStyle.icon,
            title = UserPropertyType.WorkStyle.title,
            value = lifeStyle.workStyle.name,
            editState = editState,
            onWorkStyle)

        Spacer(modifier = Modifier.height(16.dp))

        SessionCard(
            //icon = UserPropertyType.WorkSchedule.icon,
            title = UserPropertyType.WorkSchedule.title,
            session = lifeStyle.workingHours,
            editState = editState,
            onWorkSchedule)

        Spacer(modifier = Modifier.height(16.dp))

        SingleSelectionCard(icon = UserPropertyType.PhysActive.icon,
            title = UserPropertyType.PhysActive.title,
            value = lifeStyle.physicalActivity.name,
            editState = editState,
            onPhysicallyActive)

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(icon = UserPropertyType.CurActivities.icon,
            title = UserPropertyType.CurActivities.title,
            list = lifeStyle.curActivities,
            editState = editState,
            onCurrentActive)

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(icon = UserPropertyType.PrefActivities.icon,
            title = UserPropertyType.PrefActivities.title,
            list = lifeStyle.prefActivities,
            editState = editState,
            onPreferredActivity)

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(
            icon = UserPropertyType.LifeStyleTargets.icon,
            title = UserPropertyType.LifeStyleTargets.title,
            list = lifeStyle.lifeStyleTargets,
            editState = editState,
            onLifeStyleTargets
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            if (editState.value) {
                SubmitButton(text = "Update")
            }
        }
    }
}