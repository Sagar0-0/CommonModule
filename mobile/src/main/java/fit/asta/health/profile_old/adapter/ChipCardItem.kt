package fit.asta.health.profile_old.adapter

import android.os.Parcelable
import fit.asta.health.profile_old.adapter.viewholders.ProfileTabType
import fit.asta.health.profile_old.data.userprofile.Value
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





