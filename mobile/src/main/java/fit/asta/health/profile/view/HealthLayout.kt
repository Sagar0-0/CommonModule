package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Health
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.ChipCard
import fit.asta.health.profile.view.components.UpdateButton


@Composable
fun HealthLayout(
    health: Health,
    editState: MutableState<Boolean>,
) {

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(16.dp)) {

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(icon = UserPropertyType.Ailments.icon,
            title = UserPropertyType.Ailments.title,
            list = health.ailments,
            editState = editState)

        /*Spacer(modifier = Modifier.height(16.dp))

        ChipCard(
            cardImg = UserPropertyType.Injuries.icon,
            cardType = UserPropertyType.Injuries.title,
            cardList = health.injuries,
            editState = editState
        )*/

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(icon = UserPropertyType.Medications.icon,
            title = UserPropertyType.Medications.title,
            list = health.medications,
            editState = editState)

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(icon = UserPropertyType.HealthTargets.icon,
            title = UserPropertyType.HealthTargets.title,
            list = health.targets,
            editState = editState)

        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
            if (editState.value) {
                UpdateButton()
            }
        }
    }
}