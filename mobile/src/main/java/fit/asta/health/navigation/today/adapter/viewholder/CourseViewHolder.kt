package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.today_course.view.*


class CourseViewHolder(itemView: View, private val onClickListener: OnPlanClickListener?) :
    BaseViewHolder<TodayPlanItemData>(itemView), View.OnClickListener {

    init {
        itemView.Course_cardview?.setOnClickListener(this)
        itemView.imageButton?.setOnClickListener(this)
        itemView.reschedulingID?.setOnClickListener(this)
    }

    override fun bindData(content: TodayPlanItemData) {

        itemView.apply {
            tagYogacard.text = content.tag
            titleToday.text = content.title
            subtitleToday.text = content.subTitle
            timeToday.text = content.time.toString()
            durationToday.text = content.duration.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)), imageYoga
            )
        }
    }

    override fun onClick(view: View) {
        when (view) {
            itemView.Course_cardview -> {
                onClickListener?.onPlanClick(layoutPosition)
            }
            itemView.imageButton -> {
                onClickListener?.onPlayClick(layoutPosition)
            }
            itemView.reschedulingID->{
                onClickListener?.onRescheduling(layoutPosition)
            }

        }
    }
}