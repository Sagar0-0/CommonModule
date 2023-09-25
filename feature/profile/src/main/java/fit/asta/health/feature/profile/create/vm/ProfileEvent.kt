package fit.asta.health.feature.profile.create.vm

import android.net.Uri
import fit.asta.health.data.profile.remote.model.HealthProperties

sealed class ProfileEvent {
    data class GetHealthProperties(val propertyType: String) : ProfileEvent()

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
    object OnProfilePicClear : ProfileEvent()
    object OnSubmit : ProfileEvent()
}
