package fit.asta.health.common.multiselect

import fit.asta.health.common.multiselect.data.MultiSelectData
import fit.asta.health.common.multiselect.data.UserInputs

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