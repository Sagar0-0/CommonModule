package fit.asta.health.course.listing.adapter.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.course.listing.adapter.listeners.OnCourseClickListener
import fit.asta.health.course.listing.data.CourseIndexData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.course_list_card.view.*

class HeaderViewHolder(
    itemView: View,
    private val onClickListener: OnCourseClickListener?
) : BaseViewHolder<CourseIndexData>(itemView), View.OnClickListener {

    private var currentItem: CourseIndexData? = null

    init {
        itemView.courseFrame?.setOnClickListener(this)
        itemView.imgPlay?.setOnClickListener(this)
        itemView.imgShareAsana?.setOnClickListener(this)
        itemView.imgReminderAsana?.setOnClickListener(this)
        itemView.imgFavouiteAsana?.setOnClickListener(this)
    }

    override fun bindData(content: CourseIndexData) {

        currentItem = content

        itemView.courseTitle.text = content.title
        itemView.courseSubTitle.text = content.subTitle
        itemView.courseLevel.text = content.audienceLevel
        itemView.txtDurationAsana.text = content.duration
        itemView.context.showImageByUrl(
            Uri.parse(getPublicStorageUrl(itemView.context, content.url)),
            itemView.courseImage
        )

    }

    override fun onClick(view: View) {
        when (view) {
            itemView.courseFrame -> {
                onClickListener?.onCourseClick(layoutPosition)
            }
            itemView.imgPlay -> {
                onClickListener?.onPlayClick(layoutPosition)
            }
            itemView.imgShareAsana -> {
                onClickListener?.onShareClick(layoutPosition)
            }
            itemView.imgReminderAsana -> {
                onClickListener?.onAlarmClick(layoutPosition)
            }
            itemView.imgFavouiteAsana -> {
                onClickListener?.onFavoriteClick(layoutPosition)
            }
        }
    }
}