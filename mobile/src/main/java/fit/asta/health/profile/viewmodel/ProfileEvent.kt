package fit.asta.health.profile.viewmodel

import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.ThreeToggleSelections
import fit.asta.health.profile.model.domain.TwoToggleSelections

sealed class ProfileEvent {
    data class GetHealthProperties(val propertyType: String) : ProfileEvent()
    data class SetSelectHealthHisOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedInjOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedBodyPrtOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedAilOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedMedOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedHealthTarOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedFoodResOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedIsPregnantOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedPhyActOption(val option: ThreeToggleSelections) : ProfileEvent()
    data class SetSelectedWorkingHrsOption(val option: ThreeToggleSelections) : ProfileEvent()
    data class SetSelectedWorkingEnvOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedWorkingStyleOption(val option: TwoToggleSelections) : ProfileEvent()
    data class SetSelectedGenderOption(val option: ThreeToggleSelections) : ProfileEvent()
    data class SetSelectedAddItemOption(val item: HealthProperties) : ProfileEvent()
    data class SetSelectedRemoveItemOption(val item: HealthProperties) : ProfileEvent()
}
