package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.TextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class SingleReminderViewHolder(itemView: View) : BaseViewHolder<TodayPlanItemData>(itemView) {
    override fun bindData(content: TodayPlanItemData) {
        itemView.apply {

            findViewById<TextView>(R.id.tagSingleRemindercard).text = content.tag
            findViewById<TextView>(R.id.titleSingleRemindercard).text = content.title
            findViewById<TextView>(R.id.subtitleSingleRemindercard).text = content.subTitle
            findViewById<TextView>(R.id.timeSingleRemindercard).text = content.time.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)),
                findViewById(R.id.imageSingleRemindercard)
            )
        }

    }
}