package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import fit.asta.health.data.profile.remote.model.BMIUnit
import fit.asta.health.data.profile.remote.model.BodyType_Field_Name
import fit.asta.health.data.profile.remote.model.HeightUnit
import fit.asta.health.data.profile.remote.model.Physique
import fit.asta.health.data.profile.remote.model.Physique_Screen_Name
import fit.asta.health.data.profile.remote.model.WeightUnit
import kotlinx.coroutines.CoroutineScope
import kotlin.math.pow

@Stable
class PhysiqueScreenState(
    val physique: Physique,
    private val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    //Physique Page
    var userWeight by mutableStateOf(physique.weight)
    var weightUnit by mutableStateOf(physique.weightUnit)

    var userHeight by mutableStateOf(physique.height)
    var heightUnit by mutableStateOf(physique.heightUnit)

    var bmi by mutableStateOf(physique.bmi)
    private var bmiUnit = BMIUnit.BMI.value

    var bodyType by mutableStateOf(physique.bodyType)

    fun getUpdatedData(): Physique {
        return Physique(
            bodyType = bodyType,
            bmi = bmi,
            bmiUnit = bmiUnit,
            height = userHeight,
            heightUnit = heightUnit,
            weight = userWeight,
            weightUnit = weightUnit
        )
    }

    fun saveHeight(height: Double, unit: Int) {
        userHeight = height
        heightUnit = unit
        onEvent(UserProfileEvent.SaveHeight(height, unit))
        updateBmi()
    }

    fun saveWeight(weight: Double, unit: Int) {
        userWeight = weight
        weightUnit = unit
        onEvent(UserProfileEvent.SaveWeight(weight, unit))
        updateBmi()
    }

    private fun updateBmi() {
        if (userHeight != null && userWeight != null && weightUnit != null && heightUnit != null) {
            bmi = calculateBMI(
                userWeight!!,
                userHeight!!,
                WeightUnit.getType(weightUnit!!),
                HeightUnit.getType(heightUnit!!)
            )
        }
    }

    private fun calculateBMI(
        weight: Double,
        height: Double,
        weightUnit: WeightUnit,
        heightUnit: HeightUnit
    ): Double {
        val weightInKg = convertWeight(weight, weightUnit)
        val heightInMeters = convertHeight(height, heightUnit)
        require(heightInMeters > 0.0) { "Height must be greater than 0." }
        require(weightInKg > 0.0) { "Weight must be greater than 0." }
        val bmi = weightInKg / (heightInMeters * heightInMeters)
        return bmi.format(2).toDouble()
    }

    private fun convertWeight(weight: Double, unit: WeightUnit): Double {
        return when (unit) {
            WeightUnit.LB -> weight * 0.453592 // Convert lbs to kg
            WeightUnit.KG -> weight
        }
    }

    private fun convertHeight(height: Double, unit: HeightUnit): Double {
        return when (unit) {
            HeightUnit.CM -> height / 100.0 // Convert cm to meters
            HeightUnit.INCH -> height * 0.0254 // Convert inch to meters
        }
    }

    private fun Double.format(decimals: Int): String {
        // Same formatting function as before
        val scale = 10.0.pow(decimals.toDouble())
        val rounded = this * scale
        val intPart = rounded.toInt()
        val fractionPart = rounded - intPart
        return "%d.%0${decimals}d".format(intPart, fractionPart.toInt())
    }

    var bodyTypeBottomSheetVisible = mutableStateOf(false)

    fun saveBodyType(it: Int) {
        bodyType = it
        onEvent(
            UserProfileEvent.SaveInt(
                Physique_Screen_Name,
                BodyType_Field_Name,
                it
            )
        )
    }

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<PhysiqueScreenState, *> = listSaver(
            save = {
                listOf(
                    it.getUpdatedData(),
                    it.bodyTypeBottomSheetVisible.value
                )
            },
            restore = {
                PhysiqueScreenState(
                    physique = it[0] as Physique,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.bodyTypeBottomSheetVisible.value = it[1] as Boolean
                }
            }
        )
    }
}