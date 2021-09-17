package fit.asta.health.profile.listener

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import fit.asta.health.common.ui.MultiSelectActivity
import fit.asta.health.profile.adapter.ChipCardItem
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.utils.Constants


class OnChipActionListenerImpl(
    val fragment: Fragment,
    val viewModel: ProfileViewModel
) : OnChipActionListener {

    override fun onChipAdd(uid: ChipCardItem) {
        fragment.context?.let { launch(it, uid) }
    }

    override fun onChipUpdate(uid: String) {

    }

    override fun onChipDelete(chipCardItem: ChipCardItem) {
        viewModel.deleteChipData(chipCardItem)
    }

    // This is the special case for startActivityResult(), couldn't able to put Activity Launcher
    fun launch(context: Context, chipCardItem: ChipCardItem) {
        val intent = Intent(context, MultiSelectActivity::class.java)
        intent.apply {
            putExtra(MultiSelectActivity.UID, chipCardItem)
            fragment.startActivityForResult(
                this, Constants.REQUEST_CODE
            )
        }
    }
}