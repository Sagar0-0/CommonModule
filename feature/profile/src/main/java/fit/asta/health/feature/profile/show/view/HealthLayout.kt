@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.show.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.Health
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.profile.create.vm.UserPropertyType
import fit.asta.health.feature.profile.show.view.components.ProfileChipCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Composable
fun HealthLayout(health: Health) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacing.level3)
    ) {
        item {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
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
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
                }
            }
        }
    }
}

