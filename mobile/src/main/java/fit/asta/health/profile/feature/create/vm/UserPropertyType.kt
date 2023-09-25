package fit.asta.health.profile.feature.create.vm

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fit.asta.health.R
import fit.asta.health.profile.feature.create.vm.ProfileItemType.ChipsCard
import fit.asta.health.profile.feature.create.vm.ProfileItemType.PlainCard
import fit.asta.health.profile.feature.create.vm.ProfileItemType.SessionCard

enum class ProfileItemType(val value: Int) {

    SessionCard(1), PlainCard(2), ChipsCard(3);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

sealed class UserPropertyType(
    val name: String,
    val title: Int?,
    val type: ProfileItemType,
    @DrawableRes val icon: Int,
) {

    @Composable
    fun getTitle(): String = title?.let { stringResource(id = it) } ?: ""


    //Health Section
    object SignificantHealthHis : UserPropertyType(
        name = "significanthealthhis",
        type = ChipsCard,
        title = R.string.significant_health_history,
        icon = R.drawable.ailements
    )

    object Injuries : UserPropertyType(
        name = "injuries",
        type = ChipsCard,
        title = R.string.injuries,
        icon = R.drawable.targets
    )

    object BodyParts : UserPropertyType(
        name = "bodyParts",
        type = ChipsCard,
        title = (R.string.body_parts),
        icon = R.drawable.targets
    )

    object Ailments : UserPropertyType(
        name = "ailments",
        type = ChipsCard,
        title = (R.string.ailments),
        icon = R.drawable.ailements
    )

    object Medications : UserPropertyType(
        name = "medications",
        type = ChipsCard,
        title = (R.string.medications),
        icon = R.drawable.medications
    )

    object HealthTargets : UserPropertyType(
        name = "health-lifeStyleTargets",
        type = ChipsCard,
        title = (R.string.health_targets),
        icon = R.drawable.targets
    )

    object Addictions : UserPropertyType(
        name = "addictions",
        type = ChipsCard,
        title = (R.string.addictions),
        icon = R.drawable.targets
    )

    //LifeStyle Section
    object SleepSchedule : UserPropertyType(
        name = "sleep-schedule",
        type = SessionCard,
        title = (R.string.sleep_schedule),
        icon = R.drawable.ic_sunny
    )

    object WorkSchedule : UserPropertyType(
        name = "work-schedule",
        type = SessionCard,
        title = (R.string.work_schedule),
        icon = R.drawable.ic_sunny
    )

    object PhysActive : UserPropertyType(
        name = "physically-active",
        type = ChipsCard,
        title = (R.string.physically_active),
        icon = R.drawable.indoorwork
    )

    object WorkingEnv : UserPropertyType(
        name = "working-env",
        type = ChipsCard,
        title = (R.string.working_environment),
        icon = R.drawable.indoorwork
    )

    object WorkStyle : UserPropertyType(
        name = "work-style",
        type = PlainCard,
        title = (R.string.work_style),
        icon = R.drawable.indoorwork
    )

    object WorkingHours : UserPropertyType(
        name = "work-hours",
        type = PlainCard,
        title = (R.string.working_hours),
        icon = R.drawable.indoorwork
    )

    object CurActivities : UserPropertyType(
        name = "current-activities",
        type = ChipsCard,
        title = (R.string.current_activities),
        icon = R.drawable.currentactivities
    )

    object PrefActivities : UserPropertyType(
        name = "preferred-activities",
        type = ChipsCard,
        title = (R.string.preferred_activities),
        icon = R.drawable.preferredactivities
    )

    object LifeStyleTargets : UserPropertyType(
        name = "lifestyle-lifeStyleTargets",
        type = ChipsCard,
        title = (R.string.lifestyle_targets),
        icon = R.drawable.targets
    )

    //Diet Section
    object DietPref : UserPropertyType(
        name = "dietary-preferences",
        type = PlainCard,
        title = (R.string.dietary_preferences),
        icon = R.drawable.age
    )

    object NvDays : UserPropertyType(
        name = "nv-days",
        type = ChipsCard,
        title = (R.string.days_you_consume_non_veg),
        icon = R.drawable.nonveg
    )

    object FoodAllergies : UserPropertyType(
        name = "food-allergies",
        type = ChipsCard,
        title = (R.string.food_allergies),
        icon = R.drawable.foodrestrictions
    )

    object Cuisines : UserPropertyType(
        name = "cuisines",
        type = ChipsCard,
        title = (R.string.cuisines),
        icon = R.drawable.cuisine
    )

    object FoodRestrictions : UserPropertyType(
        name = "food-restrictions",
        type = ChipsCard,
        title = (R.string.food_restrictions),
        icon = R.drawable.foodrestrictions
    )

    object NONE : UserPropertyType(
        name = "", type = PlainCard, title = null, icon = 0
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