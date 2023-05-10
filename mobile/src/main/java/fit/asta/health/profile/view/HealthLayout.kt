@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Health
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.ProfileChipCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Composable
fun HealthLayout(
    health: Health,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        health.healthHistory?.let { healthHistoryList ->
            ProfileChipCard(
                icon = UserPropertyType.SignificantHealthHis.icon,
                title = UserPropertyType.SignificantHealthHis.title,
                list = healthHistoryList
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        health.injuries?.let { injuriesList ->
            ProfileChipCard(
                icon = UserPropertyType.Injuries.icon,
                title = UserPropertyType.Injuries.title,
                list = injuriesList
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        health.bodyPart?.let { bodyPartsList ->
            ProfileChipCard(
                icon = UserPropertyType.BodyParts.icon,
                title = UserPropertyType.BodyParts.title,
                list = bodyPartsList
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        health.ailments?.let {
            ProfileChipCard(
                icon = UserPropertyType.Ailments.icon,
                title = UserPropertyType.Ailments.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        health.medications?.let {
            ProfileChipCard(
                icon = UserPropertyType.Medications.icon,
                title = UserPropertyType.Medications.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        health.healthTargets?.let {
            ProfileChipCard(
                icon = UserPropertyType.HealthTargets.icon,
                title = UserPropertyType.HealthTargets.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        health.addiction?.let { addictionList ->
            ProfileChipCard(
                icon = UserPropertyType.Addictions.icon,
                title = UserPropertyType.Addictions.title,
                list = addictionList
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}