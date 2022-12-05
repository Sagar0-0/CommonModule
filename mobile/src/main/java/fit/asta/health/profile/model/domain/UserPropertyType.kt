package fit.asta.health.profile.model.domain

import androidx.annotation.DrawableRes
import fit.asta.health.R

enum class ProfileItemType(val value: Int) {

    SessionCard(1),
    PlainCard(2),
    ChipsCard(3);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

sealed class UserPropertyType(
    val name: String,
    val title: String,
    val type: ProfileItemType,
    @DrawableRes val icon: Int
) {
    object SleepSchedule : UserPropertyType(
        name = "sleep-schedule",
        type = ProfileItemType.SessionCard,
        title = "SLEEP SCHEDULE",
        icon = R.drawable.ic_sunny
    )

    object WorkSchedule : UserPropertyType(
        name = "work-schedule",
        type = ProfileItemType.SessionCard,
        title = "WORK SCHEDULE",
        icon = R.drawable.ic_sunny
    )

    object WorkStyle : UserPropertyType(
        name = "work-style",
        type = ProfileItemType.PlainCard,
        title = "WORK STYLE",
        icon = R.drawable.indoorwork
    )

    object PhysActive : UserPropertyType(
        name = "physically-active",
        type = ProfileItemType.ChipsCard,
        title = "PHYSICALLY ACTIVE",
        icon = R.drawable.indoorwork
    )

    object CurActivities : UserPropertyType(
        name = "current-activities",
        type = ProfileItemType.ChipsCard,
        title = "CURRENT ACTIVITIES",
        icon = R.drawable.currentactivities
    )

    object PrefActivities : UserPropertyType(
        name = "preferred-activities",
        type = ProfileItemType.ChipsCard,
        title = "PREFERRED ACTIVITIES",
        icon = R.drawable.preferredactivities
    )

    object LifeStyleTargets : UserPropertyType(
        name = "lifestyle-targets",
        type = ProfileItemType.ChipsCard,
        title = "LIFESTYLE TARGETS",
        icon = R.drawable.targets
    )

    object Ailments : UserPropertyType(
        name = "ailments",
        type = ProfileItemType.ChipsCard,
        title = "AILMENTS",
        icon = R.drawable.ailements
    )

    object Medications : UserPropertyType(
        name = "medications",
        type = ProfileItemType.ChipsCard,
        title = "MEDICATIONS",
        icon = R.drawable.medications
    )

    object Injuries : UserPropertyType(
        name = "injuries",
        type = ProfileItemType.ChipsCard,
        title = "INJURIES",
        icon = R.drawable.targets
    )

    object HealthTargets : UserPropertyType(
        name = "health-targets",
        type = ProfileItemType.ChipsCard,
        title = "HEALTH TARGETS",
        icon = R.drawable.targets
    )

    object DietPref : UserPropertyType(
        name = "dietary-preferences",
        type = ProfileItemType.PlainCard,
        title = "DIETARY PREFERENCES",
        icon = R.drawable.age
    )

    object NvDays : UserPropertyType(
        name = "nv-days",
        type = ProfileItemType.ChipsCard,
        title = "DAYS YOU CONSUME NON-VEG",
        icon = R.drawable.nonveg
    )

    object Cuisines : UserPropertyType(
        name = "cuisines",
        type = ProfileItemType.ChipsCard,
        title = "CUISINES",
        icon = R.drawable.cuisine
    )

    object FoodAllergies : UserPropertyType(
        name = "food-allergies",
        type = ProfileItemType.ChipsCard,
        title = "FOOD ALLERGIES",
        icon = R.drawable.foodrestrictions
    )

    object FoodRestrictions : UserPropertyType(
        name = "food-restrictions",
        type = ProfileItemType.ChipsCard,
        title = "FOOD RESTRICTIONS",
        icon = R.drawable.foodrestrictions
    )

    object NONE : UserPropertyType(
        name = "",
        type = ProfileItemType.PlainCard,
        title = "",
        icon = 0
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
                15 -> FoodRestrictions
                else -> NONE
            }
        }
    }
}