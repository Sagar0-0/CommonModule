@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.feature.show.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.Health
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.profile.feature.create.vm.UserPropertyType
import fit.asta.health.profile.feature.show.view.components.ProfileChipCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Composable
fun HealthLayout(health: Health) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AstaThemeX.spacingX.medium)
    ) {
        item {
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
        }

        val healthItems = listOf(
            UserPropertyType.SignificantHealthHis to health.healthHistory,
            UserPropertyType.Injuries to health.injuries,
            UserPropertyType.BodyParts to health.bodyPart,
            UserPropertyType.Ailments to health.ailments,
            UserPropertyType.Medications to health.medications,
            UserPropertyType.HealthTargets to health.healthTargets,
            UserPropertyType.Addictions to health.addiction
        )

        healthItems.forEach { (propertyType, itemList) ->
            itemList?.let { list ->
                item {
                    ProfileChipCard(
                        icon = propertyType.icon, title = propertyType.getTitle(), list = list
                    )
                    Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
                }
            }
        }
    }
}

