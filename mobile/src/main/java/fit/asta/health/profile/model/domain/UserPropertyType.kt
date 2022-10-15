package fit.asta.health.profile.model.domain

import androidx.annotation.DrawableRes
import fit.asta.health.R

sealed class UserPropertyType(
    val title: String,
    @DrawableRes val icon: Int
) {
    object SleepSchedule : UserPropertyType(
        title = "SLEEP SCHEDULE",
        icon = R.drawable.ic_sunny
    )

    object WorkSchedule : UserPropertyType(
        title = "WORK SCHEDULE",
        icon = R.drawable.ic_sunny
    )

    object WorkStyle : UserPropertyType(
        title = "WORK STYLE",
        icon = R.drawable.indoorwork
    )

    object PhysActive : UserPropertyType(
        title = "PHYSICALLY ACTIVE",
        icon = R.drawable.indoorwork
    )

    object CurActivities : UserPropertyType(
        title = "CURRENT ACTIVITIES",
        icon = R.drawable.currentactivities
    )

    object PrefActivities : UserPropertyType(
        title = "PREFERRED ACTIVITIES",
        icon = R.drawable.preferredactivities
    )

    object LifeStyleTargets : UserPropertyType(
        title = "LIFESTYLE TARGETS",
        icon = R.drawable.targets
    )

    object Ailments : UserPropertyType(
        title = "AILMENTS",
        icon = R.drawable.ailements
    )

    object Medications : UserPropertyType(
        title = "MEDICATIONS",
        icon = R.drawable.medications
    )

    object Injuries : UserPropertyType(
        title = "INJURIES",
        icon = R.drawable.targets
    )

    object HealthTargets : UserPropertyType(
        title = "HEALTH TARGETS",
        icon = R.drawable.targets
    )

    object DietPref : UserPropertyType(
        title = "DIETARY PREFERENCES",
        icon = R.drawable.age
    )

    object NvDays : UserPropertyType(
        title = "DAYS YOU CONSUME NON-VEG",
        icon = R.drawable.nonveg
    )

    object Cuisines : UserPropertyType(
        title = "CUISINES",
        icon = R.drawable.cuisine
    )

    object FoodAllergies : UserPropertyType(
        title = "FOOD ALLERGIES",
        icon = R.drawable.foodrestrictions
    )

    companion object {
        fun fromWMO(code: Int): UserPropertyType {
            return when (code) {
                0 -> SleepSchedule
                1 -> WorkSchedule
                2 -> WorkStyle
                3 -> PhysActive
                4 -> CurActivities
                5 -> PrefActivities
                6 -> LifeStyleTargets
                7 -> Ailments
                8 -> Medications
                9 -> Injuries
                10 -> HealthTargets
                11 -> DietPref
                12 -> NvDays
                13 -> Cuisines
                14 -> FoodAllergies
                else -> SleepSchedule
            }
        }
    }
}