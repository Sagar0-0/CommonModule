package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.today_single_reminder.view.*

class SingleReminderViewHolder(itemView: View) : BaseViewHolder<TodayPlanItemData>(itemView) {
    override fun bindData(content: TodayPlanItemData) {
        itemView.apply {

            tagSingleRemindercard.text = content.tag
            titleSingleRemindercard.text = content.title
            subtitleSingleRemindercard.text = content.subTitle
            timeSingleRemindercard.text = content.time.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)),
                imageSingleRemindercard
            )
        }

    }
}