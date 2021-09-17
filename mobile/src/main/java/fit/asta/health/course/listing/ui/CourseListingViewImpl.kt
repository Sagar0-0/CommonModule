package fit.asta.health.course.listing.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.course.listing.adapter.CourseListingAdapter
import fit.asta.health.course.listing.adapter.listeners.OnCourseClickListener
import fit.asta.health.course.listing.data.CourseIndexData
import kotlinx.android.synthetic.main.course_list_activity.view.*


class CourseListingViewImpl : CourseListingView {

    private var rootView: View? = null
    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.course_list_activity, null,
            false
        )
        setupRecyclerView()
        return rootView
    }

    private fun setupRecyclerView() {
        rootView?.let {
            val adapter = CourseListingAdapter()
            it.rcvCourseList.layoutManager = LinearLayoutManager(it.context)
            it.rcvCourseList.adapter = adapter

        }
    }

    override fun setAdapterClickListener(listener: OnCourseClickListener) {
        rootView?.let {
            (it.rcvCourseList.adapter as CourseListingAdapter).setAdapterClickListener(listener)
        }
    }


    override fun changeState(state: CourseListingView.State) {
        when (state) {
            is CourseListingView.State.LoadCourses -> setAdapter(state.list)
            CourseListingView.State.Empty -> showEmpty()
        }
    }

    private fun showEmpty() {

    }

    private fun setAdapter(list: List<CourseIndexData>) {
        rootView?.let {
            (it.rcvCourseList.adapter as CourseListingAdapter).updateList(list)
        }
    }
}