package fit.asta.health.profile.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import fit.asta.health.common.multiselect.data.FinalSelectedData
import fit.asta.health.common.multiselect.ui.MultiSelectActivity.Companion.SELECTED_DATA
import fit.asta.health.profile.adapter.viewholders.ProfileTabType
import fit.asta.health.profile.listener.OnChipActionListenerImpl
import fit.asta.health.profile.listener.ProfileWatcher
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.utils.Constants
import org.koin.android.ext.android.inject

class ProfileFragment(private val profileTabType: ProfileTabType = ProfileTabType.NONE) :
    Fragment() {

    private val profileView: ProfileView by inject()
    private val profileViewModel: ProfileViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return profileView.setContentView(requireActivity(), container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileView.setProfileTabType(profileTabType)
        profileViewModel.observeProfileLiveData(viewLifecycleOwner, profileTabType, ProfileObserver(profileView))
        profileViewModel.updateTabData(profileTabType)
        profileView.setAdapterListener(ProfileWatcher(profileViewModel))
        profileView.setOnChipListener(OnChipActionListenerImpl(this, profileViewModel))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(Constants.REQUEST_CODE == requestCode && data != null){
            val  selectedData = data.getParcelableExtra<FinalSelectedData>(SELECTED_DATA)
            profileViewModel.updateChipsCount(selectedData)
        }
    }
}