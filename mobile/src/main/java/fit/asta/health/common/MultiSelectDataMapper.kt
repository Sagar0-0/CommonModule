package fit.asta.health.common

import fit.asta.health.common.data.MultiSelectData
import fit.asta.health.profile.data.chips.UserInputs

class MultiSelectDataMapper {
    fun toMap(apiUserInputs: UserInputs): List<MultiSelectData> {
        return apiUserInputs.data.values.map{ value ->
            MultiSelectData().apply {
                id = value.uid
                displayName = value.value
            }
        }
    }
}