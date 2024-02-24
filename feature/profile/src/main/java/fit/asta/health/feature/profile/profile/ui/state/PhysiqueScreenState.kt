package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import fit.asta.health.data.profile.remote.model.BMIUnit
import fit.asta.health.data.profile.remote.model.Physique
import kotlinx.coroutines.CoroutineScope

@Stable
class PhysiqueScreenState(
    val physique: Physique,
    private val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Physique Page
    var userWeight by mutableStateOf(physique.weight?.toString())
        private set
    private var weightInFloat = physique.weight
    var weightUnit by mutableStateOf(physique.weightUnit)
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

    var userHeight by mutableStateOf(physique.height?.toString())
        private set
    var heightUnit by mutableStateOf(physique.heightUnit)
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

    var bmiUnit = BMIUnit.BMI.value


    fun isValid(): Boolean {
        return userWeight != null
                && userWeightErrorMessage == null
                && weightUnit != null
                && userHeight != null
                && userHeightErrorMessage == null
                && heightUnit != null
    }

    fun getUpdatedData(): Physique {
        return Physique(
            bodyType = physique.bodyType,
            bmi = physique.bmi,
            bmiUnit = bmiUnit,
            height = heightInFloat,
            heightUnit = heightUnit,
            weight = weightInFloat,
            weightUnit = weightUnit
        )
    }

    fun saveHeight(height: Float, unit: Int) {
        userHeight = height.toString()
        heightUnit = unit
        onEvent(UserProfileEvent.SaveHeight(height, unit))
    }

    fun saveWeight(weight: Float, unit: Int) {
        userWeight = weight.toString()
        weightUnit = unit
        onEvent(UserProfileEvent.SaveWeight(weight, unit))
    }

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<PhysiqueScreenState, *> = listSaver(
            save = {
                listOf(
                    it.getUpdatedData()
                )
            },
            restore = {
                PhysiqueScreenState(
                    physique = it[0],
                    coroutineScope,
                    onEvent
                )
            }
        )
    }
}