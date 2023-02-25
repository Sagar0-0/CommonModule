package fit.asta.health.old.course.details

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import fit.asta.health.R
import fit.asta.health.old.course.details.adapter.CourseDetailsTabType
import fit.asta.health.old.course.details.data.CourseDetailsData
import fit.asta.health.old.course.details.data.CourseHeaderData
import fit.asta.health.old.course.details.ui.OverviewFragment
import fit.asta.health.old.course.details.ui.PagerAdapter
import fit.asta.health.old.course.details.ui.SessionsFragment
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.mapStringKey
import fit.asta.health.utils.showImageByUrl


class CourseDetailsViewImpl : CourseDetailsView {

    private var rootView: View? = null
    private var fragmentManager: FragmentManager? = null
    private var courseTabType: CourseDetailsTabType = CourseDetailsTabType.SessionsTab

    override fun setContentView(activity: Activity, container: ViewGroup?): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.course_activity, container,
            false
        )
        return rootView
    }

    override fun setUpViewPager(
        fragmentManager: FragmentManager
    ) {
        this.fragmentManager = fragmentManager
    }

    override fun changeState(state: CourseDetailsView.State) {
        when (state) {
            is CourseDetailsView.State.LoadCourseDetails -> loadCourseDetails(state.course)
            else -> {}
        }
    }

    override fun setTabType(tabType: CourseDetailsTabType) {
        courseTabType = tabType
    }

    private fun loadCourseDetails(course: CourseDetailsData) {

        updateHeader(course.header)
        setAdapter(course)
    }

    private fun updateHeader(header: CourseHeaderData) {

        rootView?.let {
            it.findViewById<ContentLoadingProgressBar>(R.id.progressCourse).hide()
            it.context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(it.context, header.url)),
                it.findViewById(R.id.courseImage)
            )
            it.findViewById<CollapsingToolbarLayout>(R.id.collapsingCourseLayout).title =
                header.title
            //it.txt_course_subtitle.text = header.subTitle
            it.findViewById<TextView>(R.id.txtLevelSubTitle).text =
                it.context.mapStringKey(header.level)
            it.findViewById<TextView>(R.id.txtDurationTime).text = header.duration

            //courseId = course.uid?:""
            /*val max = course.modules?.size!!
             progress_course_status.max = max
             progress_course_status.progress = 1
             txt_course_duration.text = courseInfo?.duration*/
        }
    }

    private fun setAdapter(course: CourseDetailsData) {

        rootView?.let {

            val adapter = PagerAdapter(this.fragmentManager!!)
            adapter.addFragment(
                SessionsFragment.newInstance(course.uid, ArrayList(course.sessions)),
                it.context.getString(R.string.tab_sessions)
            )
            adapter.addFragment(
                OverviewFragment.newInstance(course.overview),
                it.context.getString(R.string.tab_overview)
            )
            val courseViewPager = it.findViewById<ViewPager>(R.id.courseViewPager)
            courseViewPager.adapter = adapter
            it.findViewById<TabLayout>(R.id.courseTabs).setupWithViewPager(courseViewPager)
        }
    }
}