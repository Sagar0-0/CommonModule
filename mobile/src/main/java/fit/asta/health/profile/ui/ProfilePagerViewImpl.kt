package fit.asta.health.profile.ui

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import fit.asta.health.R
import fit.asta.health.profile.ProfileTabsData
import fit.asta.health.profile.adapter.ProfilePagerAdapter
import fit.asta.health.profile.adapter.viewholders.ProfileTabType
import fit.asta.health.profile.ui.contacts.ContactsFragment
import kotlinx.android.synthetic.main.activity_profile.view.*


class ProfilePagerViewImpl : ProfilePagerView {
    private var selectedPos = 0
    private var rootView: View? = null

    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.activity_profile, null,
            false
        )
        return rootView

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUpViewPager(fragmentActivity: ProfileActivity, listener: ProfileViewPagerListener){
        rootView?.let {

            val profile_pager = it.profile_pager
            val profile_tab_layout = it.profile_tab_layout
            val nextBtn = it.profileUpdateBtn

            val pagerAdapter = ProfilePagerAdapter(fragmentActivity)
            pagerAdapter.setProfilesList(getProfilePager())
            profile_pager.adapter = pagerAdapter
            profile_pager.registerOnPageChangeCallback(listener)

            //profile_pager.adapter?.notifyDataSetChanged()
            val tabDetails = getTabDetails()

            TabLayoutMediator(profile_tab_layout, profile_pager)
            { tab, position ->
                tab.text = tabDetails[position].tabTitle
                tab.icon =  ContextCompat.getDrawable(it.context, tabDetails[position].tabIcon)
                profile_pager.setCurrentItem(tab.position, true)
            }.attach()
        }

    }

    override fun updateClickListener(listener: View.OnClickListener) {
        rootView?.let {
            it.profileUpdateBtn.setOnClickListener {
                listener.onClick(it)
            }
        }
    }

    fun getProfilePager(): ArrayList<Fragment> {
        val listFrag = ArrayList<Fragment>()
        listFrag.add(ContactsFragment())
        listFrag.add(ProfileFragment(ProfileTabType.PhysiqueTab))
        listFrag.add(ProfileFragment(ProfileTabType.LifeStyleTab))
        listFrag.add(ProfileFragment(ProfileTabType.HealthTargetsTab))
        return listFrag
    }

    fun getTabDetails(): ArrayList<ProfileTabsData> {
        var tabDetails = ArrayList<ProfileTabsData>()
        rootView?.let {
            tabDetails.add(
                ProfileTabsData(
                    it.context.getString(R.string.tab_title_contact),
                    R.drawable.ic_contact
                )
            )
            tabDetails.add(
                ProfileTabsData(
                    it.context.getString(R.string.tab_title_physique),
                    R.drawable.ic_physique
                )
            )
            tabDetails.add(ProfileTabsData(it.context.getString(R.string.tab_title_lifestyle), R.drawable.ic_lifestyle))
            tabDetails.add(ProfileTabsData(it.context.getString(R.string.tab_title_health_targets), R.drawable.ic_health_targets))
        }

        return tabDetails
    }

    override fun changeState(state: ProfileView.ProfileState) {
        //
    }
}