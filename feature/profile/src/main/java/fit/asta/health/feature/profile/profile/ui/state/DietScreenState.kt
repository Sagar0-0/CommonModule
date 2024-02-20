package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.data.profile.remote.model.UserProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class DietScreenState(
    val diet: Diet,
    private val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    // Diet Page
    private val dietPreference =
        (diet.preference ?: listOf()).toMutableStateList()
    private val nonVegDays = (diet.nonVegDays ?: listOf()).toMutableStateList()
    private val dietAllergies =
        (diet.allergies ?: listOf()).toMutableStateList()
    private val dietCuisines = (diet.cuisines ?: listOf()).toMutableStateList()
    private val dietRestrictions =
        (diet.restrictions ?: listOf()).toMutableStateList()

    private val updatedDiet = Diet(
        preference = dietPreference,
        nonVegDays = nonVegDays,
        allergies = dietAllergies,
        cuisines = dietCuisines,
        restrictions = dietRestrictions
    )

    val bottomSheets: List<HealthBottomSheet> = listOf(
        HealthBottomSheet(
            "dp",
            "Dietary Preferences",
            dietPreference,
        ),
        HealthBottomSheet(
//TODO: HANDLE DAYS
            "dp",
            "Non Veg Days",
            nonVegDays,
        ),
        HealthBottomSheet(
            "food",
            "Food Allergies",
            dietAllergies,
        ),
        HealthBottomSheet(
            "cu",
            "Cuisines",
            dietCuisines,
        ),
        HealthBottomSheet(
            "food",
            "Food Restrictions",
            dietRestrictions,
        ),
    )

    fun addProperty(sheetIndex: Int, userProperties: UserProperties) {
        bottomSheets[sheetIndex].list.add(userProperties)
    }

    fun removeProperty(sheetIndex: Int, userProperties: UserProperties) {
        bottomSheets[sheetIndex].list.remove(userProperties)
    }

    fun isPropertySelected(sheetIndex: Int, userProperties: UserProperties): Boolean {
        return bottomSheets[sheetIndex].list.contains(userProperties)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun openPropertiesBottomSheet(
        sheetState: SheetState,
        index: MutableState<Int>,
        bottomSheetVisible: MutableState<Boolean>
    ) {
//        currentBottomSheetIndex = index.value
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties(index.value)
    }

    private fun getHealthProperties(sheetIndex: Int) {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[sheetIndex].id))
    }

    fun getDietData(): Diet {
        return updatedDiet
    }

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<DietScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedDiet
                )
            },
            restore = {
                DietScreenState(
                    diet = it[0] as Diet,
                    coroutineScope,
                    onEvent
                )
            }
        )
    }

    data class HealthBottomSheet(
        val id: String,
        val name: String,
        val list: SnapshotStateList<UserProperties>
    )
}