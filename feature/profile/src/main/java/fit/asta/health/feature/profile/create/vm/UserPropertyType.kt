package fit.asta.health.feature.profile.create.vm

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fit.asta.health.feature.profile.create.vm.ProfileItemType.ChipsCard
import fit.asta.health.feature.profile.create.vm.ProfileItemType.PlainCard
import fit.asta.health.feature.profile.create.vm.ProfileItemType.SessionCard
import fit.asta.health.resources.strings.R
import fit.asta.health.resources.drawables.R as RDraw

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
    data object SignificantHealthHis : UserPropertyType(
        name = "significanthealthhis",
        type = ChipsCard,
        title = R.string.significant_health_history,
        icon = RDraw.drawable.ailements
    )

    data object Injuries : UserPropertyType(
        name = "injuries",
        type = ChipsCard,
        title = R.string.injuries,
        icon = RDraw.drawable.targets
    )

    data object BodyParts : UserPropertyType(
        name = "bodyParts",
        type = ChipsCard,
        title = (R.string.body_parts),
        icon = RDraw.drawable.targets
    )

    data object Ailments : UserPropertyType(
        name = "ailments",
        type = ChipsCard,
        title = (R.string.ailments),
        icon = RDraw.drawable.ailements
    )

    data object Medications : UserPropertyType(
        name = "medications",
        type = ChipsCard,
        title = (R.string.medications),
        icon = RDraw.drawable.medications
    )

    data object HealthTargets : UserPropertyType(
        name = "health-lifeStyleTargets",
        type = ChipsCard,
        title = (R.string.health_targets),
        icon = RDraw.drawable.targets
    )

    data object Addictions : UserPropertyType(
        name = "addictions",
        type = ChipsCard,
        title = (R.string.addictions),
        icon = RDraw.drawable.targets
    )

    //LifeStyle Section
    data object SleepSchedule : UserPropertyType(
        name = "sleep-schedule",
        type = SessionCard,
        title = (R.string.sleep_schedule),
        icon = RDraw.drawable.ic_sunny
    )

    data object WorkSchedule : UserPropertyType(
        name = "work-schedule",
        type = SessionCard,
        title = (R.string.work_schedule),
        icon = RDraw.drawable.ic_sunny
    )

    data object PhysActive : UserPropertyType(
        name = "physically-active",
        type = ChipsCard,
        title = (R.string.physically_active),
        icon = RDraw.drawable.indoorwork
    )

    data object WorkingEnv : UserPropertyType(
        name = "working-env",
        type = ChipsCard,
        title = (R.string.working_environment),
        icon = RDraw.drawable.indoorwork
    )

    data object WorkStyle : UserPropertyType(
        name = "work-style",
        type = PlainCard,
        title = (R.string.work_style),
        icon = RDraw.drawable.indoorwork
    )

    data object WorkingHours : UserPropertyType(
        name = "work-hours",
        type = PlainCard,
        title = (R.string.working_hours),
        icon = RDraw.drawable.indoorwork
    )

    data object CurActivities : UserPropertyType(
        name = "current-activities",
        type = ChipsCard,
        title = (R.string.current_activities),
        icon = RDraw.drawable.currentactivities
    )

    data object PrefActivities : UserPropertyType(
        name = "preferred-activities",
        type = ChipsCard,
        title = (R.string.preferred_activities),
        icon = RDraw.drawable.preferredactivities
    )

    data object LifeStyleTargets : UserPropertyType(
        name = "lifestyle-lifeStyleTargets",
        type = ChipsCard,
        title = (R.string.lifestyle_targets),
        icon = RDraw.drawable.targets
    )

    //Diet Section
    data object DietPref : UserPropertyType(
        name = "dietary-preferences",
        type = PlainCard,
        title = (R.string.dietary_preferences),
        icon = RDraw.drawable.age
    )

    data object NvDays : UserPropertyType(
        name = "nv-days",
        type = ChipsCard,
        title = (R.string.days_you_consume_non_veg),
        icon = RDraw.drawable.nonveg
    )

    data object FoodAllergies : UserPropertyType(
        name = "food-allergies",
        type = ChipsCard,
        title = (R.string.food_allergies),
        icon = RDraw.drawable.foodrestrictions
    )

    data object Cuisines : UserPropertyType(
        name = "cuisines",
        type = ChipsCard,
        title = (R.string.cuisines),
        icon = RDraw.drawable.cuisine
    )

    data object FoodRestrictions : UserPropertyType(
        name = "food-restrictions",
        type = ChipsCard,
        title = (R.string.food_restrictions),
        icon = RDraw.drawable.foodrestrictions
    )

    data object NONE : UserPropertyType(
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