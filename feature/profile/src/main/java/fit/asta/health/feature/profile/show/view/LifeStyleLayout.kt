@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.show.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.LifeStyle
import fit.asta.health.designsystem.theme.spacing
import fit.asta.health.feature.profile.create.vm.UserPropertyType
import fit.asta.health.feature.profile.create.vm.UserPropertyType.PhysActive
import fit.asta.health.feature.profile.create.vm.UserPropertyType.SleepSchedule
import fit.asta.health.feature.profile.create.vm.UserPropertyType.WorkSchedule
import fit.asta.health.feature.profile.create.vm.UserPropertyType.WorkStyle
import fit.asta.health.feature.profile.create.vm.UserPropertyType.WorkingEnv
import fit.asta.health.feature.profile.create.vm.UserPropertyType.WorkingHours
import fit.asta.health.feature.profile.show.view.components.ProfileChipCard
import fit.asta.health.feature.profile.show.view.components.ProfileSessionCard
import fit.asta.health.feature.profile.show.view.components.ProfileSingleSelectionCard
import kotlinx.coroutines.ExperimentalCoroutinesApi

// Health Screen Layout
@Composable
fun LifeStyleLayout(
    lifeStyle: LifeStyle,
) {

    val physicalActivityMap = mapOf(
        1 to "Less", 2 to "Moderate", 3 to "Very"
    )

    val workingEnvMap = mapOf(
        1 to "Standing", 2 to "Sitting"
    )

    val workStyleMap = mapOf(
        1 to "Indoor", 2 to "Outdoor"
    )

    val workingHoursMap = mapOf(
        1 to "Morning", 2 to "Afternoon", 3 to "Evening"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.medium),
        contentPadding = PaddingValues(vertical = spacing.medium),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
    ) {
        item {
            ProfileSessionCard(
                title = SleepSchedule.getTitle(), session = lifeStyle.sleep
            )
        }

        item {
            ProfileSessionCard(
                title = WorkSchedule.getTitle(), session = lifeStyle.workingTime
            )
        }

        item {
            SingleSelectionProfileCard(
                icon = PhysActive.icon,
                title = PhysActive.getTitle(),
                value = lifeStyle.physicalActivity,
                valueMap = physicalActivityMap
            )
        }

        item {
            SingleSelectionProfileCard(
                icon = WorkingEnv.icon,
                title = WorkingEnv.getTitle(),
                value = lifeStyle.workingEnv,
                valueMap = workingEnvMap
            )
        }

        item {
            SingleSelectionProfileCard(
                icon = WorkStyle.icon,
                title = WorkStyle.getTitle(),
                value = lifeStyle.workStyle,
                valueMap = workStyleMap
            )
        }

        item {
            SingleSelectionProfileCard(
                icon = WorkingHours.icon,
                title = WorkingHours.getTitle(),
                value = lifeStyle.workingHours,
                valueMap = workingHoursMap
            )
        }

        val cardData = listOf(
            lifeStyle.curActivities to UserPropertyType.CurActivities,
            lifeStyle.prefActivities to UserPropertyType.PrefActivities,
            lifeStyle.lifeStyleTargets to UserPropertyType.LifeStyleTargets
        )

        cardData.forEach { (activities, propertyType) ->
            item {
                activities?.let {
                    ProfileChipCard(
                        icon = propertyType.icon, title = propertyType.getTitle(), list = it
                    )
                }
            }
        }
    }
}


@Composable
fun SingleSelectionProfileCard(
    icon: Int,
    title: String,
    value: Int?,
    valueMap: Map<Int, String>,
) {
    value?.let {
        val displayValue = valueMap[it] ?: ""
        ProfileSingleSelectionCard(
            icon = icon, title = title, value = displayValue
        )
    }
}