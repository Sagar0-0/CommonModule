package fit.asta.health.old_profile.listener

import fit.asta.health.old_profile.adapter.ChipCardItem

interface OnChipActionListener {

    fun onChipAdd(chipCardItem: ChipCardItem)
    fun onChipUpdate(uid: String)
    fun onChipDelete(chipCardItem: ChipCardItem)
}