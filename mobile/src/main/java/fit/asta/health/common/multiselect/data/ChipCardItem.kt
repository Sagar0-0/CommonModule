package fit.asta.health.common.multiselect.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class ChipCardItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var value: ArrayList<Value> = arrayListOf(),
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.ChipsCard
) :
    ProfileItem, Parcelable





