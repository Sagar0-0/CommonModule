package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class CourseViewHolder(itemView: View, private val onClickListener: OnPlanClickListener?) :
    BaseViewHolder<TodayPlanItemData>(itemView), View.OnClickListener {

    init {
        itemView.findViewById<MaterialCardView>(R.id.Course_cardview)?.setOnClickListener(this)
        itemView.findViewById<ImageView>(R.id.imageButton)?.setOnClickListener(this)
        itemView.findViewById<ImageView>(R.id.reschedulingID)?.setOnClickListener(this)
    }

    override fun bindData(content: TodayPlanItemData) {

        itemView.apply {
            findViewById<TextView>(R.id.tagYogacard).text = content.tag
            findViewById<TextView>(R.id.titleToday).text = content.title
            findViewById<TextView>(R.id.subtitleToday).text = content.subTitle
            findViewById<TextView>(R.id.timeToday).text = content.time.toString()
            findViewById<TextView>(R.id.durationToday).text = content.duration.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)),
                findViewById(R.id.imageYoga)
            )
        }
    }

    override fun onClick(view: View) {

        when (view) {
            itemView.findViewById<MaterialCardView>(R.id.Course_cardview) -> {
                onClickListener?.onPlanClick(layoutPosition)
            }
            itemView.findViewById<ImageView>(R.id.imageButton) -> {
                onClickListener?.onPlayClick(layoutPosition)
            }
            itemView.findViewById<ImageView>(R.id.reschedulingID) -> {
                onClickListener?.onRescheduling(layoutPosition)
            }
        }
    }
}