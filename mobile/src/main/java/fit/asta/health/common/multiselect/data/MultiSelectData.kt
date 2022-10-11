package fit.asta.health.common.multiselect.data

import android.os.Parcelable
import fit.asta.health.profile_old.adapter.viewholders.ProfileTabType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MultiSelectData(var id: String = "",
                           var displayName: String =  "",
                           var isSelected: Boolean = false): Parcelable

@Parcelize
data class FinalSelectedData(
    var cardUid: String = "",
    var tabType: ProfileTabType = ProfileTabType.NONE,
    var data: List<MultiSelectData> = listOf()
): Parcelable