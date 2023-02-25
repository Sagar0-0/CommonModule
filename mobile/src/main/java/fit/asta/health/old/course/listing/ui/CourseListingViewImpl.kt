package fit.asta.health.old.course.listing.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.old.course.listing.adapter.CourseListingAdapter
import fit.asta.health.old.course.listing.adapter.listeners.OnCourseClickListener
import fit.asta.health.old.course.listing.data.CourseIndexData


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
            val rcvCourseList = it.findViewById<RecyclerView>(R.id.rcvCourseList)
            rcvCourseList.layoutManager = LinearLayoutManager(it.context)
            rcvCourseList.adapter = adapter

        }
    }

    override fun setAdapterClickListener(listener: OnCourseClickListener) {
        rootView?.let {
            (it.findViewById<RecyclerView>(R.id.rcvCourseList).adapter as CourseListingAdapter).setAdapterClickListener(
                listener
            )
        }
    }


    override fun changeState(state: CourseListingView.State) {
        when (state) {
            is CourseListingView.State.LoadCourses -> setAdapter(state.list)
            CourseListingView.State.Empty -> showEmpty()
            else -> {}
        }
    }

    private fun showEmpty() {

    }

    private fun setAdapter(list: List<CourseIndexData>) {
        rootView?.let {
            (it.findViewById<RecyclerView>(R.id.rcvCourseList).adapter as CourseListingAdapter).updateList(
                list
            )
        }
    }
}