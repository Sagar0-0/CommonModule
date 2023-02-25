package fit.asta.health.old.course.listing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.course.listing.adapter.listeners.OnCourseClickListener
import fit.asta.health.old.course.listing.adapter.viewholder.HeaderViewHolder
import fit.asta.health.old.course.listing.data.CourseIndexData


class CourseListingAdapter :
    BaseAdapter<CourseIndexData>() {

    private var onClickListener: OnCourseClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CourseIndexData> {
        val layout = R.layout.course_list_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return HeaderViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CourseIndexData>, position: Int) {
        val itemHolder = holder as HeaderViewHolder
        itemHolder.bindData(items[position])
    }

    fun setAdapterClickListener(listener: OnCourseClickListener){
        onClickListener = listener
    }
}