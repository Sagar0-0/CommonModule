package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.data.profile.remote.model.UserProperties
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState.ProfileBottomSheetPicker
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

    val bottomSheets: List<ProfileBottomSheetPicker> = listOf(
        ProfileBottomSheetPicker(
            "dp",
            "Dietary Preferences",
            dietPreference,
        ),
        ProfileBottomSheetPicker(
            //TODO: HANDLE DAYS
            "dp",
            "Non Veg Days",
            nonVegDays,
        ),
        ProfileBottomSheetPicker(
            "food",
            "Food Allergies",
            dietAllergies,
        ),
        ProfileBottomSheetPicker(
            "cu",
            "Cuisines",
            dietCuisines,
        ),
        ProfileBottomSheetPicker(
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
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties(index.value)
    }

    private fun getHealthProperties(sheetIndex: Int) {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[sheetIndex].id))
    }

    fun getUpdatedData() = updatedDiet

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
                    diet = it[0],
                    coroutineScope,
                    onEvent
                )
            }
        )
    }
}