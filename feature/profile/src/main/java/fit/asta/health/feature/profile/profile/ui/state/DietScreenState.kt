package fit.asta.health.feature.profile.profile.ui.state

import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.toMutableStateList
import fit.asta.health.data.profile.remote.model.Allergies_Field_Name
import fit.asta.health.data.profile.remote.model.Cuisines_Field_Name
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.data.profile.remote.model.Diet_Screen_name
import fit.asta.health.data.profile.remote.model.NonVeg_Field_Name
import fit.asta.health.data.profile.remote.model.Preference_Field_Name
import fit.asta.health.data.profile.remote.model.Restrictions_Field_Name
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

    private val currentListIndex = mutableIntStateOf(0)

    val bottomSheets: List<ProfileBottomSheetPicker> = listOf(
        ProfileBottomSheetPicker(
            Preference_Field_Name,
            "food",
            "Dietary Preferences",
            dietPreference,
        ),
        ProfileBottomSheetPicker(
            //TODO: HANDLE DAYS
            NonVeg_Field_Name,
            "dp",
            "Non Veg Days",
            nonVegDays,
        ),
        ProfileBottomSheetPicker(
            Allergies_Field_Name,
            "food",
            "Food Allergies",
            dietAllergies,
        ),
        ProfileBottomSheetPicker(
            Cuisines_Field_Name,
            "cu",
            "Cuisines",
            dietCuisines,
        ),
        ProfileBottomSheetPicker(
            Restrictions_Field_Name,
            "food",
            "Food Restrictions",
            dietRestrictions,
        ),
    )

    fun openPropertiesBottomSheet(
        sheetState: SheetState,
        index: Int,
        bottomSheetVisible: MutableState<Boolean>
    ) {
        currentListIndex.intValue = index
        bottomSheetVisible.value = true
        coroutineScope.launch { sheetState.expand() }
        getHealthProperties()
    }

    private fun getHealthProperties() {
        onEvent(UserProfileEvent.GetHealthProperties(bottomSheets[currentListIndex.intValue].getQueryParam))
    }

    fun saveProperties(list: List<UserProperties>) {
        bottomSheets[currentListIndex.intValue].list.apply {
            clear()
            addAll(list)
        }
        onEvent(
            UserProfileEvent.SavePropertiesList(
                Diet_Screen_name,
                bottomSheets[currentListIndex.intValue].fieldName,
                list
            )
        )
    }

    fun getUpdatedData() = updatedDiet

    fun getCurrentList() = bottomSheets[currentListIndex.intValue].list.toList()

    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<DietScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedDiet,
                    it.currentListIndex.intValue
                )
            },
            restore = {
                DietScreenState(
                    diet = it[0] as Diet,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.currentListIndex.intValue = it[1] as Int
                }
            }
        )
    }
}