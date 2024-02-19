package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import fit.asta.health.data.profile.remote.model.Physique
import kotlinx.coroutines.CoroutineScope
import java.util.Calendar

@Stable
class PhysiqueScreenState(
    val physique: Physique,
    private val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Physique Page
    val calendar: Calendar = Calendar.getInstance()
    var userAge by mutableIntStateOf(physique.age)
        private set
    var userAgeErrorMessage by mutableStateOf<String?>(null)
        private set

    fun setAge(value: Int) {
        userAgeErrorMessage = if (value == 0) {
            "Invalid age"
        } else if (value < 10) {
            "Age should be more than 10"
        } else {
            null
        }
        userAge = value
    }


    var userWeight by mutableStateOf(physique.weight.toString())
        private set
    private var weightInFloat = physique.weight
    var weightUnit by mutableIntStateOf(physique.weightUnit)
    var userWeightErrorMessage by mutableStateOf<String?>(null)
        private set

    fun setWeight(value: String) {
        val weightInFloat = value.toFloatOrNull()
        userWeightErrorMessage = if (weightInFloat == null) {
            "Invalid weight"
        } else if (weightInFloat < 30.00F) {
            "Weight should be more than 30"
        } else {
            this.weightInFloat = weightInFloat
            null
        }
        userWeight = value
    }

    var userHeight by mutableStateOf(physique.height.toString())
        private set
    var heightUnit by mutableIntStateOf(physique.heightUnit)
    private var heightInFloat = physique.height
    var userHeightErrorMessage by mutableStateOf<String?>(null)
        private set

    fun setHeight(value: String) {
        val heightInFloat = value.toFloatOrNull()
        userHeightErrorMessage = if (heightInFloat == null) {
            "Invalid height"
        } else if (heightInFloat < 3.00F) {
            "Height should be more than 3"
        } else {
            this.heightInFloat = heightInFloat
            null
        }
        userHeight = value
    }

    var userGender by mutableIntStateOf(physique.gender)
    var isPregnant by mutableIntStateOf(physique.isPregnant)
    var onPeriod by mutableIntStateOf(physique.onPeriod)
    var userPregnancyWeek by mutableStateOf(physique.pregnancyWeek?.toString())
    var userPregnancyWeekErrorMessage by mutableStateOf<String?>(null)


    fun isValid(): Boolean {
        return userAgeErrorMessage == null
                && userHeightErrorMessage == null
                && userWeightErrorMessage == null
                && userPregnancyWeekErrorMessage == null
    }

    fun getPhysiqueData(): Physique {
        return Physique(
            age = userAge,
            bodyType = physique.bodyType,
            bmi = physique.bmi,
            bmiUnit = physique.bmiUnit,
            gender = userGender,
            height = heightInFloat,
            heightUnit = heightUnit,
            isPregnant = isPregnant,
            onPeriod = onPeriod,
            pregnancyWeek = userPregnancyWeek?.toIntOrNull(),
            weight = weightInFloat,
            weightUnit = weightUnit
        )
    }

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<PhysiqueScreenState, *> = listSaver(
            save = {
                listOf(
                    it.getPhysiqueData()
                )
            },
            restore = {
                PhysiqueScreenState(
                    physique = it[0] as Physique,
                    coroutineScope,
                    onEvent
                )
            }
        )
    }
}