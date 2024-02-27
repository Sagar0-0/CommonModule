@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.show.view

import androidx.compose.runtime.Composable
import fit.asta.health.feature.profile.show.view.components.ProfileSingleSelectionCard
import kotlinx.coroutines.ExperimentalCoroutinesApi

// Health Screen Layout
//@Composable
//fun LifeStyleLayout(
//    lifeStyle: LifeStyle,
//) {
//
//    val physicalActivityMap = mapOf(
//        1 to "Less", 2 to "Moderate", 3 to "Very"
//    )
//
//    val workingEnvMap = mapOf(
//        1 to "Standing", 2 to "Sitting"
//    )
//
//    val workStyleMap = mapOf(
//        1 to "Indoor", 2 to "Outdoor"
//    )
//
//    val workingHoursMap = mapOf(
//        1 to "Morning", 2 to "Afternoon", 3 to "Evening"
//    )
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(AppTheme.spacing.level2),
//        contentPadding = PaddingValues(vertical = AppTheme.spacing.level2),
//        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
//    ) {
//        item {
//            ProfileSessionCard(
//                title = SleepSchedule.getTitle(), sleepSchedule = lifeStyle.sleepTime
//            )
//        }
//
//        item {
//            ProfileSessionCard(
//                title = WorkSchedule.getTitle(), sleepSchedule = lifeStyle.workTime
//            )
//        }
//
//        item {
//            SingleSelectionProfileCard(
//                icon = PhysActive.icon,
//                title = PhysActive.getTitle(),
//                value = lifeStyle.physicalActive,
//                valueMap = physicalActivityMap
//            )
//        }
//
//        item {
//            SingleSelectionProfileCard(
//                icon = WorkingEnv.icon,
//                title = WorkingEnv.getTitle(),
//                value = lifeStyle.workingEnv,
//                valueMap = workingEnvMap
//            )
//        }
//
//        item {
//            SingleSelectionProfileCard(
//                icon = WorkStyle.icon,
//                title = WorkStyle.getTitle(),
//                value = lifeStyle.workStyle,
//                valueMap = workStyleMap
//            )
//        }
//
//        item {
//            SingleSelectionProfileCard(
//                icon = WorkingHours.icon,
//                title = WorkingHours.getTitle(),
//                value = lifeStyle.workingHours,
//                valueMap = workingHoursMap
//            )
//        }
//
//        val cardData = listOf(
//            lifeStyle.curActivities to UserPropertyType.CurActivities,
//            lifeStyle.prefActivities to UserPropertyType.PrefActivities,
//            lifeStyle.lifeStyleTargets to UserPropertyType.LifeStyleTargets
//        )
//
//        cardData.forEach { (activities, propertyType) ->
//            item {
//                activities?.let {
//                    ProfileChipCard(
//                        icon = propertyType.icon, title = propertyType.getTitle(), list = it
//                    )
//                }
//            }
//        }
//    }
//}


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