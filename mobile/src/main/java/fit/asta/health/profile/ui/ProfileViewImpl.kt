package fit.asta.health.profile.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.profile.adapter.ProfileAdapter
import fit.asta.health.profile.adapter.ProfileItem
import fit.asta.health.profile.adapter.viewholders.ProfileTabType
import fit.asta.health.profile.listener.OnChangeListener
import fit.asta.health.profile.listener.OnChipActionListener
import kotlinx.android.synthetic.main.fragment_profile_one.view.*

class ProfileViewImpl: ProfileView {


    private var rootView: View? = null
    private var profileTabType: ProfileTabType = ProfileTabType.NONE
    override fun setContentView(activity: Activity, container: ViewGroup?): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.fragment_profile_one, container,
            false)
        initializeViews()
        return rootView
    }

    override fun changeState(state: ProfileView.ProfileState) {
        when(state){
            is ProfileView.ProfileState.LoadProfileState -> {
                setAdapter(state.list)
            }
            else -> { }
        }
    }

    override fun setProfileTabType(tabType: ProfileTabType) {
        profileTabType = tabType
    }

    override fun setAdapterListener(listener: OnChangeListener) {
        rootView?.let{
            (it.profileRecyclerView.adapter as ProfileAdapter).setListener(listener)
        }
    }

    override fun setOnChipListener(listener: OnChipActionListener) {
        rootView?.let{
            (it.profileRecyclerView.adapter as ProfileAdapter).setOnChipListener(listener)
        }
    }


    private fun initializeViews(){
        rootView?.let {
            val profileAdapter = ProfileAdapter()
            when (profileTabType) {
                ProfileTabType.PhysiqueTab -> {
                    val gridLayoutManager = GridLayoutManager(it.context, 2)
                    it.profileRecyclerView.layoutManager = gridLayoutManager
                    gridLayoutManager.spanSizeLookup = (object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return profileAdapter.getSpanCount(position)
                        }
                    })
                }
                else -> {
                    it.profileRecyclerView.layoutManager = LinearLayoutManager(it.context)
                }
            }

            it.profileRecyclerView.adapter = profileAdapter
        }
    }

    private fun setAdapter(list: List<ProfileItem>){
        rootView?.let{
            (it.profileRecyclerView.adapter as ProfileAdapter).updateList(list)
        }
    }
}