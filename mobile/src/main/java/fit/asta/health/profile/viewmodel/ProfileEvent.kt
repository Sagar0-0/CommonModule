package fit.asta.health.profile.viewmodel

import android.net.Uri
import fit.asta.health.profile.model.domain.ComposeIndex
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.ThreeToggleSelections
import fit.asta.health.profile.model.domain.TwoToggleSelections

sealed class ProfileEvent {
    data class GetHealthProperties(val propertyType: String) : ProfileEvent()
    data class SetSelectHealthHisOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedInjOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedBodyPrtOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedAilOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedMedOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedHealthTarOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedAddictionOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedFoodResOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedIsPregnantOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedIsOnPeriodOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedPhyActOption(val option: ThreeToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedWorkingHrsOption(
        val option: ThreeToggleSelections,
        val optionIndex: Int,
    ) : ProfileEvent()

    data class SetSelectedWorkingEnvOption(val option: TwoToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedWorkingStyleOption(
        val option: TwoToggleSelections,
        val optionIndex: Int,
    ) : ProfileEvent()

    data class SetSelectedGenderOption(val option: ThreeToggleSelections, val optionIndex: Int) :
        ProfileEvent()

    data class SetSelectedAddItemOption(
        val item: HealthProperties,
        val index: Int,
        val composeIndex: ComposeIndex,
    ) : ProfileEvent()

    data class SetSelectedRemoveItemOption(
        val item: HealthProperties,
        val index: Int,
        val composeIndex: ComposeIndex,
    ) : ProfileEvent()

    data class OnNameChange(val name: String) : ProfileEvent()
    data class OnEmailChange(val email: String) : ProfileEvent()
    data class OnUserImgChange(val url: Uri?) : ProfileEvent()
    data class OnUserWeightChange(val weight: String) : ProfileEvent()
    data class OnUserHeightChange(val height: String) : ProfileEvent()
    data class OnUserWakeUpTimeChange(val wakeUpTime: String) : ProfileEvent()
    data class OnUserBedTimeChange(val bedTime: String) : ProfileEvent()
    data class OnUserJStartTimeChange(val jStartTime: String) : ProfileEvent()
    data class OnUserJEndTimeChange(val jEndTime: String) : ProfileEvent()
    data class OnUserDOBChange(val dob: String) : ProfileEvent()
    data class OnUserAGEChange(val age: String) : ProfileEvent()
    data class OnUserPregWeekChange(val week: String) : ProfileEvent()
    data class OnUserInjuryTimeChange(val time: String) : ProfileEvent()
    data class IsHealthValid(val valid: Boolean) : ProfileEvent()
    data class IsPhyValid(val valid: Boolean) : ProfileEvent()
    data class IsDietValid(val valid: Boolean) : ProfileEvent()
    data class DoAllInputsValid(val valid: Boolean) : ProfileEvent()
    object OnProfilePicClear : ProfileEvent()
    object OnSubmit : ProfileEvent()
}
