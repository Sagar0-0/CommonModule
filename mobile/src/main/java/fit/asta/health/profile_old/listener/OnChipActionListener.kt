package fit.asta.health.profile_old.listener

import fit.asta.health.profile_old.adapter.ChipCardItem

interface OnChipActionListener {

    fun onChipAdd(chipCardItem: ChipCardItem)
    fun onChipUpdate(uid: String)
    fun onChipDelete(chipCardItem: ChipCardItem)
}