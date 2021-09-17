package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.today_double_reminder.view.*

class DoubleReminderViewHolder(itemView: View) : BaseViewHolder<TodayPlanItemData>(itemView) {
    override fun bindData(content: TodayPlanItemData) {

        itemView.apply {
            tagFastingcard.text = content.tag
            titleFastingcard.text = content.title
            subtitleFastingcard.text = content.subTitle
            timeFastingcard.text = content.time.toString()
            durationFastingcard.text = content.duration.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)), imagefastingcard
            )
        }


    }
}