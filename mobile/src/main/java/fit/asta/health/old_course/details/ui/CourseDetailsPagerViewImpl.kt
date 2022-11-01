package fit.asta.health.old_course.details.ui

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import fit.asta.health.R
import fit.asta.health.old_course.details.CourseDetailsActivity
import fit.asta.health.old_course.details.CourseDetailsTabsData
import fit.asta.health.old_course.details.CourseDetailsView
import fit.asta.health.old_course.details.CourseDetailsViewPagerListener
import fit.asta.health.old_course.details.adapter.CourseDetailsPagerAdapter


class CourseDetailsPagerViewImpl : CourseDetailsPagerView {
    private var selectedPos = 0
    private var rootView: View? = null

    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.profile_activity, null,
            false
        )
        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUpViewPager(
        fragmentActivity: CourseDetailsActivity,
        listener: CourseDetailsViewPagerListener
    ) {
        rootView?.let {

            val coursePager = it.findViewById<ViewPager2>(R.id.profile_pager)
            val courseTabLayout = it.findViewById<TabLayout>(R.id.profile_tab_layout)

            val pagerAdapter = CourseDetailsPagerAdapter(fragmentActivity)
            pagerAdapter.setFragmentList(getCourseDetailsPager())
            coursePager.adapter = pagerAdapter
            coursePager.registerOnPageChangeCallback(listener)

            val tabDetails = getTabDetails()
            TabLayoutMediator(courseTabLayout, coursePager) { tab, position ->
                tab.text = tabDetails[position].tabTitle
                tab.icon = ContextCompat.getDrawable(it.context, tabDetails[position].tabIcon)
                coursePager.setCurrentItem(tab.position, true)
            }.attach()
        }
    }

    private fun getCourseDetailsPager(): ArrayList<Fragment> {
        val listFrag = ArrayList<Fragment>()
        listFrag.add(SessionsFragment())
        listFrag.add(OverviewFragment())
        return listFrag
    }

    private fun getTabDetails(): ArrayList<CourseDetailsTabsData> {
        val tabDetails = ArrayList<CourseDetailsTabsData>()
        rootView?.let {
            tabDetails.add(CourseDetailsTabsData(it.context.getString(R.string.tab_sessions)))
            tabDetails.add(CourseDetailsTabsData(it.context.getString(R.string.tab_overview)))
        }

        return tabDetails
    }

    override fun changeState(state: CourseDetailsView.State) {
        //
    }
}