package fit.asta.health.navigation.today.adapter.viewholder

import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.today_water.view.*

class WaterViewHolder(itemView: View) : BaseViewHolder<TodayPlanItemData>(itemView) {
    override fun bindData(content: TodayPlanItemData) {

        itemView.apply {
            tagWatercard.text = content.tag
            titleWatercard.text = content.title
            time_watercard.text = content.time.toString()
            context.showImageByUrl(
                Uri.parse(getPublicStorageUrl(itemView.context, content.imageUrl)), imageWatercard
            )
        }
    }
}