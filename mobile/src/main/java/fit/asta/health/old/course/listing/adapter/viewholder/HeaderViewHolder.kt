package fit.asta.health.old.course.listing.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.course.listing.adapter.listeners.OnCourseClickListener
import fit.asta.health.old.course.listing.data.CourseIndexData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class HeaderViewHolder(
    itemView: View,
    private val onClickListener: OnCourseClickListener?
) : BaseViewHolder<CourseIndexData>(itemView), View.OnClickListener {

    private var currentItem: CourseIndexData? = null

    init {
        itemView.findViewById<MaterialCardView>(R.id.courseFrame)?.setOnClickListener(this)
        itemView.findViewById<AppCompatImageView>(R.id.imgPlay)?.setOnClickListener(this)
        itemView.findViewById<AppCompatImageView>(R.id.imgShareAsana)?.setOnClickListener(this)
        itemView.findViewById<AppCompatImageView>(R.id.imgReminderAsana)?.setOnClickListener(this)
        itemView.findViewById<AppCompatImageView>(R.id.imgFavouiteAsana)?.setOnClickListener(this)
    }

    override fun bindData(content: CourseIndexData) {

        currentItem = content

        itemView.findViewById<TextView>(R.id.courseTitle).text = content.title
        itemView.findViewById<TextView>(R.id.courseSubTitle).text = content.subTitle
        itemView.findViewById<TextView>(R.id.courseLevel).text = content.audienceLevel
        itemView.findViewById<TextView>(R.id.txtDurationAsana).text = content.duration
        itemView.context.showImageByUrl(
            Uri.parse(getPublicStorageUrl(itemView.context, content.url)),
            itemView.findViewById(R.id.courseImage)
        )

    }

    override fun onClick(view: View) {
        when (view) {
            itemView.findViewById<MaterialCardView>(R.id.courseFrame) -> {
                onClickListener?.onCourseClick(layoutPosition)
            }
            itemView.findViewById<AppCompatImageView>(R.id.imgPlay) -> {
                onClickListener?.onPlayClick(layoutPosition)
            }
            itemView.findViewById<AppCompatImageView>(R.id.imgShareAsana) -> {
                onClickListener?.onShareClick(layoutPosition)
            }
            itemView.findViewById<AppCompatImageView>(R.id.imgReminderAsana) -> {
                onClickListener?.onAlarmClick(layoutPosition)
            }
            itemView.findViewById<AppCompatImageView>(R.id.imgFavouiteAsana) -> {
                onClickListener?.onFavoriteClick(layoutPosition)
            }
        }
    }
}