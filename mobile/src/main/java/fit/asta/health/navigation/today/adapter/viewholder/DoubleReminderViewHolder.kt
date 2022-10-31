package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import android.widget.TextView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl

class DoubleReminderViewHolder(itemView: View) : BaseViewHolder<TodayPlanItemData>(itemView) {
    override fun bindData(content: TodayPlanItemData) {

        itemView.apply {
            findViewById<TextView>(R.id.tagFastingcard).text = content.tag
            findViewById<TextView>(R.id.titleFastingcard).text = content.title
            findViewById<TextView>(R.id.subtitleFastingcard).text = content.subTitle
            findViewById<TextView>(R.id.timeFastingcard).text = content.time.toString()
            findViewById<TextView>(R.id.durationFastingcard).text = content.duration.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)),
                findViewById(R.id.imagefastingcard)
            )
        }
    }
}